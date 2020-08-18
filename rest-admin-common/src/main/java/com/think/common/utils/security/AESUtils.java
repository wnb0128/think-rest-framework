package com.think.common.utils.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author think.wang
 * @version 1.0.0
 * @Title AESUtils
 * @Package com.think.common.utils.security
 * @Description AES加解密工具类
 * @date 2020/5/6
 */
public class AESUtils {

    private static Logger logger = LoggerFactory.getLogger(AESUtils.class);
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES加密操作
     *
     * @param content 待加密内容
     * @param key     加密Key
     * @return
     */
    public static String encrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            return Base64.encodeBase64String(result);//通过Base64转码返回
        } catch (Exception e) {
            logger.error("异常抛出：", e);
        }
        return null;
    }

    /**
     * AES解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //使用密钥初始化，设置为解密模式
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("异常抛出：", e);
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String encryptKey = "2019052400183698";
        String url = "productId=1003&channelId=qsjh5001&deviceId=EA9DB3D3-BD81-4955-92A5-6015C8AB26AE&deviceType=2&ver=&verno=10&timestamp=20190522135710&sign=e7b1c0e73bafa3b90d0d467d291219da";
        String s1 = AESUtils.encrypt(url, encryptKey);
        System.out.println("s1:" + s1);
        System.out.println("s2:" + AESUtils.decrypt(s1, encryptKey));
    }
}
