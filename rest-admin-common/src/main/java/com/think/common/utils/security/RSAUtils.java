package com.think.common.utils.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author think.wang
 * @version 1.0.0
 * @Title RSAUtils
 * @Package com.think.common.utils.security
 * @Description RSA加解密工具类
 * @date 2020/5/6
 */
public class RSAUtils {

    private static Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    private static final String KEY_ALGORITHM = "RSA";

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) {
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
            return outStr;
        } catch (Exception e) {
            logger.error("异常抛出：", e);
        }
        return null;
    }

    public static Pair<String, String> generateKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象
        String publicKeyTxt = getKeyString(publicKey);
        String privateKeyTxt = getKeyString(privateKey);
        Pair<String, String> keyMap = Pair.of(publicKeyTxt, privateKeyTxt);
        return keyMap;
    }

    private static String getKeyString(Key key) throws Exception {
        //编码返回字符串
        return new String(Base64.encodeBase64(key.getEncoded()));
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (Exception e) {
            logger.error("异常抛出：", e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7nJBDdKIz1buVn2uttFga7yOXxIWjfdbqHTzDtr1gF4mkVksRWMqpkR4r22G722+6MFre1+GlRaw+vA3EOEr8Y51cAr7zFNWEz3pyxHp+nbmIhwSqaEM2SgaT51iFFBlguvftvU36kJci2h7sGSIjEk3Kc8zbZ7pUU+cEgt4mtQIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALuckEN0ojPVu5Wfa620WBrvI5fEhaN91uodPMO2vWAXiaRWSxFYyqmRHivbYbvbb7owWt7X4aVFrD68DcQ4SvxjnVwCvvMU1YTPenLEen6duYiHBKpoQzZKBpPnWIUUGWC69+29TfqQlyLaHuwZIiMSTcpzzNtnulRT5wSC3ia1AgMBAAECgYAGQ3fdiKtRp7UXjbb+maLHY2yAyKLdjnn6XveEYTl5V8/UmLzw21IIzzt+o1hj+TOFt8+Q2QxDhEv33DdpcaevLswkKO3d/x3I9TTnQ1syMRKY9kglrHRWx4IUsIsWsZJ4pPRNdJ2ueJSXPHwiHXFBncHLrKu5AuSbWX9UZQHIgQJBAOfOsQpkBZYNQqWl7d7y+2T+i8TWbdGTDwtGjjNudBveq13rEiP8yGdsixJnvdg1hs0p0qRERHLNjO+cOKsKWA0CQQDPMRJGRnYJzlV4PNRtbobvKTLxyZ7MvdCugNFya1NZcEUb4uxW9gqVYsZ0e+hWDuKWLLFSLZl1cAbLaIcBsXdJAkEAuDdoNqu6TL8JAf9VhGJxKpenl6wY5f4KbJmLlI/oseVJJoATkIno8VgvRVcwZcxT7mOYRHcENn/nytvJpRO5WQJAbuNBd5EQbZEblz1fJ65G00sGc/dHYSaZwDs7XXRpaG243wLlIGmQIoF52y/RmfVfeaYzxNqYzgyF5mlsEBDt2QJANfx13mjJU5SeX2hIcRsC7dE50Sp3hpq7QDWi1a7+3gzph6atPC1TpXyP7w35IV+KYAvzhc0/I602eVz4mwm9LQ==";
        String message = "2019052400183698";
        String messageEn = encrypt(message, publicKey);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn, privateKey);
        System.out.println("还原后的字符串为:" + messageDe);
    }

}
