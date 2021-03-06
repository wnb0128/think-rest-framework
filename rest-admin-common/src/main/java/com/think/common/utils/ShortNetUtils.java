package com.think.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author ：thinkwang
 * @description：访问百度短链接生成短链接
 * @date ：Created in 2019/5/28 17:28
 * @version: V1.0.0
 */
public class ShortNetUtils {
    private static Logger log = LoggerFactory.getLogger(ShortNetUtils.class);
    public static int TIMEOUT = 30 * 1000;
    public static String ENCODING = "UTF-8";

    /**
     * 根据传入的url，通过访问百度短链接的接口， 将其转换成短的URL
     *
     * @param originURL
     * @return
     */
    public static String getShortURL(String originURL) {
        String tinyUrl = null;
        try {
            // 指定百度短链接的接口
            URL url = new URL("http://www.mynb8.com/api2/dwz");
            // 建立连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置连接超时时间为30秒
            connection.setConnectTimeout(TIMEOUT);
            // 设置请求模式为POST
            connection.setRequestMethod("GET");
            // 设置POST信息，这里为传入的原始URL
            String postData = URLEncoder.encode(originURL, "utf-8");
            // 输出原始的url
            connection.getOutputStream().write(
                    ("appkey=abc0c06081de9f5e3cf777341e5ada76&long_url=" + postData).getBytes());
            // 连接百度短视频接口
            connection.connect();
            // 获取返回的字符串
            String responseStr = getResponseStr(connection);
            log.info("response string: " + responseStr);
            // 在字符串里获取tinyUrl，即短链接
            tinyUrl = getValueByKey(responseStr, "tinyUrl");
            log.info("tinyUrl: " + tinyUrl);
            // 关闭链接
            connection.disconnect();
        } catch (IOException e) {
            log.error("getShortURL error:" + e.toString());
        }
        return tinyUrl;
    }

    /**
     * 通过HttpConnection 获取返回的字符串
     *
     * @param connection
     * @return
     * @throws IOException
     */
    private static String getResponseStr(HttpURLConnection connection) throws IOException {
        StringBuffer result = new StringBuffer();
        // 从连接中获取http状态码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 如果返回的状态码是OK的，那么取出连接的输入流
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
            String inputLine = "";
            while ((inputLine = reader.readLine()) != null) {
                // 将消息逐行读入结果中
                result.append(inputLine);
            }
        }
        // 将结果转换成String并返回
        return String.valueOf(result);
    }


    /**
     * JSON 依据传入的key获取value
     *
     * @param replyText
     * @param key
     * @return
     */
    private static String getValueByKey(String replyText, String key) {
        ObjectMapper mapper = new ObjectMapper();
        // 定义json节点
        JsonNode node;
        String targetValue = null;
        try {
            // 把调用返回的消息串转换成json对象
            node = mapper.readTree(replyText);
            // 依据key从json对象里获取对应的值
            targetValue = node.get(key).asText();
        } catch (JsonProcessingException e) {
            log.error("getValueByKey error:" + e.toString());
            e.printStackTrace();
        }
        return targetValue;
    }

    /**
     * 百度短链接接口
     *
     * @param args
     */
    public static void main(String[] args) {
        String shortUrl = getShortURL("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
        log.info("shortUrl ==== " + shortUrl);
    }
}
