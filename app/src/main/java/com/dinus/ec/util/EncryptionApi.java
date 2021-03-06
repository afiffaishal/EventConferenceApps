package com.dinus.ec.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PHAP on 11/01/2016.
 */
public class EncryptionApi {

    public static String SCREET_KEY = "bluegaz";
    public static long token_time;
    public static final String KEY_TOKEN_CODE = "token_code";
    public static final String KEY_TOKEN_TIME = "token_time";

    public EncryptionApi() {
    }

    private static long getCurrentTime(){
        Date date = new Date();

        return date.getTime();
    }

    private static MessageDigest digester;

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String crypt() {
        token_time = getDate(getCurrentTime());
        String token_kode=SCREET_KEY+token_time;
        if (token_kode == null || token_kode.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        digester.update(token_kode.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        return hexString.toString();
    }

    public static Long getDate(Long lngConvert) {
        String save = "";
        SimpleDateFormat formatSave = new SimpleDateFormat("HHmm");
        save = formatSave.format(lngConvert);
        return Long.parseLong(save);
    }


}
