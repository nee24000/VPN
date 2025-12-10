package com.zennetvpn.ShanDevzService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import android.content.Context;

import com.zennetvpn.ShanDevzService.logger.SkStatus;

import android.os.Handler;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

//import com.ayothayavpn.ultrasshservice.aidl.IUltraSSHServiceInternal;
import android.annotation.TargetApi;
import android.os.Build;
import android.app.Notification;

import com.zennetvpn.ShanDevzService.logger.ConnectionStatus;

import androidx.annotation.NonNull;

import android.app.NotificationManager;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import android.app.PendingIntent;

import androidx.annotation.RequiresApi;

import com.zennetvpn.ShanDevzService.tunnel.TunnelUtils;
import com.zennetvpn.ShanDevzService.tunnel.TunnelManagerThread;

import android.content.BroadcastReceiver;

import com.zennetvpn.ShanDevzService.config.Settings;

import android.app.NotificationChannel;
import android.net.ConnectivityManager;

import com.zennetvpn.ShanDevzProject.ShanDevzMainActivity;

import android.net.NetworkInfo;

import android.net.Network;

import android.content.SharedPreferences;

import com.zennetvpn.ShanDevzService.tunnel.DNSTunnelThread;

import android.os.Vibrator;

import com.zennetvpn.pro.R;

public class ShanDevzService extends Service
        implements SkStatus.StateListener {
    private static final String TAG = ShanDevzService.class.getSimpleName();
    public static final String START_SERVICE = "com.ayothayavpn.sockshttp:startTunnel";
    private static final int PRIORITY_MIN = -2;
    private static final int PRIORITY_DEFAULT = 0;
    private static final int PRIORITY_MAX = 2;
    private boolean mNotificationShowing = false;
    private NotificationManager mNotificationManager;
    private Notification.Builder mNotifyBuilder = null;
    private Handler mHandler;
    private Settings mPrefs;
    private Thread mTunnelThread;
    private TunnelManagerThread mTunnelManager;
    private ConnectivityManager connMgr;
    private DNSTunnelThread mDnsThread;
    private Notification notification;
    private int notificationId;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");

        super.onCreate();

        mPrefs = new Settings(this);
        mHandler = new Handler();
        connMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);


        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        startTunnelBroadcast();

        SkStatus.addStateListener(this);

        if (intent != null && START_SERVICE.equals(intent.getAction()))
            return START_NOT_STICKY;

        String stateMsg = getString(SkStatus.getLocalizedState(SkStatus.getLastState()));
        showNotification(stateMsg,
                stateMsg, NOTIFICATION_CHANNEL_NEWSTATUS_ID, 0, ConnectionStatus.LEVEL_START, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                startTunnel();
            }
        }).start();

        //return Service.START_STICKY;
        return Service.START_NOT_STICKY;
    }


    /**
     * Tunnel
     */

    public synchronized void startTunnel() {

        SkStatus.updateStateString(SkStatus.SSH_INICIANDO, getString(R.string.starting_service_ssh));

        networkStateChange(this, true);

        SkStatus.logInfo(String.format("Local IP: %s", getIpPublic()));

        try {

            SharedPreferences prefs = mPrefs.getPrefsPrivate();
            int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);

            if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
                mPrefs.setBypass(true);
                mDnsThread = new DNSTunnelThread(this);
                mDnsThread.start();
            }
            mTunnelManager = new TunnelManagerThread(mHandler, this);
            mTunnelManager.setOnStopClienteListener(new TunnelManagerThread.OnStopCliente() {
                @Override
                public void onStop() {
                    endTunnelService();
                }
            });

            mTunnelThread = new Thread(mTunnelManager);
            mTunnelThread.start();

            SkStatus.logInfo("Tunnel Thread Started");

        } catch (Exception e) {
            SkStatus.logException(e);
            endTunnelService();
        }
    }

    public synchronized void stopTunnel() {
        SharedPreferences prefs = mPrefs.getPrefsPrivate();
        int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);

        if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
            mPrefs.setBypass(false);
            if (mDnsThread != null) {
                mDnsThread.interrupt();
            }
            mDnsThread = null;
        }
        if (mTunnelManager != null) {
            mTunnelManager.stopAll();

            networkStateChange(this, true);

            if (mTunnelThread != null) {

                mTunnelThread.interrupt();

                SkStatus.logInfo("Tunnel Thread Stopped");
            }

            mTunnelManager = null;
        }
    }

    protected String getIpPublic() {

        final NetworkInfo network = connMgr
                .getActiveNetworkInfo();

        if (network != null && network.isConnectedOrConnecting()) {
            return TunnelUtils.getLocalIpAddress();
        } else {
            return "Indisponivel";
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");

        super.onDestroy();

        stopTunnel();

        stopTunnelBroadcast();

        SkStatus.removeStateListener(this);
    }

    public void endTunnelService() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                stopForeground(true);
                mNotificationManager.cancel(notificationId);
                stopSelf();
                SkStatus.removeStateListener(ShanDevzService.this);
            }
        });
    }


    /**
     * Notificação
     */
    public static final String NOTIFICATION_CHANNEL_BG_ID = "openvpn_bg";
    public static final String NOTIFICATION_CHANNEL_NEWSTATUS_ID = "openvpn_newstat";
    public static final String NOTIFICATION_CHANNEL_USERREQ_ID = "openvpn_userreq";

    private void connected() {
        Vibrator vb_service = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb_service.vibrate(150);
    }

    private String lastChannel;

    private void showNotification(final String msg, String tickerText, @NonNull String channel, long when, ConnectionStatus status, Intent intent) {
        int icon = getIconByConnectionStatus(status);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels(mNotificationManager);
            mNotifyBuilder = new Notification.Builder(this, NOTIFICATION_CHANNEL_NEWSTATUS_ID);
        } else if (Build.VERSION.SDK_INT >= 28) {
            createNotificationChannels(mNotificationManager);
            mNotifyBuilder = new Notification.Builder(this, NOTIFICATION_CHANNEL_NEWSTATUS_ID);
        } else {
            mNotifyBuilder = new Notification.Builder(this);
        }
        mNotifyBuilder = new Notification.Builder(this)
                .setContentTitle("Connected to ➔ " + mPrefs.getPrivString("ServerName"))
                .setOnlyAlertOnce(true)
                .setOngoing(true);

        // Try to set the priority available since API 16 (Jellybean)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            addVpnActionsToNotification(mNotifyBuilder);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            lpNotificationExtras(mNotifyBuilder, Notification.CATEGORY_SERVICE);


        int priority;
        if (channel.equals(NOTIFICATION_CHANNEL_BG_ID))
            priority = PRIORITY_MIN;
        else if (channel.equals(NOTIFICATION_CHANNEL_USERREQ_ID))
            priority = PRIORITY_MAX;
        else
            priority = PRIORITY_DEFAULT;

        mNotifyBuilder.setSmallIcon(icon);
        mNotifyBuilder.setContentText(msg);

        if (status == ConnectionStatus.LEVEL_WAITING_FOR_USER_INPUT) {
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            mNotifyBuilder.setContentIntent(pIntent);
        } else {
            mNotifyBuilder.setContentIntent(getGraphPendingIntent(this));
        }

        if (when != 0)
            mNotifyBuilder.setWhen(when);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            jbNotificationExtras(priority, mNotifyBuilder);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //noinspection NewApi
            mNotifyBuilder.setChannelId(channel);
        }

        if (tickerText != null && !tickerText.equals(""))
            mNotifyBuilder.setTicker(tickerText);

        notification = mNotifyBuilder.build();

        notificationId = channel.hashCode();

        mNotificationManager.notify(notificationId, notification);
        mNotifyBuilder.getNotification();

        if (lastChannel != null && !channel.equals(lastChannel)) {
            // Cancel old notification
            mNotificationManager.cancel(lastChannel.hashCode());
        }

        lastChannel = channel;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void lpNotificationExtras(Notification.Builder nbuilder, String category) {
        nbuilder.setCategory(category);
        nbuilder.setLocalOnly(true);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void jbNotificationExtras(int priority,
                                      Notification.Builder nbuilder) {
        try {
            if (priority != 0) {
                Method setpriority = nbuilder.getClass().getMethod("setPriority", int.class);
                setpriority.invoke(nbuilder, priority);

                Method setUsesChronometer = nbuilder.getClass().getMethod("setUsesChronometer", boolean.class);
                setUsesChronometer.invoke(nbuilder, true);
            }

            //ignore exception
        } catch (NoSuchMethodException | IllegalArgumentException |
                 InvocationTargetException | IllegalAccessException e) {
            SkStatus.logException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addVpnActionsToNotification(Notification.Builder nbuilder) {

        Intent reconnectVPN = new Intent(this, MainReceiver.class);
        reconnectVPN.setAction(MainReceiver.ACTION_SERVICE_RESTART);
        PendingIntent reconnectPendingIntent = PendingIntent.getBroadcast(this, 0, reconnectVPN, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        nbuilder.addAction(R.drawable.ic_autorenew_black_24dp,
                getString(R.string.reconnect), reconnectPendingIntent);

        Intent disconnectVPN = new Intent(this, MainReceiver.class);
        disconnectVPN.setAction(MainReceiver.ACTION_SERVICE_STOP);
        PendingIntent disconnectPendingIntent = PendingIntent.getBroadcast(this, 0, disconnectVPN, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        nbuilder.addAction(R.drawable.ic_power_settings_new_black_24dp,
                getString(R.string.stop), disconnectPendingIntent);
    }

    private int getIconByConnectionStatus(ConnectionStatus level) {
        switch (level) {
            case LEVEL_CONNECTED:
                connected();
                return R.drawable.ic_cloud_black_24dp;
            case LEVEL_AUTH_FAILED:
            case LEVEL_NONETWORK:
            case LEVEL_NOTCONNECTED:
            case LEVEL_CONNECTING_NO_SERVER_REPLY_YET:
            case LEVEL_CONNECTING_SERVER_REPLIED:
            case UNKNOWN_LEVEL:
            default:
                return R.drawable.ic_cloud_off_black_24dp;
        }
    }

    public static PendingIntent getGraphPendingIntent(Context context) {
        int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_IMMUTABLE | 0 : 0;
        Intent intent = new Intent(context, ShanDevzMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent startLW = PendingIntent.getActivity(context, 0, intent, flags);

        return startLW;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannels(NotificationManager mNotifyBuilder) {
        CharSequence name = getString(R.string.channel_name_background);
        NotificationChannel mChannel = new NotificationChannel(ShanDevzService.NOTIFICATION_CHANNEL_BG_ID,
                name, NotificationManager.IMPORTANCE_MIN);

        mChannel.setDescription(getString(R.string.channel_description_background));
        mChannel.enableLights(false);

        mNotifyBuilder.createNotificationChannel(mChannel);

        name = getString(R.string.channel_name_status);
        mChannel = new NotificationChannel(ShanDevzService.NOTIFICATION_CHANNEL_NEWSTATUS_ID,
                name, NotificationManager.IMPORTANCE_LOW);

        mChannel.setDescription(getString(R.string.channel_description_status));
        mChannel.enableLights(true);

        mNotifyBuilder.createNotificationChannel(mChannel);


        name = getString(R.string.channel_name_userreq);
        mChannel = new NotificationChannel(ShanDevzService.NOTIFICATION_CHANNEL_USERREQ_ID,
                name, NotificationManager.IMPORTANCE_HIGH);
        mChannel.setDescription(getString(R.string.channel_description_userreq));
        mChannel.enableVibration(true);

        mNotifyBuilder.createNotificationChannel(mChannel);
    }


    @Override
    public void updateState(String state, String msg, int resid, ConnectionStatus level, Intent intent) {


        if (mTunnelThread == null && !mNotificationShowing)
            return;

        String channel = NOTIFICATION_CHANNEL_NEWSTATUS_ID;

        if (level == ConnectionStatus.LEVEL_CONNECTED) {
            channel = NOTIFICATION_CHANNEL_USERREQ_ID;
        }

        String stateMsg = getString(SkStatus.getLocalizedState(SkStatus.getLastState()));
        showNotification(stateMsg,
                stateMsg, channel, 0, level, null);
    }



    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network net) {
            SkStatus.logDebug("Available network");
        }

        @Override
        public void onLost(Network net) {
            SkStatus.logDebug("Network lost");
        }

        @Override
        public void onUnavailable() {
            SkStatus.logDebug("Network unavailable");
        }
    };

    public static final String TUNNEL_SSH_RESTART_SERVICE = ShanDevzService.class.getName() + "::restartservicebroadcast",
            TUNNEL_SSH_STOP_SERVICE = ShanDevzService.class.getName() + "::stopservicebroadcast";

    private void startTunnelBroadcast() {
        if (Build.VERSION.SDK_INT >= 24) {
            connMgr.registerDefaultNetworkCallback(networkCallback);
        }

        IntentFilter broadcastFilter = new IntentFilter();
        broadcastFilter.addAction(TUNNEL_SSH_STOP_SERVICE);
        broadcastFilter.addAction(TUNNEL_SSH_RESTART_SERVICE);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mTunnelSSHBroadcastReceiver, broadcastFilter);
    }

    private void stopTunnelBroadcast() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mTunnelSSHBroadcastReceiver);

        if (Build.VERSION.SDK_INT >= 24)
            connMgr.unregisterNetworkCallback(networkCallback);
    }

    private BroadcastReceiver mTunnelSSHBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action == null) {
                return;
            }

            if (action.equals(TUNNEL_SSH_RESTART_SERVICE)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mTunnelManager != null) {
                            mTunnelManager.reconnectSSH();
                        }
                    }
                }).start();
            } else if (action.equals(TUNNEL_SSH_STOP_SERVICE)) {
                endTunnelService();
            }
        }
    };

    private static String lastStateMsg;

    protected void networkStateChange(Context context, boolean showStatusRepetido) {
        String netstatestring;

        try {
            // deprecated in 29
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo == null) {
                netstatestring = "not connected";
            } else {
                String subtype = networkInfo.getSubtypeName();
                if (subtype == null)
                    subtype = "";
                String extrainfo = networkInfo.getExtraInfo();
                if (extrainfo == null)
                    extrainfo = "";
                netstatestring = String.format("%2$s %4$s to %1$s %3$s", networkInfo.getTypeName(),
                        networkInfo.getDetailedState(), extrainfo, subtype);
            }

        } catch (Exception e) {
            netstatestring = e.getMessage();
        }

        if (showStatusRepetido || !netstatestring.equals(lastStateMsg))
            SkStatus.logInfo(netstatestring);

        lastStateMsg = netstatestring;
    }
}
