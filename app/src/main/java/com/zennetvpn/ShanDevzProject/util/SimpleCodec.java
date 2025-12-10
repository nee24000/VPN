package com.zennetvpn.ShanDevzProject.util;

import android.util.Base64;

public class SimpleCodec {

    private static final String CHARSET = "UTF-8";

    // เข้ารหัสแบบ XOR + Base64
    public static String encode(String key, String data) {
        try {
            byte[] bytes = data.getBytes(CHARSET);
            byte[] keyBytes = key.getBytes(CHARSET);
            byte[] out = new byte[bytes.length];

            for (int i = 0; i < bytes.length; i++) {
                out[i] = (byte) (bytes[i] ^ keyBytes[i % keyBytes.length]);
            }

            return Base64.encodeToString(out, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
    }

    // ถอดรหัสแบบ XOR + Base64
    public static String decode(String key, String encoded) {
        try {
            byte[] bytes = Base64.decode(encoded, Base64.NO_WRAP);
            byte[] keyBytes = key.getBytes(CHARSET);
            byte[] out = new byte[bytes.length];

            for (int i = 0; i < bytes.length; i++) {
                out[i] = (byte) (bytes[i] ^ keyBytes[i % keyBytes.length]);
            }

            return new String(out, CHARSET);
        } catch (Exception e) {
            return null;
        }
    }
}
