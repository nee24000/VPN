package com.zennetvpn.ShanDevzProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

    private final Activity myContext;
    public String romz = (new String(android.util.Base64.decode(new String(android.util.Base64.decode(new Object() {
        int spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk;
        public String toString() {
            byte[] buf = new byte[28];
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1251589592;
            buf[0] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 20);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 1029152427;
            buf[1] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 18);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -690805372;
            buf[2] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 11);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -228536155;
            buf[3] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 10);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 641893143;
            buf[4] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 3);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1096595754;
            buf[5] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 8);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1108075719;
            buf[6] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 6);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1419588413;
            buf[7] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 11);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1434272568;
            buf[8] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 21);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 823357430;
            buf[9] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 11);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -395895271;
            buf[10] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 6);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 1151664950;
            buf[11] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 15);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 1009822372;
            buf[12] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 1);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -634563081;
            buf[13] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 10);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1126881167;
            buf[14] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 18);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -546236140;
            buf[15] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 7);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 942453654;
            buf[16] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 9);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1302929989;
            buf[17] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 12);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 1300145954;
            buf[18] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 22);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 300063656;
            buf[19] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 11);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1817149211;
            buf[20] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 15);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1869988583;
            buf[21] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 2);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -1895386398;
            buf[22] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 12);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 1739342576;
            buf[23] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 20);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -64078166;
            buf[24] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 1);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = -764149592;
            buf[25] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 1);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 2057921722;
            buf[26] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 10);
            spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk = 743718175;
            buf[27] = (byte) (spkspkspkspkspkspkspkspkspkspkspkspkspskpskpskpspkspkspkspkspkspkspkspkspkspkspk >>> 8);
            return new String(buf);
        }
    }.toString().getBytes(), android.util.Base64.DEFAULT)).getBytes(), android.util.Base64.DEFAULT)) + new String(android.util.Base64.decode(new String(android.util.Base64.decode(new Object() {
        int mymymymymymymymymymymymymymymymymymymymymy;
        public String toString() {
            byte[] buf = new byte[32];
            mymymymymymymymymymymymymymymymymymymymymy = 445711702;
            buf[0] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 2);
            mymymymymymymymymymymymymymymymymymymymymy = 1984731232;
            buf[1] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 7);
            mymymymymymymymymymymymymymymymymymymymymy = -1919874662;
            buf[2] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 18);
            mymymymymymymymymymymymymymymymymymymymymy = 444064315;
            buf[3] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 3);
            mymymymymymymymymymymymymymymymymymymymymy = -1513730032;
            buf[4] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 12);
            mymymymymymymymymymymymymymymymymymymymymy = -1677542694;
            buf[5] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 11);
            mymymymymymymymymymymymymymymymymymymymymy = -764704103;
            buf[6] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 19);
            mymymymymymymymymymymymymymymymymymymymymy = -435215250;
            buf[7] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 13);
            mymymymymymymymymymymymymymymymymymymymymy = 708915426;
            buf[8] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 7);
            mymymymymymymymymymymymymymymymymymymymymy = -1109771202;
            buf[9] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 11);
            mymymymymymymymymymymymymymymymymymymymymy = 1988575279;
            buf[10] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 20);
            mymymymymymymymymymymymymymymymymymymymymy = -1596902224;
            buf[11] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 7);
            mymymymymymymymymymymymymymymymymymymymymy = 1086632847;
            buf[12] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 17);
            mymymymymymymymymymymymymymymymymymymymymy = -245910775;
            buf[13] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 18);
            mymymymymymymymymymymymymymymymymymymymymy = 1716891473;
            buf[14] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 14);
            mymymymymymymymymymymymymymymymymymymymymy = -1508449794;
            buf[15] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 12);
            mymymymymymymymymymymymymymymymymymymymymy = 349998262;
            buf[16] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 22);
            mymymymymymymymymymymymymymymymymymymymymy = -895404661;
            buf[17] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 21);
            mymymymymymymymymymymymymymymymymymymymymy = -173864217;
            buf[18] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 20);
            mymymymymymymymymymymymymymymymymymymymymy = 1474931982;
            buf[19] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 2);
            mymymymymymymymymymymymymymymymymymymymymy = 1989249577;
            buf[20] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 10);
            mymymymymymymymymymymymymymymymymymymymymy = 1758332022;
            buf[21] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 4);
            mymymymymymymymymymymymymymymymymymymymymy = 870964814;
            buf[22] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 13);
            mymymymymymymymymymymymymymymymymymymymymy = -1916581423;
            buf[23] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 2);
            mymymymymymymymymymymymymymymymymymymymymy = -415988370;
            buf[24] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 4);
            mymymymymymymymymymymymymymymymymymymymymy = 1826333971;
            buf[25] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 14);
            mymymymymymymymymymymymymymymymymymymymymy = 892548695;
            buf[26] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 7);
            mymymymymymymymymymymymymymymymymymymymymy = 1187727412;
            buf[27] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 13);
            mymymymymymymymymymymymymymymymymymymymymy = -357926335;
            buf[28] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 21);
            mymymymymymymymymymymymymymymymymymymymymy = -1923866532;
            buf[29] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 16);
            mymymymymymymymymymymymymymymymymymymymymy = -1188698619;
            buf[30] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 5);
            mymymymymymymymymymymymymymymymymymymymymy = 959511450;
            buf[31] = (byte) (mymymymymymymymymymymymymymymymymymymymymy >>> 24);
            return new String(buf);
        }
    }.toString().getBytes(), android.util.Base64.DEFAULT)).getBytes(), android.util.Base64.DEFAULT)))+ " Telegram on @Zenze000";
    public ExceptionHandler(Activity activity) {
        this.myContext = activity;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("************ APPLICATION ERROR ************\n\n");
        stringBuilder.append(stringWriter.toString());
        stringBuilder.append("\n************ DEVICE INFORMATION ***********\n");
        stringBuilder.append("Brand: ");
        stringBuilder.append(Build.BRAND);
        stringBuilder.append("\n");
        stringBuilder.append("Device: ");
        stringBuilder.append(Build.DEVICE);
        stringBuilder.append("\n");
        stringBuilder.append("Model: ");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append("\n");
        stringBuilder.append("Id: ");
        stringBuilder.append(Build.ID);
        stringBuilder.append("\n");
        stringBuilder.append("Product: ");
        stringBuilder.append(Build.PRODUCT);
        stringBuilder.append("\n");
        stringBuilder.append("\n************ FIRMWARE ************\n");
        stringBuilder.append("SDK: ");
        stringBuilder.append(VERSION.SDK);
        stringBuilder.append("\n");
        stringBuilder.append("Release: ");
        stringBuilder.append(VERSION.RELEASE);
        stringBuilder.append("\n");
        stringBuilder.append("Incremental: ");
        stringBuilder.append(VERSION.INCREMENTAL);
        stringBuilder.append("\n");
        stringBuilder.append(romz);
        stringBuilder.append("\n");
        try {
            Intent intent = new Intent(this.myContext, Error.class);
            intent.putExtra("error", stringBuilder.toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.myContext.startActivity(intent);
            Process.killProcess(Process.myPid());
            System.exit(10);
        } catch (Throwable e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }
}



