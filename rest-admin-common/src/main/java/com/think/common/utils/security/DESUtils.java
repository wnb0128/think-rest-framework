package com.think.common.utils.security;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author think.wang
 * @version 1.0.0
 * @Title DESUtils
 * @Package com.think.common.utils.security
 * @Description DES加解密工具类
 * @date 2020/5/6
 */
public class DESUtils {

    private static Logger logger = LoggerFactory.getLogger(DESUtils.class);
    private static final Integer DES_KEY_LEN = 16;
    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 生成DES秘钥，长度为16位
     *
     * @return
     * @throws Exception
     */
    public static String generateDesKey() throws Exception {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < DES_KEY_LEN; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * DES 算法 ECB 模式 PKCS5 填充方式加密
     *
     * @param source
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String source, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(1, secureKey, random);

            byte[] buf = cipher.doFinal(source.getBytes());
            return new String(Base64.encodeBase64(buf));
        } catch (Exception e) {
            logger.error("异常抛出：", e);
        }
        return null;
    }

    /**
     * DES 算法 ECB 模式 PKCS5 填充方式解密
     *
     * @param cryptograph
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String cryptograph, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(2, secureKey, random);
            byte[] buf = cipher.doFinal(Base64.decodeBase64(cryptograph.getBytes()));
            return new String(buf);
        } catch (Exception e) {
            logger.error("异常抛出：", e);
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String encryptKey = "2019052400183698";
        String url = "{\"mchId\":\"1002001\",\"ver\":\"2.5.0\",\"verno\":\"10\",\"productId\":\"1003\",\"deviceId\":\"EA9DB3D3-BD81-4955-92A5-6015C8AB26AE\",\"deviceType\":\"2\",\"channelId\":\"dyy001\",\"timestamp\":\"20190522135710\",\"sign\":\"e58450a7e75c605a09b7dd8a7bc459ee\"}";
        String s1 = DESUtils.encrypt(url, encryptKey);
        System.out.println("s1:" + s1);
        System.out.println("s2:" + DESUtils.decrypt(s1, encryptKey));
    }

}
