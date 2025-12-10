package com.zennetvpn.ShanDevzProject.util;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import java.io.InputStream;
import android.util.Log;

import com.zennetvpn.ShanDevzProject.model.PayloadModel;
import com.zennetvpn.pro.R;

import java.util.ArrayList;


public class PayloadAdapter extends BaseAdapter {

    Context context;
    ArrayList<PayloadModel> arrayList;

    public PayloadAdapter(Context context, ArrayList<PayloadModel> arrayList) {
        this.context = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.itemNamea);
        TextView info = convertView.findViewById(R.id.sinf);
        ImageView img = convertView.findViewById(R.id.itemImagea);

        PayloadModel model = arrayList.get(position);

        name.setText(model.getName());
        info.setText(model.getInfo());

        // Debug
        Log.d("PAYLOAD", "Try load logo: " + model.getLogo());
        Log.d("PAYLOAD", "Path: payload/" + model.getLogo() + ".png");
        Log.d("PAYLOAD", "ImageView = " + img);

        // โหลดรูปจาก assets/payload/logo.png
        String logoFile = model.getLogo();

        if (logoFile != null && !logoFile.isEmpty()) {

            // บังคับ lower-case ชื่อไฟล์
            String fileName = logoFile.toLowerCase() + ".png";

            try {
                InputStream is = context.getAssets().open("payload/" + fileName);
                img.setImageDrawable(Drawable.createFromStream(is, null));
                is.close();

            } catch (Exception e) {
                Log.e("PAYLOAD", "Load error: " + "payload/" + fileName + " => " + e.getMessage());
                img.setImageResource(R.drawable.ic_launcher_foreground);
            }

        } else {
            Log.e("PAYLOAD", "Logo empty for: " + model.getName());
            img.setImageResource(R.drawable.ic_launcher_foreground);
        }


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
