package com.gzkemays.mushroom_sign.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MuVipCodeUtils {
    private static ThreadLocal<String> tlSDF = new ThreadLocal<>();
    public static String YZJ_KEY = "YZJ";

    /**
     * @author gzkemays
     * @since 2020-07-30
     * 修订版 MD5
     */
    public static String adminGetVipCode(String auth){
        MessageDigest md5 = null;
        tlSDF.set(YZJ_KEY + new SimpleDateFormat("yyyyMMddss").format(new Date()) + auth);
        String inStr = tlSDF.get();
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] bytesArray = new byte[charArray.length];
        int j = 0;
        for(int i = charArray.length ;i>0 ;i--){
            bytesArray[j] = (byte) charArray[i-1];
            j++;
        }
        byte[] md5Bytes = md5.digest(bytesArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 50){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
