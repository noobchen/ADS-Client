package com.android.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncrypUtil {

    public static String aaaa = "duew&^%5d54nc'KH";
    public static String bbbb = "AES";

    public static String encode(String in) {
        String hex = "";
        try {
            byte[] bytIn = in.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(aaaa.getBytes(), bbbb);
            Cipher cipher = Cipher.getInstance(bbbb);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] bytOut = cipher.doFinal(bytIn);
            hex = byte2hexString(bytOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hex;
    }

    public static String decode(String hex) {
        String rr = "";
        try {
            byte[] bytIn = hex2Bin(hex);
            SecretKeySpec skeySpec = new SecretKeySpec(aaaa.getBytes(), bbbb);
            Cipher cipher = Cipher.getInstance(bbbb);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] bytOut = cipher.doFinal(bytIn);
            rr = new String(bytOut);

        } catch (Exception e) {

        }

        return rr;
    }

    private static byte[] hex2Bin(String src) {
        if (src.length() < 1)
            return null;
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    private static String byte2hexString(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            strbuf.append(Integer.toString((buf[i] >> 4) & 0xf, 16) +
                    Integer.toString(buf[i] & 0xf, 16));
        }

        return strbuf.toString();
    }

}

