package com.zennetvpn.ShanDevzProject;

import android.Manifest;
import android.content.Context;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.zennetvpn.ShanDevzProject.activities.BaseActivity;
import com.zennetvpn.ShanDevzProject.activities.ConfigGeralActivity;
import com.zennetvpn.ShanDevzProject.adapter.LogsAdapter;
import com.zennetvpn.ShanDevzProject.util.ConfigUpdate;
import com.zennetvpn.ShanDevzProject.util.ConfigUtil;
import com.zennetvpn.ShanDevzProject.util.GoogleFeedbackUtils;
import com.zennetvpn.ShanDevzProject.util.NethPogi;
import com.zennetvpn.ShanDevzProject.util.PayloadAdapter;
import com.zennetvpn.ShanDevzProject.model.PayloadModel;
import com.zennetvpn.ShanDevzProject.util.ServerAdapter;
import com.zennetvpn.ShanDevzProject.util.ServerModel;
import com.zennetvpn.ShanDevzProject.util.Utils;
import com.zennetvpn.ShanDevzProject.util.VPNUtils;
import com.zennetvpn.ShanDevzService.LaunchVpn;
import com.zennetvpn.ShanDevzService.ShanDevzService;
import com.zennetvpn.ShanDevzService.config.ConfigParser;
import com.zennetvpn.ShanDevzService.config.Settings;
import com.zennetvpn.ShanDevzService.logger.ConnectionStatus;
import com.zennetvpn.ShanDevzService.logger.SkStatus;
import com.zennetvpn.ShanDevzService.tunnel.TunnelManagerHelper;
import com.zennetvpn.ShanDevzService.tunnel.TunnelUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import com.zennetvpn.ShanDevzProject.util.SimpleCodec;

import com.zennetvpn.pro.R;


public class ShanDevzMainActivity extends BaseActivity implements SkStatus.StateListener {

    private ArrayList<PayloadModel> payList;

    private Button mButtonSet;

    private SharedPreferences prefs;

    private MaterialTextView bytesIn;

    private MaterialTextView bytesOut;

    private ServerAdapter serverAdapter;

    private Window window;

    private TextView tvTimer;

    private ImageView logo;   // ← ต้องอยู่ตรงนี้

    private void startLogoAnimation() {
        if (logo != null) {
            Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_zen);
            logo.startAnimation(rotate);
        }
    }


    private void stopLogoAnimation() {
        if (logo != null) {
            logo.clearAnimation();
        }
    }
	public class NethPH extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            // TODO: Implement this method
            return 2;
        }

        @Override
        public boolean isViewFromObject(View p1, Object p2)
        {
            // TODO: Implement this method
            return p1 == p2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            int[] ids = new int[]{R.id.tab1, R.id.tab2};
            int id = 0;
            id = ids[position];
            // TODO: Implement this method
            return findViewById(id);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            // TODO: Implement this method
            return titles.get(position);
        }

        private List<String> titles;
        public NethPH(List<String> str)
        {
            titles = str;
        }
	}
    private static final String UPDATE_VIEWS = "MainUpdate";
	public static final String OPEN_LOGS = "com.ayothayavpn.sockshttp:openLogs";
	private Settings mConfig;
	private Toolbar toolbar_main;
	private Handler mHandler;
	private MaterialTextView status;
	private FloatingActionButton deleteLogs;
	private RecyclerView logList;
    private LogsAdapter mLogAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ConfigUtil config;
    private Button buttonshan_devz_fiverr;
    
    private AppCompatSpinner serverSpinner;
    private AppCompatSpinner payloadSpinner;
    private ViewPager vp;
    private TabLayout tabs;
	private InterstitialAd interstitialAd;
	private AdView adsBannerView;
	private RewardedAd rewardedAd;
	private MaterialAlertDialogBuilder builer;
	private AlertDialog alert;
	private MaterialTextView ok;
	private MaterialTextView cancel;
	private boolean mClaim;
	private boolean isLoading;
	
    public void loadInterstitialAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
			this,
			getString(R.string.SHAN_intersid),
			adRequest,
			new InterstitialAdLoadCallback() {
				@Override
				public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
					// The mInterstitialAd reference will be null until
					// an ad is loaded.
					ShanDevzMainActivity.this.interstitialAd = interstitialAd;
					// Log.i(TAG, "onAdLoaded");
					// Toast.makeText(MyActivity.this, "onAdLoaded()",
					// Toast.LENGTH_SHORT).show();
					interstitialAd.setFullScreenContentCallback(
						new FullScreenContentCallback() {
							@Override
							public void onAdDismissedFullScreenContent() {
								// Called when fullscreen content is dismissed.
								// Make sure to set your reference to null so you don't
								// show it a second time.
								ShanDevzMainActivity.this.interstitialAd = null;
								// Log.d("TAG", "The ad was dismissed.");
							}

							@Override
							public void onAdFailedToShowFullScreenContent(AdError adError) {
								// Called when fullscreen content failed to show.
								// Make sure to set your reference to null so you don't
								// show it a second time.
								ShanDevzMainActivity.this.interstitialAd = null;
								// Log.d("TAG", "The ad failed to show.");
							}

							@Override
							public void onAdShowedFullScreenContent() {
								// Called when fullscreen content is shown.
								// Log.d("TAG", "The ad was shown.");
							}
						});
				}

				@Override
				public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
					// Handle the error
					// Log.i(TAG, loadAdError.getMessage());
					interstitialAd = null;

					// String error = String.format("domain: %s, code: %d, message: %s",
					// loadAdError.getDomain(), loadAdError.getCode(),
					// loadAdError.getMessage());
					// Toast.makeText(MyActivity.this, "onAdFailedToLoad() with error: " +
					// error, Toast.LENGTH_SHORT).show();
				}
			});
    }

    private void adsPopUp() {
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            startGame();
        }
    }

    private void startGame() {
        if (interstitialAd == null) {
            loadInterstitialAds();
        }
    }
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private long mTimeLeftBtn;
    private long saved_ads_time;
    private boolean mConnected;
    private CountDownTimer mBtnCountDown;
	private boolean mTimerEnabled;
    private MaterialTextView mMaterialTextViewCountDown;

    private ArrayList<ServerModel> serverList = new ArrayList<>();
    
    
	@Override
    protected void onCreate(@Nullable Bundle $)
    {
        super.onCreate($);
		mHandler = new Handler();
		mConfig = new Settings(this);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));    
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
	    prefs = mConfig.getPrefsPrivate();
        askNotificationPermission();
		boolean showFirstTime = prefs.getBoolean("connect_first_time", true);
		int lastVersion = prefs.getInt("last_version", 0);
		loadInterstitialAds();
		if (showFirstTime)
        {
            SharedPreferences.Editor pEdit = prefs.edit();
            pEdit.putBoolean("connect_first_time", false);
            pEdit.apply();
			Settings.setDefaultConfig(this);
        }

		try {
			int idAtual = ConfigParser.getBuildId(this);
			if (lastVersion < idAtual) {
				SharedPreferences.Editor pEdit = prefs.edit();
				pEdit.putInt("last_version", idAtual);
				pEdit.apply();
				if (!showFirstTime) {
					if (lastVersion <= 12) {
						Settings.setDefaultConfig(this);
						Settings.clearSettings(this);
					}
				}
			}
		} catch(IOException e) {}
        String reMod = getIntent().getStringExtra("ReMod");

// กัน NullPointerException
        if (reMod == null) {
            reMod = "";
        }

        doLayout();
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_VIEWS);
		filter.addAction(OPEN_LOGS);
		LocalBroadcastManager.getInstance(this)
		.registerReceiver(mActivityReceiver, filter);
		doUpdateLayout();
 //       doTabs();
	}
    private void askNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {

            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1212);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1212);

            }
        }
    }
    private void doLayout() {

        setContentView(R.layout.activity_main_drawer);

        // ImageView โลโก้
        logo = findViewById(R.id.logoZen);

        toolbar_main = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

        config = new ConfigUtil(this);
        mClaim = false;
        window = getWindow();

        // Config Version
        MaterialTextView configv = findViewById(R.id.configv);
        configv.setText("Config Version: " + config.getVersion());

        // Update Time (ตัวนี้ต้องใช้ id = update)
        //MaterialTextView updatet = findViewById(R.id.gags);

        SharedPreferences pref = mConfig.getPrefsPrivate();
        String updateTime = pref.getString("Latest Version", "");

        //if (updateTime == null || updateTime.trim().isEmpty()) {
            //updatet.setText("");
        //} else {
            //updatet.setText(updateTime);
        //}
        // ====== โหลด Server และ Payload Spinner ======
        if (serverList == null) {
            serverList = new ArrayList<>();
        }

// สร้าง Adapter สำหรับ Server
        serverAdapter = new ServerAdapter(
                ShanDevzMainActivity.this,   // Activity
                this,                        // Context
                serverList                 // List server
        );

// ใส่ Adapter ให้ serverSpinner
        serverSpinner = findViewById(R.id.serverSpinner);
        serverSpinner.setAdapter(serverAdapter);

// Listener เลือก server
        serverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mConfig.getPrefsPrivate().edit().putInt("SelectedServer", position).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // ตัวนับเวลา Time Credit (gags)
        tvTimer = findViewById(R.id.gags);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_main, R.string.open, R.string.cancel);
        toggle.syncState();

        loadInterstitialAds();

        adsBannerView = findViewById(R.id.adBannerMainView);
        if (TunnelUtils.isNetworkOnline(this)) {
            adsBannerView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    adsBannerView.setVisibility(View.VISIBLE);
                }
            });
            adsBannerView.loadAd(new AdRequest.Builder().build());
        }

    mMaterialTextViewCountDown = (MaterialTextView)findViewById(R.id.gags);
        mButtonSet = (Button) findViewById(R.id.btnAddTime);
        mButtonSet.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                        loadz();
                }
			});
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mLogAdapter = new LogsAdapter(layoutManager,this);
		deleteLogs = (FloatingActionButton)findViewById(R.id.clearLog);
		logList = (RecyclerView) findViewById(R.id.recyclerDrawerView);
		logList.setAdapter(mLogAdapter);
		logList.setLayoutManager(layoutManager);
		mLogAdapter.scrollToLastPosition();
		deleteLogs.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					mLogAdapter.clearLog();
					deleteLogs.startAnimation(AnimationUtils.loadAnimation(ShanDevzMainActivity.this, R.anim.grow));
					VPNUtils.ShanToast(ShanDevzMainActivity.this, R.drawable.check, "Logs is cleared");
				//	SkStatus.logInfo("<font color='green'>Logs Deleted By your Finger!</font>");
					
				}


			});
        updateConfig(true);
		status = (MaterialTextView) findViewById(R.id.monsour_stats);
        bytesIn = (MaterialTextView) findViewById(R.id.download);
        bytesOut = (MaterialTextView) findViewById(R.id.upload);
        buttonshan_devz_fiverr = (Button) findViewById(R.id.shan_start);
        buttonshan_devz_fiverr.setOnClickListener(new OnClickListener(){
        @Override
        public void onClick(View ugh){
            startOrStopTunnel(ShanDevzMainActivity.this);
        }
        });
        new Timer().schedule(new TimerTask(){@Override public void run() {new Handler(Looper.getMainLooper()).post(new Runnable() {@Override public void run() {
        getData();}});}}, 0,1000);
        serverList = new ArrayList<ServerModel>();
        serverSpinner = (AppCompatSpinner) findViewById(R.id.serverSpinner);
		payloadSpinner = (AppCompatSpinner) findViewById(R.id.payloadSpinner);
        try {
            JSONArray array = config.getServersArray();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                SharedPreferences prefs = mConfig.getPrefsPrivate();
                SharedPreferences.Editor edit = prefs.edit();
                String flag = jsonObject.getString("FLAG");
                String sname = jsonObject.getString("Name");
                String sHost = jsonObject.getString("ServerIP");
                String sPort = jsonObject.getString("ServerPort");
                String sinfo = jsonObject.getString("sInfo");
                String onlineApi = jsonObject.getString("OnlineAPI");

                ServerModel model = new ServerModel();
                model.setServerName(sname);
                model.setServerInfo(sinfo);
                model.setServerHost(sHost);
                model.setServerPort(sPort);
                model.setServerflag(flag);
                model.setOnlineAPI(onlineApi);


// เพิ่มโหลดจำนวน online ผู้ใช้งาน
                //loadOnlineUsers(model);

                serverList.add(model);

                edit.apply();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (payList == null) {
            payList = new ArrayList<>();
        }
        try {
            JSONArray array = config.getNetworksArray();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                SharedPreferences prefs = mConfig.getPrefsPrivate();
                SharedPreferences.Editor edit = prefs.edit();
                String sname = jsonObject.getString("Name");
                String sinfo = jsonObject.getString("pInfo");
                String spayload = jsonObject.getString("Payload");
                String logo = jsonObject.optString("logo", "");
                PayloadModel model = new PayloadModel();
                model.setName(sname);
                model.setInfo(sinfo);
                model.setPayload(spayload);
                model.setLogo(logo);
                payList.add(model);
                edit.apply();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServerAdapter serverAdapter = new ServerAdapter(
                ShanDevzMainActivity.this,
                this,
                serverList
        );
        PayloadAdapter payAdapter = new PayloadAdapter(
                ShanDevzMainActivity.this,
                payList
        );

        payloadSpinner.setAdapter(payAdapter);

        if (config.getJSONConfig() != null){
		serverSpinner.setAdapter(serverAdapter);
        payloadSpinner.setAdapter(payAdapter);
		} else {
			VPNUtils.ShanToast(this, R.drawable.wrong, "No Server & Payload Detected.");
		}
        serverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
                   mConfig.getPrefsPrivate().edit().putInt("Selected1", p3).apply();
				   adsPopUp();
                }
                @Override
                public void onNothingSelected(AdapterView<?> p1) {
                    Toast.makeText(getApplicationContext(), "Nothing selected!", Toast.LENGTH_SHORT).show();
                }
        });
        payloadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
                    mConfig.getPrefsPrivate().edit().putInt("Selected2", p3).apply();
					adsPopUp();
                }
                @Override
                public void onNothingSelected(AdapterView<?> p1) {
                    Toast.makeText(getApplicationContext(), "Nothing selected!",Toast.LENGTH_SHORT).show();
                }
            });
        serverSpinner.setSelection(mConfig.getPrefsPrivate().getInt("Selected1",0));
        payloadSpinner.setSelection(mConfig.getPrefsPrivate().getInt("Selected2",0));
		try {
			JSONArray jsonArray = config.getServersArray();
			for (int i = 0; i < jsonArray.length(); i++) {
				MaterialTextView serverCount = (MaterialTextView) findViewById(R.id.serverCount);
				serverCount.setText("Server(s): " + jsonArray.length());
			}
		} catch (Exception err){
			err.printStackTrace();
			VPNUtils.ShanToast(ShanDevzMainActivity.this, R.drawable.wrong, err.getMessage());
		}
		try {
			JSONArray jsonArray2 = config.getNetworksArray();
			for (int i = 0; i < jsonArray2.length(); i++) {
				MaterialTextView payCount = (MaterialTextView) findViewById(R.id.payCount);
				payCount.setText("Payload(s): " + jsonArray2.length());
			}
		} catch (Exception err){
			err.printStackTrace();
			VPNUtils.ShanToast(ShanDevzMainActivity.this, R.drawable.wrong, err.getMessage());
		}
	    vp = (ViewPager)findViewById(R.id.viewpager);
        tabs = (TabLayout)findViewById(R.id.tablayout);
        vp.setAdapter(new NethPH(Arrays.asList(new String[]{"HOME", "LOGS"})));
        vp.setOffscreenPageLimit(2);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(vp);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

				@Override
				public void onPageScrolled(int p1, float p2, int p3) {
				}

				@Override
				public void onPageSelected(int p1) {
					switch (p1){
						case 0:
							deleteLogs.setVisibility(View.GONE);
							break;
					    case 1:
							new Handler().postDelayed(new Runnable(){
								@Override
								public void run(){
							deleteLogs.setVisibility(View.VISIBLE);
							deleteLogs.startAnimation(AnimationUtils.loadAnimation(ShanDevzMainActivity.this, R.anim.grow));
							}
							}, 100);
							break;
					}
				}

				@Override
				public void onPageScrollStateChanged(int p1) {
				}
				
			
		});
        final NavigationView drawerNavigationView = (NavigationView) findViewById(R.id.drawerNavigationView);
        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(MenuItem p1) {
                    switch (p1.getItemId()) {
                        case R.id.updConf:
                        updateConfig(false);
                        break;
                        case R.id.Huntzf:
                        ipHuntz();
                        break;
                        case R.id.Bell:
                            View inflate = LayoutInflater.from(ShanDevzMainActivity.this).inflate(R.layout.notification, null);
                            MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(ShanDevzMainActivity.this);
                            builer.setView(inflate); 
                            MaterialTextView title = inflate.findViewById(R.id.notiftext1);
                            MaterialTextView ms = inflate.findViewById(R.id.confimsg);
                            MaterialTextView ok = inflate.findViewById(R.id.appButton1);
                            MaterialTextView cancel = inflate.findViewById(R.id.appButton2);
                            title.setText("Release Notes!");
                            ms.setText(config.geNote());
                            ok.setText("Ok,Close");
                            cancel.setText(".");
                            cancel.setVisibility(View.GONE);
                            final AlertDialog alert = builer.create(); 
                            alert.setCanceledOnTouchOutside(false);
		alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
                            alert.getWindow().setGravity(Gravity.CENTER); 
                            ok.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View p1){
                                        alert.dismiss();
                                    }
                                });

                            cancel.setOnClickListener(new OnClickListener(){

                                    @Override
                                    public void onClick(View p1) {

                                        alert.dismiss();
                                    }




                                });
                            alert.show();
                        break;
						case R.id.ann:
							View inflate1 = LayoutInflater.from(ShanDevzMainActivity.this).inflate(R.layout.notification, null);
                            MaterialAlertDialogBuilder builer1 = new MaterialAlertDialogBuilder(ShanDevzMainActivity.this);
                            builer1.setView(inflate1); 
                            MaterialTextView title1 = inflate1.findViewById(R.id.notiftext1);
                            MaterialTextView ms1 = inflate1.findViewById(R.id.confimsg);
                            MaterialTextView ok1 = inflate1.findViewById(R.id.appButton1);
                            MaterialTextView cancel1 = inflate1.findViewById(R.id.appButton2);
                            title1.setText("Announcements!");
                            ms1.setText(config.getAnn());
                            ok1.setText("Ok,Close");
                            cancel1.setText(".");
                            cancel1.setVisibility(View.GONE);
                            final AlertDialog alert1 = builer1.create(); 
                            alert1.setCanceledOnTouchOutside(false);
							alert1.getWindow().getAttributes().windowAnimations = R.style.Shan01;
                            alert1.getWindow().setGravity(Gravity.CENTER); 
                            ok1.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View p1){
                                        alert1.dismiss();
                                    }
                                });

                            cancel1.setOnClickListener(new OnClickListener(){

                                    @Override
                                    public void onClick(View p1) {

                                        alert1.dismiss();
                                    }




                                });
                            alert1.show();
							break;
                  /*      case R.id.claimV:
                        claimz();
                        break;*/
                        case R.id.clearz:
                        clerd();
                        break;
                        case R.id.hardware:
                            MaterialAlertDialogBuilder mBuilder = new MaterialAlertDialogBuilder(ShanDevzMainActivity.this);
                            mBuilder.setTitle("Hardware ID");
                            mBuilder.setMessage(VPNUtils.getHWID());
                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton("COPY", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface mDialogInterface, int mInt)
                                    {
                                        ((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("HWID", VPNUtils.getHWID()));
                                    }
                                });
                            mBuilder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface mDialog, int mInt)
                                    {
                                        mDialog.cancel();
                                    }
                                });
                            mBuilder.show();
                            break;

                        case R.id.miPhoneConfg:
                            if (Build.VERSION.SDK_INT >= 30){
                                    try {
                                        Intent in = new Intent(Intent.ACTION_MAIN);
                                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        in.setClassName("com.android.phone", "com.android.phone.settings.RadioInfo");
                                        ShanDevzMainActivity.this.startActivity(in);
                                    } catch (Exception e){
                                        Toast.makeText(ShanDevzMainActivity.this, R.string.error_no_supported, Toast.LENGTH_SHORT)
                                            .show();   }
                            } else {
                                try {
                                    Intent in = new Intent(Intent.ACTION_MAIN);
                                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    in.setClassName("com.android.settings", "com.android.settings.RadioInfo");
                                    ShanDevzMainActivity.this.startActivity(in);
                                } catch(Exception e) {
                                    Toast.makeText(ShanDevzMainActivity.this, R.string.error_no_supported, Toast.LENGTH_SHORT)
                                        .show();   }
                            }
                            break;
                        case R.id.miAvaliarPlaystore:
                            String url = "https://t.me//Zenze000";
                            Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ShanDevzMainActivity.this.startActivity(Intent.createChooser(intent3, ShanDevzMainActivity.this.getText(R.string.open_with)));
                            break;

                        case R.id.miSendFeedback:
                            if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                                try {
                                    GoogleFeedbackUtils.bindFeedback(ShanDevzMainActivity.this);
                                } catch (Exception e) {
                                    Toast.makeText(ShanDevzMainActivity.this, "Not available on your device", Toast.LENGTH_SHORT)
                                        .show();
                                    SkStatus.logDebug("Error: " + e.getMessage());
                                }
                            }
                            else {
                                Intent email = new Intent(Intent.ACTION_SEND);  
                                email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"harshanadevz@gmail.com"});
                                email.putExtra(Intent.EXTRA_SUBJECT, "Source Code: Ayothaya VPN Devz By @Zenze000- " + ShanDevzMainActivity.this.getString(R.string.feedback));
                                //email.putExtra(Intent.EXTRA_TEXT, "");  

                                //need this to prompts email client only  
                                email.setType("message/rfc822");  

                                ShanDevzMainActivity.this.startActivity(Intent.createChooser(email, "Choose an Email client:"));
                            }
                            break;

                            /** case R.id.miAbout:
                             if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                             drawerLayout.closeDrawers();
                             }
                             Intent aboutIntent = new Intent(mActivity, AboutActivity.class);
                             mActivity.startActivity(aboutIntent);
                             break;**/
                    }
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                      drawerLayout.closeDrawers();
                    }
					adsPopUp();
                    return true;
                        }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener(){
                private boolean bolbol = true;
                @Override
                public void onDrawerSlide(View p1, float p2) {
                 if (bolbol){
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(500);
                p1.startAnimation(fadeIn);
                bolbol = false;
                }     
				}
                @Override
                public void onDrawerOpened(View p1) {
                }
                @Override
                public void onDrawerClosed(View p1) {
                 bolbol = true;
                }
                @Override
                public void onDrawerStateChanged(int p1) {
                }
            });
	}
    private String render_bandwidth(double bw) {
        String postfix;
        float div;
        Object[] objArr;
        float bwf = (float) bw;
        if (bwf >= 1.0E12f) {
            postfix = "TB";
            div = 1.0995116E12f;
        } else if (bwf >= 1.0E9f) {
            postfix = "GB";
            div = 1.0737418E9f;
        } else if (bwf >= 1000000.0f) {
            postfix = "MB";
            div = 1048576.0f;
        } else if (bwf >= 1000.0f) {
            postfix = "KB";
            div = 1024.0f;
        } else {
            objArr = new Object[1];
            objArr[0] = Float.valueOf(bwf);
            return String.format("%.0f", objArr);
        }
        objArr = new Object[2];
        objArr[0] = Float.valueOf(bwf / div);
        objArr[1] = postfix;
        return String.format("%.2f %s", objArr);
    }
    private void getData() {
        boolean isRunning = SkStatus.isTunnelActive();
        long mUpload, mDownload, saved_Send ,saved_Down/*,up, down*/;
        String saved_date, tDate;
        List<Long> allData;
        allData = NethPogi.findData();
        mDownload = allData.get(0);
        mUpload = allData.get(1);
        NethPogi.damn(mDownload, mUpload);
        //down = mDownload;
        //up = mUpload;
        SharedPreferences myData = mConfig.getPrefsPrivate();
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("MMM dd, yyyy");
            tDate = sdf.format(ca.getTime());
            saved_date = myData.getString("today_date", "empty");
            SharedPreferences.Editor editor = myData.edit();
            if (saved_date.equals(tDate)) {
                saved_Send = myData.getLong("UP_DATA", 0);
                saved_Down = myData.getLong("DOWN_DATA", 0);
                editor.putLong("UP_DATA", mUpload + saved_Send);
                editor.putLong("DOWN_DATA", mDownload + saved_Down);
                editor.apply();
            } else {
                editor.clear();
                editor.putString("today_date", tDate);
                editor.apply();
            }
            if(isRunning){
                bytesOut.setText(render_bandwidth(myData.getLong("UP_DATA", 0)));
                bytesIn.setText(render_bandwidth(myData.getLong("DOWN_DATA", 0)));
            }else{
                myData.edit().putLong("UP_DATA", 0).apply();
                myData.edit().putLong("DOWN_DATA", 0).apply();
            }
        }
    }
	
	private void doUpdateLayout() {
        if (config.time()){
        } else {
            mButtonSet.setText("Disabled");
            mButtonSet.setEnabled(false);
        }
        setStarterButton(buttonshan_devz_fiverr, this);
	}
	public void doRestart() {
        finish();
        startActivity(new Intent(this,LauncherActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}
	/**
	 * Tunnel SSH
     *
	 */
	public void startOrStopTunnel(Activity activity) {
		if (SkStatus.isTunnelActive()) {
			TunnelManagerHelper.stopSocksHttp(activity);
		}
		else {
			// oculta teclado se vísivel, tá com bug, tela verde
			//Utils.hideKeyboard(activity);
            if (mConfig.getPrivString(Settings.SERVIDOR_KEY).isEmpty() || mConfig.getPrivString(Settings.SERVIDOR_PORTA_KEY).isEmpty()) {
                SkStatus.updateStateString("USER_VPN_PASSWORD_CANCELLED", "", R.string.state_user_vpn_password_cancelled,
                                           ConnectionStatus.LEVEL_NOTCONNECTED);
			}
            _pos_(serverSpinner.getSelectedItemPosition(),payloadSpinner.getSelectedItemPosition());
			Intent intent = new Intent(activity, LaunchVpn.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			activity.startActivity(intent);
		}
	}
    String getName(String str){
      return mConfig.getPrivString(str);  
    }
    private synchronized void _pos_(int pos, int pos1) {
        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();
            prefs.getBoolean(Settings.CUSTOM_PAYLOAD_KEY, true);
            edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, !true);
			String ssh_name = config.getServersArray().getJSONObject(pos).getString("Name");
            String ssh_server = config.getServersArray().getJSONObject(pos).getString("ServerIP");
            String remote_proxy = config.getServersArray().getJSONObject(pos).getString("ProxyIP");
            String proxy_port = config.getServersArray().getJSONObject(pos).getString("ProxyPort");
            String user = config.getServersArray().getJSONObject(pos).getString("ServerUser");
            String pass = config.getServersArray().getJSONObject(pos).getString("ServerPass");
            String chvKey = config.getServersArray().getJSONObject(pos).getString("SlowDnskey");
            String nvKey = config.getServersArray().getJSONObject(pos).getString("SlowDnshost");
            String cfz = config.getServersArray().getJSONObject(pos).getString("CloudfrontIP");
            String proxycum = config.getNetworksArray().getJSONObject(pos1).getString("CustomProxy");
            String proxyporn = config.getNetworksArray().getJSONObject(pos1).getString("CustomProxyPort");
            boolean CustomProxyBold = config.getNetworksArray().getJSONObject(pos1).getBoolean("isCustom");
            boolean directModeType = config.getNetworksArray().getJSONObject(pos1).getBoolean("isSSL");
            boolean sshssltype =  config.getNetworksArray().getJSONObject(pos1).getBoolean("isSslPayRp");
            boolean slowdnstype = config.getNetworksArray().getJSONObject(pos1).getBoolean("SlowDns");
            edit.putString(Settings.SERVIDOR_KEY, ssh_server);
			edit.putString("ServerName", ssh_name);
            if (CustomProxyBold == true){
                edit.putString(Settings.PROXY_IP_KEY, VPNUtils.IsangTangangNagDecrypt(proxycum).replace("[HOST]", config.getServersArray().getJSONObject(pos).getString("ServerIP")));
                edit.putString(Settings.PROXY_PORTA_KEY, proxyporn);          
            }
            else {
                edit.putString(Settings.PROXY_IP_KEY, remote_proxy);
                edit.putString(Settings.PROXY_PORTA_KEY, proxy_port);
            }
            if (directModeType) {
                String ssl_port = config.getServersArray().getJSONObject(pos).getString("SSLPort");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port);
            } else if (sshssltype) {
                String ssl_port1 = config.getServersArray().getJSONObject(pos).getString("SSLPort");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port1);
            } else if (slowdnstype) {
                edit.putString(Settings.SERVIDOR_KEY, "127.0.0.1");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, "2222");
            } else {
                String ssh_port = config.getServersArray().getJSONObject(pos).getString("ServerPort");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssh_port);
            }
            edit.apply();
            edit.putString(Settings.USUARIO_KEY, VPNUtils.IsangTangangNagDecrypt(user));
            edit.putString(Settings.SENHA_KEY, VPNUtils.IsangTangangNagDecrypt(pass));
            if (cfz.isEmpty()){
            } else {
                edit.putString("CloudfrontIP", cfz);
            }
            if (chvKey.isEmpty()){
            } else {
                edit.putString(Settings.CHAVE_KEY, chvKey);
            }
            if (nvKey.isEmpty()){
            } else {
                edit.putString(Settings.NAMESERVER_KEY, nvKey);
            }
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Server Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            //SocksHttpApp.toast(getApplicationContext(), R.color.red, e.getMessage());
        }

        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();
            prefs.getBoolean(Settings.CUSTOM_PAYLOAD_KEY, true);
            edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, !true);
            boolean directModeType = config.getNetworksArray().getJSONObject(pos1).getBoolean("isSSL");
            boolean sshssltype =  config.getNetworksArray().getJSONObject(pos1).getBoolean("isSslPayRp");
            boolean slowdnstype = config.getNetworksArray().getJSONObject(pos1).getBoolean("SlowDns");
			boolean FuckingCloudfront = config.getNetworksArray().getJSONObject(pos1).getBoolean("isCf");
            if (directModeType) {
                edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true).apply();
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSL_TLS).apply();
                String sni = config.getNetworksArray().getJSONObject(pos1).getString("SNI");
                String payload = VPNUtils.IsangTangangNagDecrypt(config.getNetworksArray().getJSONObject(pos1).getString("Payload"));
                edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload.replace("app.shandevz.tk", prefs.getString("CloudfrontIP", "")));
                if (FuckingCloudfront){
                    edit.putString(Settings.CUSTOM_SNI, prefs.getString("CloudfrontIP", ""));
                } else {
                    edit.putString(Settings.CUSTOM_SNI, sni);
                }
                edit.apply();
            } else if (sshssltype) {
                String payload = VPNUtils.IsangTangangNagDecrypt(config.getNetworksArray().getJSONObject(pos1).getString("Payload"));
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
                String snissl = config.getNetworksArray().getJSONObject(pos1).getString("SNI");
                if (FuckingCloudfront){
                    edit.putString(Settings.CUSTOM_SNI, prefs.getString("CloudfrontIP", ""));
                } else {
                    edit.putString(Settings.CUSTOM_SNI, snissl);
                }
                if (FuckingCloudfront){
                    edit.putString(Settings.CUSTOM_PAYLOAD_KEY, VPNUtils.cloudfront_payload(prefs));
                } else {
                    edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload.replace("[HOST]", getName(Settings.SERVIDOR_KEY)));
                }
                edit.apply();
            }else if (slowdnstype){
                edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true).apply();
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SLOWDNS).apply();
                String dnsKey = config.getNetworksArray().getJSONObject(pos1).getString("dnsKey");
				if (Settings.CUSTOM_PAYLOAD_KEY == null){
					edit.putString(Settings.CUSTOM_PAYLOAD_KEY, "[]").apply();
				}
				edit.putString(Settings.DNS_KEY, dnsKey);
                edit.apply();
            } else {
                String payload = VPNUtils.IsangTangangNagDecrypt(config.getNetworksArray().getJSONObject(pos1).getString("Payload"));
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();
                if (FuckingCloudfront){
                    edit.putString(Settings.CUSTOM_PAYLOAD_KEY, VPNUtils.cloudfront_payload(prefs));
                } else {
                    edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload.replace("[HOST]", getName(Settings.SERVIDOR_KEY)));
                }
                edit.apply();
            }
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Payload Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            //SocksHttpApp.toast(getApplicationContext(), R.color.red, e.getMessage());
        }
    }
    private void updateConfig(final boolean isOnCreate) {
        new ConfigUpdate(this, new ConfigUpdate.OnUpdateListener() {
            @Override
            public void onUpdateListener(String result) {
                try {
                    if (!result.contains("Error on getting data")) {

                        // ถอดรหัส
                        String json_data = SimpleCodec.decode(config.PASSWORD, result);

                        if (json_data == null || json_data.isEmpty()) {
                            json_data = result; // fallback
                        }

                        // ตรวจเวอร์ชันใหม่
                        if (isNewVersion(json_data)) {
                            newUpdateDialog(json_data);   // ส่ง JSON ดิบ
                        } else if (!isOnCreate) {
                            noUpdateDialog();
                        }

                    } else if (!isOnCreate) {
                        errorUpdateDialog(result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start(isOnCreate);
    }
    private boolean isNewVersion(String jsonDecoded) {
        try {
            String current = config.getVersion();
            String update = new JSONObject(jsonDecoded).getString("Version");
            return config.versionCompare(update, current);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void newUpdateDialog(final String json_data) throws JSONException {

        // อ่าน ReleaseNotes จาก JSON ดิบ
        String notes = new JSONObject(json_data).getString("ReleaseNotes");

        View inflate = LayoutInflater.from(this).inflate(R.layout.notification, null);
        MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(this);
        builer.setView(inflate);

        MaterialTextView title = inflate.findViewById(R.id.notiftext1);
        MaterialTextView ms = inflate.findViewById(R.id.confimsg);
        MaterialTextView ok = inflate.findViewById(R.id.appButton1);
        MaterialTextView cancel = inflate.findViewById(R.id.appButton2);

        title.setText("New Update Available");
        ms.setText(notes);
        ok.setText("Restart");
        cancel.setText("Dismiss");
        cancel.setVisibility(View.VISIBLE);

        final AlertDialog alert = builer.create();
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
        alert.getWindow().setGravity(Gravity.CENTER);

        ok.setOnClickListener(v -> {
            try {
                // บันทึก JSON ใหม่
                File file = new File(getFilesDir(), "Config.json");
                OutputStream out = new FileOutputStream(file);
                out.write(json_data.getBytes());
                out.flush();
                out.close();

                // บันทึกเวลา UpdateTime ตอนนี้
                String timeNow = VPNUtils.getTime01(ShanDevzMainActivity.this);
                mConfig.getPrefsPrivate().edit()
                        .putString("UpdateTime", timeNow)
                        .apply();

                doRestart();
                alert.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cancel.setOnClickListener(v -> alert.dismiss());

        alert.show();
    }


    private void noUpdateDialog() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.notification, null);
        MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(this); 
        builer.setView(inflate); 
        MaterialTextView title = inflate.findViewById(R.id.notiftext1);
        MaterialTextView ms = inflate.findViewById(R.id.confimsg);
        MaterialTextView ok = inflate.findViewById(R.id.appButton1);
        MaterialTextView cancel = inflate.findViewById(R.id.appButton2);
        title.setText("No Update");
        ms.setText("Latest config version is already installed");
        ok.setText("Ok,Close");
        cancel.setText(".");
        cancel.setVisibility(View.GONE);
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
		alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
         
        alert.getWindow().setGravity(Gravity.CENTER); 
        ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View p1){
                    alert.dismiss();
                }
            });

        cancel.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {

                    alert.dismiss();
                }




            });
        alert.show();
    }
   private void clerd() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.notification, null);
        MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(this); 
        builer.setView(inflate); 
        MaterialTextView title = inflate.findViewById(R.id.notiftext1);
        MaterialTextView ms = inflate.findViewById(R.id.confimsg);
        MaterialTextView ok = inflate.findViewById(R.id.appButton1);
        MaterialTextView cancel = inflate.findViewById(R.id.appButton2);
        title.setText("Clear Data");
		ms.setText("You sure want to clear data? This cannot be undone!");
		ok.setText("Clear");
		cancel.setText("Cancel");
		cancel.setVisibility(View.VISIBLE);
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
		alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
        alert.getWindow().setGravity(Gravity.CENTER); 
        ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View p1){
                    		try {
						// clearing app data
						String packageName = getApplicationContext().getPackageName();
						Runtime runtime = Runtime.getRuntime();
						runtime.exec("pm clear "+packageName);
						Toast.makeText(getApplicationContext(), "Done! Reopen the app!", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
					}
                }
            });

        cancel.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {

                    alert.dismiss();
                }




            });
        alert.show();
    }
    
    private void errorUpdateDialog(String error) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.notification, null);
        MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(this); 
        builer.setView(inflate); 
        MaterialTextView title = inflate.findViewById(R.id.notiftext1);
        MaterialTextView ms = inflate.findViewById(R.id.confimsg);
        MaterialTextView ok = inflate.findViewById(R.id.appButton1);
        MaterialTextView cancel = inflate.findViewById(R.id.appButton2);
        title.setText("Error");
        ms.setText(error.replace(VPNUtils.ConfigUrl, "**********"));
        ok.setText("Ok,Close");
        cancel.setText(".");
        cancel.setVisibility(View.GONE);
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
		alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
         
        alert.getWindow().setGravity(Gravity.CENTER); 
        ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View p1){
                    alert.dismiss();
                }
            });

        cancel.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {

                    alert.dismiss();
                }




            });
        alert.show();
	}
    private void ipHuntz() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.notification, null);
        MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(this); 
        builer.setView(inflate); 
        MaterialTextView title = inflate.findViewById(R.id.notiftext1);
        final MaterialTextView ms = inflate.findViewById(R.id.confimsg);
        final MaterialTextView ok = inflate.findViewById(R.id.appButton1);
        MaterialTextView cancel = inflate.findViewById(R.id.appButton2);
        title.setText("IP Hunter");
        ms.setText("Check IP! - For GTM No Load");
        ok.setText("Hunt");
        cancel.setText("Close");
        cancel.setVisibility(View.VISIBLE);
        final AlertDialog alert = builer.create(); 
        alert.setCanceledOnTouchOutside(false);
		alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
       
        alert.getWindow().setGravity(Gravity.CENTER); 
        ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View p1){
                    ms.setText("Checking...");
                    ok.setText("Checking");
                    ok.setEnabled(false);
                    ipHuntez(ms,ok,"✓ Success! Magic IP","× Fail! Airplane Mode","× Something Went Wrong.");
                }
            });

        cancel.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {

                    alert.dismiss();
                }

            });
        alert.show();
	}
    private void ipHuntez(final MaterialTextView ms, final MaterialTextView ok
    , final String magic, final String fail, final String something){
           new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    try {
                        int l = 0;
                        URL whatismyip = new URL("http://noloadbalance.globe.com.ph");
                        try{        
                            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("104.16.213.74", 80));
                            HttpURLConnection connection = (HttpURLConnection) whatismyip.openConnection(proxy);
                            connection.setRequestMethod("GET");
                            connection.connect();
                            connection.getContentLength();
                            connection.setConnectTimeout(3000);
                            InputStream in = connection.getInputStream();
                            byte[] buffer = new byte[4096];
                            int countBytesRead;
                            while((countBytesRead = in.read(buffer)) != -1) {
                                l += countBytesRead;
                            }
                            in.markSupported();
                            if (l == 333){
                                ms.setText(magic);
                                SkStatus.logInfo(magic);
                                ok.setText("Check Again");
                                ok.setEnabled(true);
                                return;
                            }
                            if (connection.getResponseCode() == 200){
                                ms.setText(magic);
                                SkStatus.logInfo(magic);
                                ok.setText("Check Again");
                                ok.setEnabled(true);
                                return;
                            }
                            in.close();
                            ms.setText(fail);
                            SkStatus.logInfo(fail);
                            ok.setText("Check Again");
                            ok.setEnabled(true);
                        } catch (IOException e) {
                            ok.setText("Check Again");
                            ok.setEnabled(true);
                            ms.setText(something);
                            SkStatus.logInfo(something);
                        }
                    }catch (MalformedURLException e) {}}
            }, 1000);
    }
	public void setStarterButton(Button starterButton, Activity activity) {
		String state = SkStatus.getLastState();
		boolean isRunning = SkStatus.isTunnelActive();

		if (starterButton != null) {
			int resId;
			
			SharedPreferences prefsPrivate = new Settings(activity).getPrefsPrivate();

			if (ConfigParser.isValidadeExpirou(prefsPrivate
					.getLong(Settings.CONFIG_VALIDADE_KEY, 0))) {
				resId = R.string.expired;
				starterButton.setEnabled(false);

				if (isRunning) {
					startOrStopTunnel(activity);
				}
			}
			else if (prefsPrivate.getBoolean(Settings.BLOQUEAR_ROOT_KEY, false) &&
					ConfigParser.isDeviceRooted(activity)) {
			   resId = R.string.blocked;
			   starterButton.setEnabled(false);
			   Toast.makeText(activity, R.string.error_root_detected, Toast.LENGTH_SHORT)
					.show();
			   if (isRunning) {
				   startOrStopTunnel(activity);
			   }
			}
			else if (SkStatus.SSH_INICIANDO.equals(state)) {
				resId = R.string.stop;
                //vp.setCurrentItem(1);
				adsPopUp();
				starterButton.setEnabled(false);
			}
			else if (SkStatus.SSH_PARANDO.equals(state)) {
				resId = R.string.state_stopping;
				adsPopUp();
				starterButton.setEnabled(false);
			}
			else {
				resId = isRunning ? R.string.stop : R.string.start;
				starterButton.setEnabled(true);
			}
			starterButton.setText(resId);
		}
	}
	
	
	@Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
   }
	@Override
    public void updateState(final String state, String msg, int localizedResId, final ConnectionStatus level, Intent intent)
    {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                doUpdateLayout();

                //======= CONNECTED =======
                if (level.equals(ConnectionStatus.LEVEL_CONNECTED)) {
                    updateStartButton(true);
                    startLogoAnimation();
                    status.setText(R.string.state_connected);
                    //startStopwatch();   // ← เริ่มนับ
                    // เริ่มนับเวลาเมื่อเชื่อมต่อสำเร็จ
                    if (config.time()) {
                        start();
                    } else {
                        mButtonSet.setText("Disabled");
                        mButtonSet.setEnabled(false);
                    }
                }

                //======= DISCONNECTED / STOP / FAIL =======
                else if (level.equals(ConnectionStatus.LEVEL_NOTCONNECTED)
                        || level.equals(ConnectionStatus.UNKNOWN_LEVEL)
                        || level.equals(ConnectionStatus.LEVEL_AUTH_FAILED)) {
                    updateStartButton(false);
                    stopLogoAnimation();

                    status.setText(R.string.state_disconnected);

                    // หยุดเวลาเมื่อหลุด
                    stop();
                    //stopStopwatch();    // ← หยุด
                }

                //======= CONNECTING =======
                else if (level.equals(ConnectionStatus.LEVEL_CONNECTING_SERVER_REPLIED)) {
                    status.setText(R.string.state_auth);
                }
                else if (level.equals(ConnectionStatus.LEVEL_CONNECTING_NO_SERVER_REPLY_YET)) {
                    status.setText(R.string.state_connecting);
                }

                //======= NO NETWORK =======
                else if (level.equals(ConnectionStatus.LEVEL_NONETWORK)) {
                    status.setText(R.string.state_nonetwork);
                    stop(); // ไม่มีเน็ต → หยุดเวลา
                    //stopStopwatch();    // ← หยุด
                }
            }
        });
		
		switch (state) {
			case SkStatus.SSH_CONECTADO:
				// carrega ads banner
				if (adsBannerView != null && TunnelUtils.isNetworkOnline(ShanDevzMainActivity.this)) {
					adsBannerView.setAdListener(new AdListener() {
						@Override
						public void onAdLoaded() {
							if (adsBannerView != null && !isFinishing()) {
								adsBannerView.setVisibility(View.VISIBLE);
							}
						}
					});
					adsBannerView.postDelayed(new Runnable() {
						@Override
						public void run() {
							// carrega ads interestitial
							// ads banner
							if (adsBannerView != null && !isFinishing()) {
								adsBannerView.loadAd(new AdRequest.Builder()
									.build());
							}
						}
					}, 5000);
				}
			break;
		}
	}


	/**
	 * Recebe locais Broadcast
	 */

	private BroadcastReceiver mActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null)
                return;

            if (action.equals(UPDATE_VIEWS) && !isFinishing()) {
				doUpdateLayout();
			
				}
			}
        
    };


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
            case R.id.miSettings:
                Intent intentSettings = new Intent(this, ConfigGeralActivity.class);
                intentSettings.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intentSettings);
                break;
           case R.id.offlz:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 550);
               break;
		   case R.id.clipimport:
				View inflate = LayoutInflater.from(ShanDevzMainActivity.this).inflate(R.layout.notification, null);
				MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(ShanDevzMainActivity.this);
				builer.setView(inflate); 
				MaterialTextView title = inflate.findViewById(R.id.notiftext1);
				MaterialTextView ms = inflate.findViewById(R.id.confimsg);
				MaterialTextView ok = inflate.findViewById(R.id.appButton1);
				MaterialTextView cancel = inflate.findViewById(R.id.appButton2);
				title.setText("Import From ClipBoard");
				ms.setText("Do not forget to copy the config then click OK to confirm. Else if you copied wrong config, it will fail to open the app.");
				ok.setText("OK");
				cancel.setText("Cancel");
				cancel.setVisibility(View.VISIBLE);
				final AlertDialog alert = builer.create(); 
				alert.setCanceledOnTouchOutside(false);
		alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
				 
				alert.getWindow().setGravity(Gravity.CENTER); 
				ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View p1){
							try {
								String b = ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).getText().toString();
								File file = new File(getFilesDir(), "Config.json");
								OutputStream out = new FileOutputStream(file);
								out.write(b.getBytes());
								out.flush();
								out.close();
								mConfig.getPrefsPrivate().edit()
									.putString("UpdateTime", VPNUtils.getTime01(ShanDevzMainActivity.this)).apply();
								doRestart();
							} catch (IOException e) {
								e.printStackTrace();
								VPNUtils.ShanToast(ShanDevzMainActivity.this, R.drawable.wrong, e.getMessage());
							}
							alert.dismiss();
						}
					});

				cancel.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1) {
							alert.dismiss();
						}
					});
				alert.show();
				break;
        }
        adsPopUp();
        return false;
    }
	@SuppressLint("MissingSuperCall")
    @Override
	public void onBackPressed() {
		showExitDialog();
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 550)
        {
            if (resultCode == RESULT_OK) {
                try {
                    Uri uri = data.getData();
                    String intentData = importer(uri);
                    //String cipter = AESCrypt.decrypt(ConfigUtil.PASSWORD, intentData);
                    File file = new File(getFilesDir(), "Config.json");
                    OutputStream out = new FileOutputStream(file);
                    out.write(intentData.getBytes());
                    out.flush();
                    out.close();
					mConfig.getPrefsPrivate().edit()
						.putString("UpdateTime", VPNUtils.getTime01(ShanDevzMainActivity.this)).apply();
                    doRestart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String importer(Uri uri)
    {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
            reader.close();
        }
        catch (IOException e) {e.printStackTrace();}
        return builder.toString();
	} 

	public void loadz(){
		loadRewardedAd();
		View inflate = LayoutInflater.from(ShanDevzMainActivity.this).inflate(R.layout.notification, null);
	    builer = new MaterialAlertDialogBuilder(ShanDevzMainActivity.this);
		builer.setView(inflate); 
		MaterialTextView title = inflate.findViewById(R.id.notiftext1);
		MaterialTextView ms = inflate.findViewById(R.id.confimsg);
		ok = (MaterialTextView) inflate.findViewById(R.id.appButton1);
		cancel = (MaterialTextView) inflate.findViewById(R.id.appButton2);
		title.setText("Requesting Ads");
		ms.setText("Please wait while loading ads...");
		ok.setText("Claim Reward");
		cancel.setText("Cancel");
		cancel.setVisibility(View.VISIBLE);
		if (!mClaim){
			ok.setVisibility(View.GONE);
		}
	    alert = builer.create(); 
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().getAttributes().windowAnimations = R.style.Shan01;
		alert.getWindow().setGravity(Gravity.CENTER); 
		ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
					showRewardedVideo();
                    alert.dismiss();
					mClaim = false;
				}
			});
		alert.show();
		cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
					alert.dismiss();
				}
			});
		alert.show();
	}

    // Rewarded
    private void showRewardedVideo() {
        if (rewardedAd == null) {
            return;
        }

        rewardedAd.setFullScreenContentCallback(
			new FullScreenContentCallback() {
				@Override
				public void onAdShowedFullScreenContent() {
					// Called when ad is shown.
				}

				@Override
				public void onAdFailedToShowFullScreenContent(AdError adError) {
					// Called when ad fails to show.
					// Don't forget to set the ad reference to null so you
					// don't show the ad a second time.
					rewardedAd = null;
				}

				@Override
				public void onAdDismissedFullScreenContent() {
					// Called when ad is dismissed.
					// Don't forget to set the ad reference to null so you
					// don't show the ad a second time.
					rewardedAd = null;
					//btnTimer(false);
				}
			});
        rewardedAd.show(
			ShanDevzMainActivity.this,
			new OnUserEarnedRewardListener() {
				@Override
				public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
					addTime();
					VPNUtils.ShanToast(ShanDevzMainActivity.this, R.drawable.check, "2 hours added to your time!");
					btnTimer();
				}
			});
    }

    private void loadRewardedAd() {
        if (rewardedAd == null) {
            isLoading = true;
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(
				this,
				getString(R.string.SHAN_rewardedid),
				adRequest,
				new RewardedAdLoadCallback() {
					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error.
						Log.d(ShanDevzMainActivity.class.getSimpleName(), loadAdError.getMessage());
						rewardedAd = null;
						ShanDevzMainActivity.this.isLoading = false;
					}

					@Override
					public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
						ShanDevzMainActivity.this.rewardedAd = rewardedAd;
						ShanDevzMainActivity.this.isLoading = false;
						//	SocksHttpApp.toast(SocksHttpMainActivity.this, R.color.green, "Rewarded Ads Loaded");
						ok.setVisibility(View.VISIBLE);
						mClaim = true;
					}
				});
        }
    }
    // End Of Rewarded
	
	@Override
    public void onResume() {
        super.onResume();
		SkStatus.addStateListener(this);
		if (adsBannerView != null) {
			adsBannerView.resume();
		}
        if (!mTimerEnabled){
            resumeTime(); // resume time
		}
    }

	@Override
	protected void onPause()
	{
		super.onPause();
		SkStatus.removeStateListener(this);
		//rewardedAd.pause(this);
		if (adsBannerView != null) {
			adsBannerView.pause();
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		//mDrawer.onDestroy();
		LocalBroadcastManager.getInstance(this)
			.unregisterReceiver(mActivityReceiver);	
		if (adsBannerView != null) {
			adsBannerView.destroy();
		}
	}       
	public static void updateMainViews(Context context) {
		Intent updateView = new Intent(UPDATE_VIEWS);
		LocalBroadcastManager.getInstance(context)
			.sendBroadcast(updateView);
	}

    private void updateStartButton(boolean connected) {

        Button startBtn = findViewById(R.id.shan_start);

        if (connected) {
            startBtn.setText("Stop");
            startBtn.setBackgroundResource(R.drawable.button_stop);
        } else {
            startBtn.setText("Start");
            startBtn.setBackgroundResource(R.drawable.button_start);
        }
    }


    private void setTime(long milliseconds) {
        saved_ads_time = mTimeLeftInMillis + milliseconds;
        mTimeLeftInMillis = saved_ads_time;
        updateCountDownText();

    }
    private void saveTime(){
        SharedPreferences.Editor time_edit = prefs.edit();
        time_edit.putLong("SAVED_TIME", mTimeLeftInMillis);
        time_edit.commit();
    }

    private void resumeTime(){
        long saved_time = prefs.getLong("SAVED_TIME", 0);
        setTime(saved_time);
        // Use this code to continue time if app close accidentally while connected
        /**
         String state = SkStatus.getLastState();

         if (SkStatus.SSH_CONECTADO.equals(state)) {

         if (!mTimerRunning){
         startTimer();
         mConnected = true;
         }
         }**/
        mTimerEnabled = true;
    }
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                saveTime();
                updateCountDownText();
                long harshana = 172800000;
                if (saved_ads_time > harshana) {
                    mButtonSet.setEnabled(false);
                } else {
                    mButtonSet.setEnabled(true);
                }
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                pauseTimer();
                saved_ads_time = 0;
                // Code for auto stop vpn (sockshtttp)         
                Intent stopVPN = new Intent(ShanDevzService.TUNNEL_SSH_STOP_SERVICE);
                LocalBroadcastManager.getInstance(ShanDevzMainActivity.this)
                    .sendBroadcast(stopVPN);
                Toast.makeText(ShanDevzMainActivity.this, "Time expired! Click Add + Time to renew access!", Toast.LENGTH_LONG).show();

            }
        }.start();
        mTimerRunning = true;
    }

    private void btnTimer() {

        mBtnCountDown = new CountDownTimer(16000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftBtn = millisUntilFinished;
                mButtonSet.setEnabled(false);
                updateBtnText();
            }
            @Override
            public void onFinish() {
                mButtonSet.setEnabled(true);
                mButtonSet.setText("ADD + TIME");
            }

        }.start();

    }

    private void updateBtnText() {
        int seconds = (int) (mTimeLeftBtn / 1000) % 60;
        String timeLeftFormatted;
        if (seconds > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                                              "%02d", seconds);

            mButtonSet.setText(timeLeftFormatted + " SECS");

        }
    }
    private void updateCountDownText(){
        long hours = TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis);
        long hoursMillis = TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis - hoursMillis);
        long minutesMillis = TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis - hoursMillis - minutesMillis);

        String resultString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        // แสดงแค่ใน Time Credit (gags) ตรงการ์ดด้านบน
        mMaterialTextViewCountDown.setText(resultString);
    }

    private void start() {

        // ❗ หากมีเวลาเก่ามากกว่า 0 → ใช้เวลานั้น
        if (saved_ads_time > 0) {
            if (!mTimerRunning) {
                startTimer();
            }
            return;
        }

        // ❗ ถ้าไม่มีเวลาเลย ค่อยตั้งค่า default (10 นาที)
        setTime(10 * 60 * 1000);

        if (!mTimerRunning) {
            startTimer();
        }
    }

    private void stop(){
        if (mTimerRunning){
            pauseTimer();
        }
        mConnected = false;
    }

    private void addTime(){
        long time = 21600000;
        setTime(time);
        if (mTimerRunning){
            pauseTimer();
        }
        startTimer();
    }

    private void pauseTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mTimerRunning = false;
    }

    public void showExitDialog() {
		AlertDialog dialog = new MaterialAlertDialogBuilder(this).
			create();
		dialog.setTitle(getString(R.string.attention));
		dialog.setMessage(getString(R.string.alert_exit));

		dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.
				string.exit),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Utils.exitAll(ShanDevzMainActivity.this);
				}
			}
		);

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.
				string.minimize),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// minimiza app
					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME);
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(startMain);
				}
			}
		);

		dialog.show();
	}
	
}

