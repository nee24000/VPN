package com.zennetvpn.ShanDevzService.tunnel;

import android.content.Intent;
import android.os.Build;
import android.content.Context;
import com.zennetvpn.ShanDevzService.ShanDevzService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TunnelManagerHelper
{
	public static void startSocksHttp(Context context) {
        Intent startVPN = new Intent(context, ShanDevzService.class);
		
		if (startVPN != null) {
			TunnelUtils.restartRotateAndRandom();
			
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			//noinspection NewApi
                context.startService(startVPN);
            else
                context.startService(startVPN);
        }
    }
	
	public static void stopSocksHttp(Context context) {
		Intent stopTunnel = new Intent(ShanDevzService.TUNNEL_SSH_STOP_SERVICE);
		LocalBroadcastManager.getInstance(context)
			.sendBroadcast(stopTunnel);
	}
}
