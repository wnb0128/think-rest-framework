package com.think.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author think.wang
 * @version 1.0.0
 * @Title StringUtils
 * @Package com.think.common.utils
 * @Description 字符串操作
 * @date 2020/5/6
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    // 用于匹配手机号码
    private final static String REGEX_MOBILE_PHONE = "^0?1[3456789]\\d{9}$";

    // 用于匹配固定电话号码
    private final static String REGEX_FIXED_PHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";

    // 判断url地址
    private final static String REGEX_HTTP_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    //随机数
    private final static String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static Pattern PATTERN_MOBILE_PHONE;
    private static Pattern PATTERN_FIXED_PHONE;
    private static Pattern PATTERN_URL;

    static {
        PATTERN_FIXED_PHONE = Pattern.compile(REGEX_FIXED_PHONE);
        PATTERN_MOBILE_PHONE = Pattern.compile(REGEX_MOBILE_PHONE);
        PATTERN_URL = Pattern.compile(REGEX_HTTP_URL);
    }

    /**
     * 随机生成字符串
     *
     * @param length 生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(RANDOM_STR.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 对字符串的首字母大写转小写
     */
    public static String firstStrToLowerCase(final String str) {
        char[] chars = new char[1];
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z') {// 当为字母时，则转换为小写
            return str.replaceFirst(temp, temp.toLowerCase());
        }
        return str;
    }

    /**
     * 判断姓名是否正确（2到15位汉字）
     *
     * @param name 姓名
     * @return
     */
    public static boolean checkChineseName(String name) {
        return name.matches("^[\\u4e00-\\u9fa5·•]{2,15}");
    }

    /**
     * 判断是否为存数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是空：包括null，空。参考示例：<br>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 判断非空：包括null，空。与isEmpty()正好相反。参考示例：<br>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断是空：包括null，空，空格。参考示例：<br>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断非空：包括null，空，空格。与isNotBlank()正好相反。参考示例：<br>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断是否为手机号码
     *
     * @param number 手机号码
     * @return true为手机号码
     */
    public static boolean isCellPhone(String number) {
        if (!isEmpty(number)) {
            number = replacePhoneParam(number);
        }
        Matcher match = PATTERN_MOBILE_PHONE.matcher(number);
        return match.matches();
    }

    /**
     * 判断url是否正确
     *
     * @param url
     * @return
     */
    public static boolean chekUrl(String url) {
        Matcher match = PATTERN_URL.matcher(url);
        return match.matches();
    }

    /**
     * 去除号码的不必要的值
     *
     * @param phone
     * @return
     */
    public static String replacePhoneParam(String phone) {
        if (!isEmpty(phone)) {
            phone = phone.replaceAll(" ", "").replaceAll("-", "");
            // 去除0开头的号码
            if (phone.startsWith("0")) {
                phone = phone.substring(1);
                // 把前面的0全部去掉
                phone = replacePhoneParam(phone);
            }
            if (phone.startsWith("86")) {
                phone = phone.substring(2);
            }
            if (phone.startsWith("+86")) {
                phone = phone.substring(3);
            }
            if (phone.startsWith("0086")) {
                phone = phone.substring(4);
            }
            if (phone.startsWith("+0086")) {
                phone = phone.substring(5);
            }
        }
        return phone;
    }

    /**
     * 以脱敏格式化手机号
     *
     * @param phone
     * @return
     */
    public static String formatPhoneWithChar(String phone) {
        if (!isEmpty(phone) && phone.length() == 11) {
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phone;
    }

    /**
     * 判断是否为固定电话号码
     *
     * @param number 固定电话号码
     * @return true为固话
     */
    public static boolean isFixedPhone(String number) {
        if (!isEmpty(number)) {
            number = number.replaceAll(" ", "").replaceAll("-", "");
        }
        Matcher match = PATTERN_FIXED_PHONE.matcher(number);
        return match.matches();
    }

    /**
     * 判断是否包含的某些字符的
     *
     * @param ls
     * @param str
     * @return
     */
    public static boolean isExistType(List<String> ls, String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        for (String s : ls) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 队列比较
     *
     * @return false，则不相同，true全部相同
     */
    public static <T> boolean compareListSame(List<T> a, List<T> b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.size() != b.size()) {
            return false;
        }
        int same = 0;
        // 循环判断
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (a.get(i).hashCode() == b.get(j).hashCode()) {
                    same++;
                    break;
                }
            }
        }
        return same == a.size();
    }

    /**
     * 以脱敏格式化身份证号
     *
     * @param idCard
     * @return
     */
    public static String formatIDCardWithChar(String idCard) {
        if (!isEmpty(idCard)) {
            if (idCard.length() == 18) {
                return idCard.replaceAll("(\\d{6})\\d{8}(\\w{4})", "$1********$2");
            } else if (idCard.length() == 15) {
                return idCard.replaceAll("(\\d{6})\\d{6}(\\w{3})", "$1******$2");
            }
        }
        return idCard;
    }

    /**
     * 获取文件内容
     *
     * @param in
     * @param encode
     * @return
     */
    public static String getContent(InputStream in, String encode) {
        String mesage = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = in.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            mesage = new String(outputStream.toByteArray(), encode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mesage;
    }

    /**
     * 获取URL的文件名称
     *
     * @param url
     * @return
     */
    public static String getUrlName(String url) {
        String name = "";
        if (!StringUtils.isEmpty(url)) {
            String[] parts = url.split("/");
            if (parts != null) {
                name = parts[parts.length - 1];
            }
        }
        return name;
    }

    /**
     * 根据用户名的不同长度，来进行替换 ，达到保密效果
     *
     * @param userName 用户名
     * @return 替换后的用户名
     */
    public static String userNameReplace(String userName) {

        if (userName == null) {
            userName = "";
        }
        int nameLength = userName.length();
        if (nameLength <= 1) {
        } else if (nameLength == 2) {
            userName = userName.substring(0, 1) + "*";
        } else if (nameLength >= 3) {
            userName = userName.substring(0, 1) + "*" + userName.substring(userName.length() - 1);
        }
        return userName;
    }

    /**
     * 从地址中截取城市名
     *
     * @param area
     * @return
     */
    public static String getCityName(String area) {
        if (!StringUtils.isEmpty(area)) {
            try {
                int index = area.indexOf("省");
                if (index == -1) {
                    index = area.indexOf("自治区");
                }
                int endIndex = area.indexOf("市", index < 0 ? 0 : index);
                if (endIndex <= index) {
                    if (index < 0) {
                        area = area;
                    } else {
                        area = area.substring(index + 1);
                    }
                } else {
                    if (index < 0) {
                        area = area.substring(0, endIndex);
                    } else {
                        area = area.substring(index + 1, endIndex);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return area;
    }

    /**
     * 替换xml中不能使用的特殊字符
     *
     * @param txt
     * @return
     */
    public static String filterSpecialCharOfXml(String txt) {
        if (isEmpty(txt)) {
            return "";
        }
        String res = "";
        for (int i = 0; i < txt.length(); ++i) {
            char ch = txt.charAt(i);
            if (Character.isDefined(ch) && ch != '&' && ch != '<' && ch != '>' && ch != '\'' && ch != '\"'
                    && !Character.isHighSurrogate(ch) && !Character.isISOControl(ch) && !Character.isLowSurrogate(ch)) {
                res = res + ch;
            }
        }
        return res;
    }

    /**
     * 替换模板内容中的关键字
     *
     * @param tmplcontent
     * @param paramMap
     * @return
     */
    public static String replaceKeyword(String tmplcontent, Map<String, String> paramMap) {
        if (StringUtils.isEmpty(tmplcontent) || paramMap == null) {
            return "";
        }
        // 替换[关键字]，组装真实文本内容
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (StringUtils.isEmpty(entry.getKey())) {
                continue;
            }
            String searchKeyA = "#" + entry.getKey() + "#";// 格式：#code#
            if (tmplcontent.indexOf(searchKeyA.toLowerCase()) != -1) {
                tmplcontent = tmplcontent.toLowerCase().replaceAll(escapeExprSpecialWord(searchKeyA.toLowerCase()),
                        entry.getValue());
            }
            String searchKeyB = "${" + entry.getKey() + "}";// 格式：${code}
            if (tmplcontent.indexOf(searchKeyB.toLowerCase()) != -1) {
                tmplcontent = tmplcontent.toLowerCase().replaceAll(escapeExprSpecialWord(searchKeyB.toLowerCase()),
                        entry.getValue());
            }
        }
        return tmplcontent;
    }

    /**
     * 转义正则特殊字符：\$()*+.[]?^{},|
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return "";
        }
        String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
        for (String key : fbsArr) {
            if (keyword.contains(key)) {
                keyword = keyword.replace(key, "\\" + key);
            }
        }
        return keyword;
    }

    /**
     * 获取随机数
     *
     * @param startNum
     * @param endNum
     * @return
     */
    public static Integer getRandomNum(Integer startNum, Integer endNum) {
        Random rand = new Random();
        return rand.nextInt(endNum) + startNum;
    }

    /**
     * 以脱敏格式化名称
     *
     * @param name
     * @return
     */
    public static String formatNameWithChar(String name) {
        String formatName = name;
        if (!isEmpty(name)) {
            if (name.length() == 2) {
                formatName = formatName.replaceFirst(name.substring(1), "*");
            } else if (name.length() > 2) {
                formatName = formatName.replaceFirst(name.substring(1, name.length() - 1), "*");
            }
        }
        return formatName;
    }

    public static String getUrlHost(String url) {
        if (!chekUrl(url)) {
            return "";
        }
        try {
            URL urls = new URL(url);
            return urls.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断内容中关键字是否替换成功：true正确，false错误
     */
    public static boolean checkKeywrod(String realsmscontent) {
        if (StringUtils.isBlank(realsmscontent)) {
            return false;
        }
        String keywrodReg = "\\S*#[a-zA-Z0-9]{3,}#\\S*";
        return !Pattern.compile(keywrodReg).matcher(realsmscontent).matches();// 关键字没有替换成功
    }

    /**
     * 判断多个Map中是否存在空Map
     *
     * @param maps
     * @return
     */
    public static boolean isEmpty(Map<?, ?>... maps) {
        if (null == maps || maps.length < 1) {
            return true;
        }
        for (Map<?, ?> map : maps) {
            if (isEmpty(map)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Map是否不为空
     *
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 以脱敏格式化银行卡号
     *
     * @param bankCard
     * @return
     */
    public static String formatBankCardChar(String bankCard) {
        if (!isEmpty(bankCard)) {
            return bankCard.replaceAll("(\\d{4})\\d{8}(\\d{4})", "$1********$2");
        }
        return bankCard;
    }

    public static void main(String[] args) {
        System.out.println(formatIDCardWithChar("330724198807250731"));
        System.out.println(formatPhoneWithChar("13728788257"));
        System.out.println(formatNameWithChar("wnb"));
        System.out.println(formatBankCardChar("6222021307001157773"));
        //银行卡前4后四
        System.out.println("6222021307001157773".replaceAll("(\\d{4})\\d{8}(\\d{4})", "$1********$2"));
    }
}
