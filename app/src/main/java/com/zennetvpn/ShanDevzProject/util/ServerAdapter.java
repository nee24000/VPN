package com.zennetvpn.ShanDevzProject.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;
import com.zennetvpn.pro.R;
import java.io.InputStream;
import java.util.ArrayList;
import androidx.cardview.widget.CardView;
import android.view.animation.AnimationUtils;

import com.zennetvpn.ShanDevzProject.adapter.ServerFinger;
import android.app.Activity;
import com.zennetvpn.ShanDevzService.tunnel.TunnelUtils;
import android.text.Html;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.util.Log;

public class ServerAdapter extends BaseAdapter {

	private Context context;
	private Activity c;
	private ArrayList<ServerModel> arrayList;

	public ServerAdapter(Activity c, Context context, ArrayList<ServerModel> arrayList) {
		this.context = context;
		this.c = c;
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	// =========== LAYOUT ตอน Spinner ปิด ===========
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		boolean isDropDown = (parent instanceof Spinner);

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.spinner_item,
					parent,
					false
			);
		}

		bindData(position, convertView, true);   // ไม่ต้อง ping + ไม่ต้อง users
		return convertView;
	}


	// =========== LAYOUT ตอน Dropdown แสดง Servers ===========
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.server_item, parent, false);
		}
		ServerModel model = arrayList.get(position);
		bindData(position, convertView, false);  // <-- โหลดผู้ใช้/ping เหมือนกัน
		// ========= โหลด Users =========
		TextView tvOnlineUsers = convertView.findViewById(R.id.tvOnlineUsers);
		if (tvOnlineUsers != null) {
			tvOnlineUsers.setText("Users: --");
			loadOnlineUsers(model, tvOnlineUsers);
		}

		return convertView;
	}



	// =========== ฟังก์ชันร่วมในการใส่ข้อมูล ===========
	private void bindData(int position, View convertView, boolean isCompact) {

		ServerModel model = arrayList.get(position);

		TextView name = convertView.findViewById(R.id.itemNamea);
		TextView info = convertView.findViewById(R.id.sinf);
		ImageView im = convertView.findViewById(R.id.itemImagea);

		name.setText(model.getServerName());
		info.setText(model.getServerInfo());

		// โหลด icon
		try {
			getServerIcon(position, im);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ========= โหลด Users =========
		TextView tvOnlineUsers = convertView.findViewById(R.id.tvOnlineUsers);
		if (tvOnlineUsers != null) {
			tvOnlineUsers.setText("Users: --");
			loadOnlineUsers(model, tvOnlineUsers);
		}

		// ========= โหลด Ping =========
		TextView ping = convertView.findViewById(R.id.pinglatency);
		TextView stat = convertView.findViewById(R.id.statusping);
		if (ping != null && stat != null) {
			getPing(c, ping, stat, model.getServerHost(), model.getServerPort());
		}
	}



	// =========== โหลดจำนวนผู้ใช้งาน ===========
	private void loadOnlineUsers(ServerModel model, TextView tv) {

		new Thread(() -> {
			try {
				String api = model.getOnlineAPI();

				if (!api.startsWith("http://") && !api.startsWith("https://")) {
					api = "http://" + api;
				}

				URL url = new URL(api);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setReadTimeout(3000);

				BufferedReader br = new BufferedReader(
						new InputStreamReader(conn.getInputStream())
				);
				String result = br.readLine();

				JSONArray arr = new JSONArray(result);
				JSONObject obj = arr.getJSONObject(0);

				int online = obj.getInt("onlines");
				int limit = obj.getInt("limite");

				model.setOnlineUsers(online);
				model.setOnlineLimit(limit);

				// ========= คำนวณ % โหลด =========
				float percent = (limit == 0) ? 0 : (online * 100f / limit);

				String color;

				if (percent < 50) {
					color = "#04D000";    // เขียว
				} else if (percent < 80) {
					color = "#F9C80E";    // เหลือง
				} else {
					color = "#FD1C0D";    // แดง
				}

				String text = "<font color='" + color + "'><b>Users: "
						+ online + "/" + limit + "</b></font>";

				if (tv != null) {
					((Activity) context).runOnUiThread(() ->
							tv.setText(Html.fromHtml(text))
					);
				}

			} catch (Exception e) {

				if (tv != null) {
					((Activity) context).runOnUiThread(() ->
							tv.setText(Html.fromHtml("<font color='#AAAAAA'>Users: --</font>"))
					);
				}
			}
		}).start();
	}



	// =========== Icon ธง ===========
	private void getServerIcon(int position, ImageView im) throws Exception {
		InputStream inputStream = context.getAssets().open(
				"flags/" + arrayList.get(position).getServerflag() + ".png"
		);

		im.setImageDrawable(
				Drawable.createFromStream(inputStream,
						arrayList.get(position).getServerflag() + ".png")
		);

		if (inputStream != null) inputStream.close();
	}


	// =========== Ping Server ===========
	private void getPing(Activity c, TextView ping, TextView stat, String host, String port) {

		new ServerFinger(new ServerFinger.OnPingListener() {

			@Override
			public void ms(int i) {
				c.runOnUiThread(() -> {

					// สีเขียวถ้า ping < 100ms, สีแดงถ้ามากกว่า
					String clr = (i < 100) ? "#04D000" : "#FD1C0D";

					ping.setText(
							Html.fromHtml("<b><font color=\"" + clr + "\">" + i + "ms</font></b>")
					);
				});
			}

			@Override
			public void accept(boolean isOnline) {
				c.runOnUiThread(() -> {

					// สี Online / Offline
					String clr = isOnline ? "#04D000" : "#FD1C0D";
					String text = isOnline ? "Online" : "Offline";

					stat.setText(
							Html.fromHtml("<font color=\"" + clr + "\"><b>" + text + "</b></font>")
					);
				});
			}

			@Override
			public void load() {
				c.runOnUiThread(() -> {
					ping.setText("...");
					stat.setText("Checking");
				});
			}

		}, host, port, 1500);
	}



}
