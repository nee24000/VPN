package com.zennetvpn.ShanDevzService.util;

import android.content.Context;

import com.zennetvpn.pro.R;

/**
 * @author Skank3r
 */
public class SkProtect {

	private static final String TAG = SkProtect.class.getSimpleName();
	
	private static final String APP_BASE = new String(new byte[]{104,116,116,112,116,117,110,110,101,108,46,104,97,107,100,111,103});
    
    private static final String APP_NAME = new String(new byte[]{104,116,116,112,32,116,117,110,110,101,108});


	private static SkProtect mInstance;

	private Context mContext;
	
	public static void init(Context context) {
		if (mInstance == null) {
			mInstance = new SkProtect(context);

		}
	}

	private SkProtect(Context context) {
		mContext = context;
	}


    public void simpleProtect() {
        if (!APP_BASE.equals(mContext.getPackageName().toLowerCase()) ||
            !mContext.getString(R.string.app_name).toLowerCase().equals(APP_NAME)) {
            throw new RuntimeException();
        }
	}

	public static void CharlieProtect() {
		if (mInstance == null) return;
			
		mInstance.simpleProtect();

	}
}
