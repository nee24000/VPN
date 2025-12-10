package com.zennetvpn.ShanDevzProject;
import android.content.Intent;
import android.os.Bundle;
import com.zennetvpn.pro.R;
import com.zennetvpn.ShanDevzProject.activities.BaseActivity;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.view.Gravity;
import android.os.Handler;
import android.os.Looper;
import com.zennetvpn.ShanDevzProject.util.ConfigUtil;
import android.view.View;
import android.widget.TextView;
import android.graphics.Typeface;
import android.graphics.Color;
/**
 * @author
 */
public class LauncherActivity extends BaseActivity
{
    private String reMod = "_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		String appName = "Ayothaya VPN".toLowerCase();
		String pckgName = "com.ayothaya.app".toLowerCase();
		if (!(((String) getPackageManager().getApplicationLabel(getApplicationInfo())).toLowerCase().equals(appName)
		&&getPackageName().toLowerCase().equals(pckgName))){reMod = String.valueOf(View.GONE);}else{reMod = "_";}
		final LinearLayout linearbase = new LinearLayout(this);
        final ImageView im = new ImageView(this);
        linearbase.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearbase.setOrientation(LinearLayout.VERTICAL);
        linearbase.setGravity(Gravity.CENTER);
        setContentView(linearbase);
        im.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        im.setBackgroundResource(R.drawable.icon);
        final TextView tv = new TextView(this);
		tv.setText(getString(R.string.app_name));
		tv.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		tv.setTextColor(Color.parseColor("#ffffffff"));
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		tv.setPadding(0,10,0,0);
		linearbase.addView(im);
		linearbase.addView(tv);
       new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){                             
           @Override                             
           public void run(){ 									
               Intent intent = new Intent(LauncherActivity.this, ShanDevzMainActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); 									
               intent.putExtra("ReMod", reMod); 									
               startActivity(intent); 									
               finish(); 								
               }                         
               },ConfigUtil.dur);
    }
}
