package com.think.common.utils.security;

import java.security.MessageDigest;

/**
 * @author think.wang
 * @version 1.0.0
 * @Title MD5Utils
 * @Package com.think.common.utils.security
 * @Description MD5加解密工具类
 * @date 2020/5/6
 */
public class MD5Utils {

    private static String keyCode = "abc357hjgamegood";

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    public static String getMd5Sign(String gameId, String deviceId, String signedCode) {
        // MD5(gameId+code+”abc357hjgamegood” +deviceId)
        String srcString = gameId + signedCode + keyCode + deviceId;
        return MD5Encode(srcString, null);
    }

    public static String getInitMd5Sign(String deviceId, String keyCode, String signedCode) {
        String srcString = signedCode + keyCode + deviceId;
        return MD5Encode(srcString, null);
    }

    public static String getAdRequestMd5Sign(String deviceId, String keyCode, String signedCode) {
        String srcString = deviceId + keyCode + signedCode;
        return MD5Encode(srcString, null);
    }

    public static String getGatherRequestMd5Sign(String keyCode, String signedCode) {
        String srcString = keyCode + signedCode;
        return MD5Encode(srcString, null);
    }

    public static void main(String[] args) {
        System.out.println(getMd5Sign("600001", "2345678939", "20190524152658"));

        String verSign = getInitMd5Sign("867450023702847", "i12n2061ihjt", "20191216100825");
        System.out.println(verSign);
    }
}
