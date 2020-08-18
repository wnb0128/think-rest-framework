package com.think.common.utils;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : think
 * @Description : 工具类:数据校验
 * @date : 2020年6月25日 上午11:12:23
 */
public final class ValidatorUtils {
    /**
     * 字符串:"null"
     */
    public static final String NULL = "null";
    /**
     * 字符串:"."
     */
    public static final String DOT = ".";
    /**
     * 字符串:"/"
     */
    public static final String SLASH = "/";
    /**
     * 字符串:"\r\n"
     */
    public static final String CRLF = "\r\n";
    /**
     * 字符串:"\n"
     */
    public static final String NEWLINE = "\n";
    /**
     * 字符串:"0"
     */
    public static final String ZERO = "0";
    /**
     * ASCII码:半角空白
     */
    public static final char ASCII_DBC_CASE_BLANK = 32;
    /**
     * ASCII码:全角空白
     */
    public static final char ASCII_SBC_CASE_BLANK = 12288;

    /**
     * 判断字符串是否为正整数
     *
     * @param str
     * @return
     */
    public static boolean isPositiveInteger(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^[1-9]\\d*$");
    }

    /**
     * 判断字符串是否不为正整数
     *
     * @param str
     * @return
     */
    public static boolean isNotPositiveInteger(String str) {
        return !isPositiveInteger(str);
    }

    /**
     * 判断字符串是否为正整数，并且正整数的大小不能超过指定范围
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isPositiveInteger(String str, int min, int max) {
        if (min < 1 || max < 1 || min > max || isNotPositiveInteger(str)) {
            return false;
        }
        int v = Integer.parseInt(str);
        return v >= min && v <= max;
    }

    /**
     * 判断字符串是否不为正整数，或者正整数的大小超过指定范围
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isNotPositiveInteger(String str, int min, int max) {
        return !isPositiveInteger(str, min, max);
    }

    /**
     * 判断字符串是否为正整数，并且正整数的大小不能超过最大值
     *
     * @param str
     * @param max
     * @return
     */
    public static boolean isPositiveInteger(String str, int max) {
        return !isNotPositiveInteger(str, max);
    }

    /**
     * 判断字符串是否不为正整数，或者正整数的大小超过最大值
     *
     * @param str
     * @param max
     * @return
     */
    public static boolean isNotPositiveInteger(String str, int max) {
        return max < 1 || isNotPositiveInteger(str) || Integer.parseInt(str) > max;
    }

    /**
     * 判断整数是否为正整数
     *
     * @param v
     * @return
     */
    public static boolean isPositiveInteger(Integer v) {
        return !isNotPositiveInteger(v);
    }

    /**
     * 判断整数是否不为正整数
     *
     * @param v
     * @return
     */
    public static boolean isNotPositiveInteger(Integer v) {
        return null == v || v < 1;
    }

    /**
     * 判断整数是否为正整数
     *
     * @param v
     * @return
     */
    public static boolean isPositiveInteger(Long v) {
        return !isNotPositiveInteger(v);
    }

    /**
     * 判断整数是否不为正整数
     *
     * @param v
     * @return
     */
    public static boolean isNotPositiveInteger(Long v) {
        return null == v || v < 1;
    }

    /**
     * 判断多个字符串至少有一个为正整数
     *
     * @param params
     * @return
     */
    public static boolean isPositiveInteger(String... params) {
        for (String tmp : params) {
            if (isPositiveInteger(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串中至少有一个不为正整数
     *
     * @param params
     * @return
     */
    public static boolean isNotPositiveInteger(String... params) {
        for (String tmp : params) {
            if (isNotPositiveInteger(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串是否都为正整数
     *
     * @param params
     * @return
     */
    public static boolean isAllPositiveInteger(String... params) {
        for (String tmp : params) {
            if (isNotPositiveInteger(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串是否都不为正整数
     *
     * @param params
     * @return
     */
    public static boolean isNotAllPositiveInteger(String... params) {
        for (String tmp : params) {
            if (isPositiveInteger(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^-?(0|[1-9]\\d*)$");
    }

    /**
     * 判断字符串是否不为整数
     *
     * @param str
     * @return
     */
    public static boolean isNotInteger(String str) {
        return !isInteger(str);
    }

    /**
     * 判断字符串是否为指定范围内的整数(取值范围:大于等于min或小于等于max)
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isInteger(String str, int min, int max) {
        if (isNotInteger(str)) {
            return false;
        }
        int v = Integer.parseInt(str);
        return v >= min && v <= max;
    }

    /**
     * 判断字符串是否不为指定范围内的整数(取值范围:大于等于min或小于等于max)
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isNotInteger(String str, int min, int max) {
        return !isInteger(str, min, max);
    }

    /**
     * 判断整数是否在指定范围内(取值范围:大于等于min或小于等于max)
     *
     * @param v
     * @param min
     * @param max
     * @return
     */
    public static boolean isInteger(Integer v, int min, int max) {
        return !isNotInteger(v, min, max);
    }

    /**
     * 判断整数是否不在指定范围内(取值范围:大于等于min或小于等于max)
     *
     * @param v
     * @param min
     * @param max
     * @return
     */
    public static boolean isNotInteger(Integer v, int min, int max) {
        return null == v || min >= max || v < min || v > max;
    }

    /**
     * 判断多个字符串中至少有一个为整数
     *
     * @param params
     * @return
     */
    public static boolean isInteger(String... params) {
        if (isEmptyIgnoreBlank(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isInteger(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串中至少有一个不为整数
     *
     * @param params
     * @return
     */
    public static boolean isNotInteger(String... params) {
        if (isEmptyIgnoreBlank(params)) {
            return true;
        }
        for (String tmp : params) {
            if (isNotInteger(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串是否都为整数
     *
     * @param params
     * @return
     */
    public static boolean isAllInteger(String... params) {
        if (isEmptyIgnoreBlank(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isNotInteger(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串是否都不为整数
     *
     * @param params
     * @return
     */
    public static boolean isNotAllInteger(String... params) {
        if (isEmptyIgnoreBlank(params)) {
            return true;
        }
        for (String tmp : params) {
            if (isInteger(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为非零的整数
     *
     * @param str
     * @return
     */
    public static boolean isIntegerByNotZero(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^-?[1-9]\\d*$");
    }

    /**
     * 判断字符串是否为小数
     *
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^-?(0|[1-9]\\d*)\\.\\d+$");
    }

    /**
     * 判断字符串是否不为小数
     *
     * @param str
     * @return
     */
    public static boolean isNotDecimal(String str) {
        return !isDecimal(str);
    }

    /**
     * 判断字符串是否为数字(整数或小数)
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^-?(0|[1-9]\\d*)(\\.\\d+)?$");
    }

    /**
     * 判断字符串是否不为数字(整数或小数)
     *
     * @param str
     * @return
     */
    public static boolean isNotNumeric(String str) {
        return !isNumeric(str);
    }

    /**
     * 判断多个字符串中至少有一个为数字(整数或小数)
     *
     * @param params
     * @return
     */
    public static boolean isNumeric(String... params) {
        if (isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isNumeric(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串中至少有一个不为数字(整数或小数)
     *
     * @param params
     * @return
     */
    public static boolean isNotNumeric(String... params) {
        if (isEmptyArray(params)) {
            return true;
        }
        for (String tmp : params) {
            if (isNotNumeric(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为有效的电子邮箱地址
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        String regex = "^(\\w)+([-|\\.]\\w+)*@[a-zA-Z0-9]\\w+\\.[a-z]{2,}(\\.[a-z]{2,})?$";
        return !isEmptyIgnoreBlank(str) && str.matches(regex);
    }

    /**
     * 判断字符串是否为无效的电子邮箱地址
     *
     * @param str
     * @return
     */
    public static boolean isNotEmail(String str) {
        return !isEmail(str);
    }

    /**
     * 判断字符串是否为有效的手机号
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^1\\d{10}$");
    }

    /**
     * 判断字符串是否为无效的手机号
     *
     * @param str
     * @return
     */
    public static boolean isNotMobile(String str) {
        return !isMobile(str);
    }

    /**
     * 判断字符串是否为有效的固定电话号码
     *
     * @param str
     * @return
     */
    public static boolean isTel(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^\\d{3}-\\d{8}|\\d{4}-\\d{7,8}$");
    }

    /**
     * 判断字符串是否为无效的固定电话号码
     *
     * @param str
     * @return
     */
    public static boolean isNotTel(String str) {
        return !isTel(str);
    }

    /**
     * 判断字符串是否为有效的邮政编码
     *
     * @param str
     * @return
     */
    public static boolean isPostCode(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^[1-9]\\d{5}$");
    }

    /**
     * 判断字符串是否为无效的邮政编码
     *
     * @param str
     * @return
     */
    public static boolean isNotPostCode(String str) {
        return !isPostCode(str);
    }

    /**
     * 判断字符串是否为有效的IP地址
     *
     * @param str
     * @return
     */
    public static boolean isIP(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches(
                "^(?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$");
    }

    /**
     * 判断字符串是否为无效的IP地址
     *
     * @param str
     * @return
     */
    public static boolean isNotIP(String str) {
        return !isIP(str);
    }

    /**
     * 判断字符串是否为空值(空值:null, "")
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || str.length() < 1;
    }

    /**
     * 判断字符串是否为非空值(空值:null, "")
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断多个字符串中是否存在空值(空值:null, "")
     *
     * @param params
     * @return
     */
    public static boolean isEmpty(String... params) {
        if (isEmptyArray(params)) {
            return true;
        }
        for (String tmp : params) {
            if (isEmpty(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串中是否存在非空值(空值:null, "")
     *
     * @param params
     * @return
     */
    public static boolean isNotEmpty(String... params) {
        if (isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isNotEmpty(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串是否都为空值(空值:null, "")
     *
     * @param params
     * @return
     */
    public static boolean isAllEmpty(String... params) {
        if (isEmptyArray(params)) {
            return true;
        }
        for (String tmp : params) {
            if (isNotEmpty(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串是否都不为空值(空值:null, "")
     *
     * @param params
     * @return
     */
    public static boolean isNotAllEmpty(String... params) {
        if (isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isEmpty(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空值(空值:null, "", "半角空白", "全角空白", "null")
     *
     * @param str
     * @return
     */
    public static boolean isEmptyIgnoreBlank(String str) {
        if (null == str) {
            return true;
        }
        str = str.trim();
        if (str.length() < 1 || NULL.equalsIgnoreCase(str)) {
            return true;
        }
        char[] c = str.toCharArray();
        int count = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ASCII_DBC_CASE_BLANK || c[i] == ASCII_SBC_CASE_BLANK) {
                count++;
            }
        }
        return count == c.length;
    }

    /**
     * 判断字符串是否为非空值(空值:null, "", "半角空白", "全角空白", "null")
     *
     * @param str
     * @return
     */
    public static boolean isNotEmptyIgnoreBlank(String str) {
        return !isEmptyIgnoreBlank(str);
    }

    /**
     * 判断多个字符串中是否存在空值(空值:null, "", "半角空白", "全角空白", "null")
     *
     * @param params
     * @return
     */
    public static boolean isEmptyIgnoreBlank(String... params) {
        if (isEmptyArray(params)) {
            return true;
        }
        for (String tmp : params) {
            if (isEmptyIgnoreBlank(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串中是否存在非空值(空值:null, "", "半角空白", "全角空白", "null")
     *
     * @param params
     * @return
     */
    public static boolean isNotEmptyIgnoreBlank(String... params) {
        if (isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isNotEmptyIgnoreBlank(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串是否都为空值(空值:null, "", "半角空白", "全角空白", "null")
     *
     * @param params
     * @return
     */
    public static boolean isAllEmptyIgnoreBlank(String... params) {
        if (isEmptyArray(params)) {
            return true;
        }
        for (String tmp : params) {
            if (isNotEmptyIgnoreBlank(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串是否都不为空值(空值:null, "", "半角空白", "全角空白", "null")
     *
     * @param params
     * @return
     */
    public static boolean isNotAllEmptyIgnoreBlank(String... params) {
        if (isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isEmptyIgnoreBlank(tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为成功的标识符
     *
     * @param str
     * @return
     */
    public static boolean isSuccess(String str) {
        if (isEmptyIgnoreBlank(str)) {
            return false;
        }
        String[] array = {"1", "y", "yes", "ok", "true", "pass", "success", "successes", "successful", "successfully",
                "成功", "通过"};// 代表成功的标识符
        for (String tmp : array) {
            if (isEqualsIgnoreCase(str, tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为不成功的标识符
     *
     * @param str
     * @return
     */
    public static boolean isFailed(String str) {
        return !isSuccess(str);
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isEquals(String str1, String str2) {
        return null == str1 ? null == str2 : str1.equals(str2);
    }

    /**
     * 判断两个字符串是否不相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isNotEquals(String str1, String str2) {
        return !isEquals(str1, str2);
    }

    /**
     * 判断两个字符串是否相等(忽略大小写)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isEqualsIgnoreCase(String str1, String str2) {
        return null == str1 ? null == str2 : str1.equalsIgnoreCase(str2);
    }

    /**
     * 判断两个字符串是否不相等(忽略大小写)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isNotEqualsIgnoreCase(String str1, String str2) {
        return !isEqualsIgnoreCase(str1, str2);
    }

    /**
     * 判断字符串是否与多个字符串中的一个相等
     *
     * @param str
     * @param params
     * @return
     */
    public static boolean isEquals(String str, String... params) {
        if (null == str || isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isEquals(str, tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否与多个字符串都不相等
     *
     * @param str
     * @param params
     * @return
     */
    public static boolean isNotEquals(String str, String... params) {
        if (null == str && isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isEquals(str, tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否与多个字符串中的一个相等(忽略大小写)
     *
     * @param str
     * @param params
     * @return
     */
    public static boolean isEqualsIgnoreCase(String str, String... params) {
        if (null == str || isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isEqualsIgnoreCase(str, tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否与多个字符串都不相等(忽略大小写)
     *
     * @param str
     * @param params
     * @return
     */
    public static boolean isNotEqualsIgnoreCase(String str, String... params) {
        if (null == str && isEmptyArray(params)) {
            return false;
        }
        for (String tmp : params) {
            if (isEqualsIgnoreCase(str, tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmptyArray(String[] array) {
        return null == array || array.length < 1 || (array.length == 1 && null == array[0]);
    }

    /**
     * 判断字符串数组是否不为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmptyArray(String[] array) {
        return !isEmptyArray(array);
    }

    /**
     * 判断数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return null == array || array.length < 1 || (array.length == 1 && null == array[0]);
    }

    /**
     * 判断数组是否不为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断Map是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
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
     * 判断多个Map中是否存在非空Map
     *
     * @param maps
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?>... maps) {
        if (null == maps || maps.length < 1) {
            return false;
        }
        for (Map<?, ?> map : maps) {
            if (isNotEmpty(map)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个Map是否都为空
     *
     * @param maps
     * @return
     */
    public static boolean isAllEmpty(Map<?, ?>... maps) {
        if (null == maps || maps.length < 1) {
            return true;
        }
        for (Map<?, ?> map : maps) {
            if (isNotEmpty(map)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个Map是否都不为空
     *
     * @param maps
     * @return
     */
    public static boolean isNotAllEmpty(Map<?, ?>... maps) {
        if (null == maps || maps.length < 1) {
            return false;
        }
        for (Map<?, ?> map : maps) {
            if (isEmpty(map)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

    /**
     * 判断List是否不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    /**
     * 判断多个List中是否存在空List
     *
     * @param lists
     * @return
     */
    public static boolean isEmpty(List<?>... lists) {
        if (null == lists || lists.length < 1) {
            return true;
        }
        for (List<?> list : lists) {
            if (isEmpty(list)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个List中是否存在非空List
     *
     * @param lists
     * @return
     */
    public static boolean isNotEmpty(List<?>... lists) {
        if (null == lists || lists.length < 1) {
            return false;
        }
        for (List<?> list : lists) {
            if (isNotEmpty(list)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个List是否都为空
     *
     * @param lists
     * @return
     */
    public static boolean isAllEmpty(List<?>... lists) {
        if (null == lists || lists.length < 1) {
            return true;
        }
        for (List<?> list : lists) {
            if (isNotEmpty(list)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个List是否都不为空
     *
     * @param lists
     * @return
     */
    public static boolean isNotAllEmpty(List<?>... lists) {
        if (null == lists || lists.length < 1) {
            return false;
        }
        for (List<?> list : lists) {
            if (isEmpty(list)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断Collection是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断Collection是否不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断多个Collection中是否存在空Collection
     *
     * @param collections
     * @return
     */
    public static boolean isEmpty(Collection<?>... collections) {
        if (null == collections || collections.length < 1) {
            return true;
        }
        for (Collection<?> collection : collections) {
            if (isEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个Collection中是否存在非空Collection
     *
     * @param collections
     * @return
     */
    public static boolean isNotEmpty(Collection<?>... collections) {
        if (null == collections || collections.length < 1) {
            return false;
        }
        for (Collection<?> collection : collections) {
            if (isNotEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个Collection是否都为空
     *
     * @param collections
     * @return
     */
    public static boolean isAllEmpty(Collection<?>... collections) {
        if (null == collections || collections.length < 1) {
            return true;
        }
        for (Collection<?> collection : collections) {
            if (isNotEmpty(collection)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个Collection是否都不为空
     *
     * @param collections
     * @return
     */
    public static boolean isNotAllEmpty(Collection<?>... collections) {
        if (null == collections || collections.length < 1) {
            return false;
        }
        for (Collection<?> collection : collections) {
            if (isEmpty(collection)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否以指定前缀开始
     *
     * @param str
     * @param prefix
     * @return
     */
    public static boolean isStartsWith(String str, String prefix) {
        return (null != str && null != prefix) && str.startsWith(prefix);
    }

    /**
     * 判断字符串是否不以指定前缀开始
     *
     * @param str
     * @param prefix
     * @return
     */
    public static boolean isNotStartsWith(String str, String prefix) {
        return !isStartsWith(str, prefix);
    }

    /**
     * 判断字符串是否以多个指定前缀中的任何一个开始
     *
     * @param str
     * @param prefixs
     * @return
     */
    public static boolean isStartsWith(String str, String... prefixs) {
        if (null == str || isEmptyArray(prefixs)) {
            return false;
        }
        for (String prefix : prefixs) {
            if (isStartsWith(str, prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否不以多个指定前缀中的任何一个开始
     *
     * @param str
     * @param prefixs
     * @return
     */
    public static boolean isNotStartsWith(String str, String... prefixs) {
        if (null == str || isEmptyArray(prefixs)) {
            return true;
        }
        for (String prefix : prefixs) {
            if (isStartsWith(str, prefix)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否以指定后缀结束
     *
     * @param str
     * @param suffix
     * @return
     */
    public static boolean isEndsWith(String str, String suffix) {
        return (null != str && null != suffix) && str.endsWith(suffix);
    }

    /**
     * 判断字符串是否不以指定后缀结束
     *
     * @param str
     * @param suffix
     * @return
     */
    public static boolean isNotEndsWith(String str, String suffix) {
        return !isEndsWith(str, suffix);
    }

    public static boolean isContains(Object[] array, Object obj) {
        if (isEmpty(array) || null == obj) {
            return false;
        }
        for (Object item : array) {
            if (obj.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContains(Object[] array, Object... obj) {
        if (isEmpty(array) || isEmpty(obj)) {
            return false;
        }
        for (Object item : array) {
            for (Object o : obj) {
                if (o.equals(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断字符串是否包含指定关键字
     *
     * @param str
     * @param keyword
     * @return
     */
    public static boolean isContains(String str, String keyword) {
        return !isEmpty(str, keyword) && str.indexOf(keyword) > -1;
    }

    /**
     * 判断字符串是否不包含指定关键字
     *
     * @param str
     * @param keyword
     * @return
     */
    public static boolean isNotContains(String str, String keyword) {
        return !isContains(str, keyword);
    }

    /**
     * 判断字符串是否包含多个指定关键字中的一个
     *
     * @param str
     * @param keywords
     * @return
     */
    public static boolean isContains(String str, String... keywords) {
        if (null == str || isEmptyArray(keywords)) {
            return false;
        }
        for (String keyword : keywords) {
            if (str.indexOf(keyword) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否不包含所有指定关键字
     *
     * @param str
     * @param keywords
     * @return
     */
    public static boolean isNotContains(String str, String... keywords) {
        if (null == str || isEmptyArray(keywords)) {
            return true;
        }
        for (String keyword : keywords) {
            if (str.indexOf(keyword) > -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否包含指定关键字(忽略大小写)
     *
     * @param str
     * @param keyword
     * @return
     */
    public static boolean isContainsIgnoreCase(String str, String keyword) {
        if (null == str || null == keyword) {
            return false;
        }
        int len = keyword.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, keyword, 0, len)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否不包含指定关键字(忽略大小写)
     *
     * @param str
     * @param keyword
     * @return
     */
    public static boolean isNotContainsIgnoreCase(String str, String keyword) {
        return !isContainsIgnoreCase(str, keyword);
    }

    /**
     * 判断字符串是否包含空白
     *
     * @param str
     * @return
     */
    public static boolean isContainsBlank(String str) {
        if (null == str) {
            return false;
        }
        if (str.length() < 1) {
            return false;
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ASCII_DBC_CASE_BLANK || c[i] == ASCII_SBC_CASE_BLANK) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否不包含空白
     *
     * @param str
     * @return
     */
    public static boolean isNotContainsBlank(String str) {
        return !isContainsBlank(str);
    }

    /**
     * 判断字符串是否为中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^[\\u4e00-\\u9fa5]+$");
    }

    /**
     * 判断字符串是否不为中文
     *
     * @param str
     * @return
     */
    public static boolean isNotChinese(String str) {
        return !isChinese(str);
    }

    /**
     * 判断字符串是否为中文，并且中文个数不能小于指定个数
     *
     * @param str
     * @param m
     * @return
     */
    public static boolean isChinese(String str, int m) {
        return !isEmptyIgnoreBlank(str) && str.matches(String.format("^[\u4e00-\u9fa5]{%s,}$", m));
    }

    /**
     * 判断字符串是否不为中文，或者中文个数小于指定个数
     *
     * @param str
     * @param m
     * @return
     */
    public static boolean isNotChinese(String str, int m) {
        return !isChinese(str, m);
    }

    /**
     * 判断字符串是否为中文，并且中文个数不能超过指定范围
     *
     * @param str
     * @param m
     * @param n
     * @return
     */
    public static boolean isChinese(String str, int m, int n) {
        return !isEmptyIgnoreBlank(str) && str.matches(String.format("^[\u4e00-\u9fa5]{%s,%s}$", m, n));
    }

    /**
     * 判断字符串是否不为中文，或者中文个数超过指定范围
     *
     * @param str
     * @param m
     * @param n
     * @return
     */
    public static boolean isNotChinese(String str, int m, int n) {
        return !isChinese(str, m, n);
    }

    /**
     * 判断字符串是否为中文姓名(2至15个汉字)
     *
     * @param str
     * @return
     */
    public static boolean isChineseName(String str) {
        return isChinese(str, 2, 15);
    }

    /**
     * 判断字符串是否不为中文姓名(2至15个汉字)
     *
     * @param str
     * @return
     */
    public static boolean isNotChineseName(String str) {
        return !isChineseName(str);
    }

    /**
     * 判断字符串是否为双字节字符
     *
     * @param str
     * @return
     */
    public static boolean isDBCS(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches("^[^\\x00-\\xff]+$");
    }

    /**
     * 判断字符串是否不为双字节字符
     *
     * @param str
     * @return
     */
    public static boolean isNotDBCS(String str) {
        return !isDBCS(str);
    }

    /**
     * 判断对象是否为枚举
     *
     * @param obj
     * @return
     */
    public static boolean isEnum(Object obj) {
        return obj.getClass().isEnum();
    }

    /**
     * 判断对象是否不为枚举
     *
     * @param obj
     * @return
     */
    public static boolean isNotEnum(Object obj) {
        return !isEnum(obj);
    }

    /**
     * 判断对象是否为字符串
     *
     * @param obj
     * @return
     */
    public static boolean isString(Object obj) {
        return null != obj && String.class.isAssignableFrom(obj.getClass());
    }

    /**
     * 判断对象是否不为字符串
     *
     * @param obj
     * @return
     */
    public static boolean isNotString(Object obj) {
        return !isString(obj);
    }

    /**
     * 判断对象是否为org.w3c.dom.Document
     *
     * @param obj
     * @return
     */
    public static boolean isDom(Object obj) {
        return null != obj && org.w3c.dom.Document.class.isAssignableFrom(obj.getClass());
    }

    /**
     * 判断对象是否不为org.w3c.dom.Document
     *
     * @param obj
     * @return
     */
    public static boolean isNotDom(Object obj) {
        return !isDom(obj);
    }

    /**
     * 判断对象是否为数组
     *
     * @param obj
     * @return
     */
    public static boolean isArray(Object obj) {
        return obj.getClass().isArray();
    }

    /**
     * 判断对象是否不为数组
     *
     * @param obj
     * @return
     */
    public static boolean isNotArray(Object obj) {
        return !isArray(obj);
    }

    /**
     * 判断对象是否为Map
     *
     * @param obj
     * @return
     */
    public static boolean isMap(Object obj) {
        return null != obj && Map.class.isAssignableFrom(obj.getClass());
    }

    /**
     * 判断对象是否不为Map
     *
     * @param obj
     * @return
     */
    public static boolean isNotMap(Object obj) {
        return !isMap(obj);
    }

    /**
     * 判断对象是否为List
     *
     * @param obj
     * @return
     */
    public static boolean isList(Object obj) {
        return null != obj && List.class.isAssignableFrom(obj.getClass());
    }

    /**
     * 判断对象是否不为List
     *
     * @param obj
     * @return
     */
    public static boolean isNotList(Object obj) {
        return !isList(obj);
    }

    /**
     * 判断对象是否为byte[]
     *
     * @param obj
     * @return
     */
    public static boolean isByteArray(Object obj) {
        return null != obj && byte[].class.isAssignableFrom(obj.getClass());
    }

    /**
     * 判断对象是否不为byte[]
     *
     * @param obj
     * @return
     */
    public static boolean isNotByteArray(Object obj) {
        return !isByteArray(obj);
    }

    /**
     * 判断对象是否为InputStream
     *
     * @param obj
     * @return
     */
    public static boolean isInputStream(Object obj) {
        return null != obj && InputStream.class.isAssignableFrom(obj.getClass());
    }

    /**
     * 判断对象是否不为InputStream
     *
     * @param obj
     * @return
     */
    public static boolean isNotInputStream(Object obj) {
        return !isInputStream(obj);
    }

    /**
     * 判断对象是否为File
     *
     * @param obj
     * @return
     */
    public static boolean isFile(Object obj) {
        return null != obj && File.class.isAssignableFrom(obj.getClass());
    }

    /**
     * 判断对象是否不为File
     *
     * @param obj
     * @return
     */
    public static boolean isNotFile(Object obj) {
        return !isFile(obj);
    }

    /**
     * 判断字符串是否为小写字母
     *
     * @param str
     * @return
     */
    public static boolean isLowerCase(String str) {
        if (isEmptyIgnoreBlank(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLowerCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为小写字母
     *
     * @param str
     * @return
     */
    public static boolean isNotLowerCase(String str) {
        return !isLowerCase(str);
    }

    /**
     * 判断字符串是否为大写字母
     *
     * @param str
     * @return
     */
    public static boolean isUpperCase(String str) {
        if (isEmptyIgnoreBlank(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isUpperCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为大写字母
     *
     * @param str
     * @return
     */
    public static boolean isNotUpperCase(String str) {
        return !isUpperCase(str);
    }

    /**
     * 判断两个整数是否相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isEquals(Integer v1, Integer v2) {
        return null == v1 ? null == v2 : null != v2 && v1.compareTo(v2) == 0;
    }

    /**
     * 判断两个整数是否不相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotEquals(Integer v1, Integer v2) {
        return !isEquals(v1, v2);
    }

    /**
     * 判断整数是否与多个整数中的一个相等
     *
     * @param v
     * @param params
     * @return
     */
    public static boolean isEquals(Integer v, Integer... params) {
        if (null == v || isEmpty(params)) {
            return false;
        }
        for (Integer tmp : params) {
            if (isEquals(v, tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断整数是否与多个整数都不相等
     *
     * @param v
     * @param params
     * @return
     */
    public static boolean isNotEquals(Integer v, Integer... params) {
        if (null == v && isEmpty(params)) {
            return false;
        }
        for (Integer tmp : params) {
            if (isEquals(v, tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个整数是否相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isEquals(Long v1, Long v2) {
        return null == v1 ? null == v2 : null != v2 && v1.compareTo(v2) == 0;
    }

    /**
     * 判断两个整数是否不相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotEquals(Long v1, Long v2) {
        return !isEquals(v1, v2);
    }

    /**
     * 判断整数是否与多个整数中的一个相等
     *
     * @param v
     * @param params
     * @return
     */
    public static boolean isEquals(Long v, Long... params) {
        if (null == v || isEmpty(params)) {
            return false;
        }
        for (Long tmp : params) {
            if (isEquals(v, tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断整数是否与多个整数都不相等
     *
     * @param v
     * @param params
     * @return
     */
    public static boolean isNotEquals(Long v, Long... params) {
        if (null == v && isEmpty(params)) {
            return false;
        }
        for (Long tmp : params) {
            if (isEquals(v, tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个数字是否相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isEquals(BigDecimal v1, BigDecimal v2) {
        return null == v1 ? null == v2 : null != v2 && v1.compareTo(v2) == 0;
    }

    /**
     * 判断两个数字是否不相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotEquals(BigDecimal v1, BigDecimal v2) {
        return !isEquals(v1, v2);
    }

    /**
     * 判断数字是否与多个数字中的一个相等
     *
     * @param v
     * @param params
     * @return
     */
    public static boolean isEquals(BigDecimal v, BigDecimal... params) {
        if (null == v || isEmpty(params)) {
            return false;
        }
        for (BigDecimal tmp : params) {
            if (isEquals(v, tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数字是否与多个数字都不相等
     *
     * @param v
     * @param params
     * @return
     */
    public static boolean isNotEquals(BigDecimal v, BigDecimal... params) {
        if (null == v && isEmpty(params)) {
            return false;
        }
        for (BigDecimal tmp : params) {
            if (isEquals(v, tmp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个时间戳是否相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isEquals(Timestamp v1, Timestamp v2) {
        return null == v1 ? null == v2 : null != v2 && v1.compareTo(v2) == 0;
    }

    /**
     * 判断两个时间戳是否不相等
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotEquals(Timestamp v1, Timestamp v2) {
        return !isEquals(v1, v2);
    }

    /**
     * 判断字符串是否为有效的组织机构代码(全国组织机构代码中心提供校验规则)
     *
     * @param str
     * @return
     */
    public static boolean isOrgCode(String str) {
        if (isEmptyIgnoreBlank(str) || str.length() != 9) {
            return false;
        }
        int[] codeArray = new int[8];
        int[] codeFactor = new int[8];
        String verifyChar;
        for (int i = 0; i < 8; i++) {
            String tmpChar = str.substring(i, i + 1);
            codeArray[i] = isInteger(tmpChar) ? Integer.parseInt(tmpChar) : (int) tmpChar.charAt(0) - 65 + 10;
        }
        String endChar = str.substring(8, 9);
        codeFactor[0] = 3;
        codeFactor[1] = 7;
        codeFactor[2] = 9;
        codeFactor[3] = 10;
        codeFactor[4] = 5;
        codeFactor[5] = 8;
        codeFactor[6] = 4;
        codeFactor[7] = 2;
        int j = 0;
        for (int i = 0; i < 8; i++) {
            j += codeArray[i] * codeFactor[i];
        }
        j = 11 - j % 11;
        switch (j) {
            case 11:
                verifyChar = "0";
                break;
            case 10:
                verifyChar = "X";
                break;
            default:
                verifyChar = String.valueOf(j);
        }
        return endChar.equals(verifyChar);
    }

    /**
     * 判断字符串是否为无效的组织机构代码(全国组织机构代码中心提供校验规则)
     *
     * @param str
     * @return
     */
    public static boolean isNotOrgCode(String str) {
        return !isOrgCode(str);
    }

    /**
     * 判断字符串是否为升序的数字串(格式:0123456789)
     *
     * @param str
     * @return
     */
    public static boolean isAscNumeric(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches(
                "^(0(?=1|$)|1(?=2|$)|2(?=3|$)|3(?=4|$)|4(?=5|$)|5(?=6|$)|6(?=7|$)|7(?=8|$)|8(?=9|$)|9(?=$))*$");
    }

    /**
     * 判断字符串是否不为升序的数字串(格式:0123456789)
     *
     * @param str
     * @return
     */
    public static boolean isNotAscNumeric(String str) {
        return !isAscNumeric(str);
    }

    /**
     * 判断字符串是否为降序的数字串(格式:9876543210)
     *
     * @param str
     * @return
     */
    public static boolean isDescNumeric(String str) {
        return !isEmptyIgnoreBlank(str) && str.matches(
                "^(9(?=8|$)|8(?=7|$)|7(?=6|$)|6(?=5|$)|5(?=4|$)|4(?=3|$)|3(?=2|$)|2(?=1|$)|1(?=0|$)|0(?=$))*$");
    }

    /**
     * 判断字符串是否不为降序的数字串(格式:9876543210)
     *
     * @param str
     * @return
     */
    public static boolean isNotDescNumeric(String str) {
        return !isDescNumeric(str);
    }

    /**
     * 判断字符串是否为相同的数字串(格式:6666)
     *
     * @param str
     * @return
     */
    public static boolean isEqualsNumeric(String str) {
        if (isEmptyIgnoreBlank(str) || str.length() == 1) {
            return false;
        }
        return str.matches(
                "^(0(?=0|$)|1(?=1|$)|2(?=2|$)|3(?=3|$)|4(?=4|$)|5(?=5|$)|6(?=6|$)|7(?=7|$)|8(?=8|$)|9(?=9|$))*$");
    }

    /**
     * 判断字符串是否不为相同的数字串(格式:6666)
     *
     * @param str
     * @return
     */
    public static boolean isNotEqualsNumeric(String str) {
        return !isEqualsNumeric(str);
    }

    /**
     * 判断字符串是否为字符串"0"
     *
     * @param str
     * @return
     */
    public static boolean isZero(String str) {
        return ZERO.equals(str);
    }

    /**
     * 判断字符串是否不为字符串"0"
     *
     * @param str
     * @return
     */
    public static boolean isNotZero(String str) {
        return !isZero(str);
    }

    /**
     * 判断字符串是否为字符串"null"
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return NULL.equals(str);
    }

    /**
     * 判断字符串是否不为字符串"null"
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * 判断字符串是否为字符串"null"(忽略大小写)
     *
     * @param str
     * @return
     */
    public static boolean isNullIgnoreCase(String str) {
        return NULL.equalsIgnoreCase(str);
    }

    /**
     * 判断字符串是否不为字符串"null"(忽略大小写)
     *
     * @param str
     * @return
     */
    public static boolean isNotNullIgnoreCase(String str) {
        return !isNullIgnoreCase(str);
    }

    /**
     * 判断字符串是否为字符串"null"(忽略两端半角空白)
     *
     * @param str
     * @return
     */
    public static boolean isNullIgnoreTrim(String str) {
        return null != str && NULL.equals(str.trim());
    }

    /**
     * 判断字符串是否不为字符串"null"(忽略两端半角空白)
     *
     * @param str
     * @return
     */
    public static boolean isNotNullIgnoreTrim(String str) {
        return !isNullIgnoreTrim(str);
    }

    /**
     * 判断字符串是否为字符串"null"(忽略大小写和两端半角空白)
     *
     * @param str
     * @return
     */
    public static boolean isNullIgnoreAll(String str) {
        return null != str && NULL.equalsIgnoreCase(str.trim());
    }

    /**
     * 判断字符串是否不为字符串"null"(忽略大小写和两端半角空白)
     *
     * @param str
     * @return
     */
    public static boolean isNotNullIgnoreAll(String str) {
        return !isNullIgnoreAll(str);
    }

    /**
     * 判断数组中是否有重复的元素
     *
     * @param array
     * @return
     */
    public static boolean isRepeat(String[] array) {
        if (null != array && array.length > 1) {
            int len = array.length;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (isEquals(array[i], array[j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断数组中是否没有重复的元素
     *
     * @param array
     * @return
     */
    public static boolean isNotRepeat(String[] array) {
        return !isRepeat(array);
    }

    /**
     * 判断字符串是否符合指定的日期格式
     *
     * @param str
     * @param format
     * @return
     */
    public static boolean isDateFormat(String str, String format) {
        if (isEmptyIgnoreBlank(str, format) || str.length() != format.length()) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            sdf.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否不符合指定的日期格式
     *
     * @param str
     * @param format
     * @return
     */
    public static boolean isNotDateFormat(String str, String format) {
        return !isDateFormat(str, format);
    }

    /**
     * 判断出生日期是否有效(格式:yyyyMMdd)
     *
     * @param birthdate
     * @return
     */
    public static boolean isBirthdate(String birthdate) {
        if (null == birthdate || birthdate.length() != 8) {
            return false;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(birthdate);
        } catch (Exception e) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return isBirthdate(year, month, day);
    }

    /**
     * 判断出生日期是否无效(格式:yyyyMMdd)
     *
     * @param birthdate
     * @return
     */
    public static boolean isNotBirthdate(String birthdate) {
        return !isBirthdate(birthdate);
    }

    /**
     * 判断出生日期是否有效
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isBirthdate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        if (year < 1900 || year > currentYear || month < 1 || month > 12 || day < 1) {
            return false;
        }
        if (year == currentYear && (month > currentMonth || day > currentDay)) {
            return false;
        }
        int lastDayOfMonth;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                lastDayOfMonth = 30;
                break;
            case 2:
                lastDayOfMonth = isLeapYear(year) ? 29 : 28;
                break;
            default:
                lastDayOfMonth = 31;
        }
        return day <= lastDayOfMonth;
    }

    /**
     * 判断出生日期是否无效
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isNotBirthdate(int year, int month, int day) {
        return !isBirthdate(year, month, day);
    }

    /**
     * 判断第一个数字是否小于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isLessThan(Object v1, Object v2) {
        String str1 = String.valueOf(v1);
        String str2 = String.valueOf(v2);
        if (isNotNumeric(str1, str2)) {
            throw new IllegalArgumentException("参数必须为整数或小数！");
        }
        BigDecimal value1 = BigDecimal.valueOf(Double.valueOf(str1));
        BigDecimal value2 = BigDecimal.valueOf(Double.valueOf(str2));
        return isLessThan(value1, value2);
    }

    /**
     * 判断第一个数字是否大于或等于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotLessThan(Object v1, Object v2) {
        return !isLessThan(v1, v2);
    }

    /**
     * 判断第一个数字是否小于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isLessThan(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) {
            throw new IllegalArgumentException("参数必须为整数或小数！");
        }
        return v1.compareTo(v2) < 0;
    }

    /**
     * 判断第一个数字是否大于或等于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotLessThan(BigDecimal v1, BigDecimal v2) {
        return !isLessThan(v1, v2);
    }

    /**
     * 判断第一个时间戳是否小于第二个时间戳
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isLessThan(Timestamp v1, Timestamp v2) {
        if (null == v1 || null == v2) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        return v1.compareTo(v2) < 0;
    }

    /**
     * 判断第一个时间戳是否大于或等于第二个时间戳
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotLessThan(Timestamp v1, Timestamp v2) {
        return !isLessThan(v1, v2);
    }

    public static boolean isLessThan(Date v1, Date v2) {
        if (null == v1 || null == v2) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        return v1.compareTo(v2) < 0;
    }

    /**
     * 判断第一个数字是否大于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isGreaterThan(Object v1, Object v2) {
        String str1 = String.valueOf(v1);
        String str2 = String.valueOf(v2);
        if (isNotNumeric(str1, str2)) {
            throw new IllegalArgumentException("参数必须为整数或小数！");
        }
        BigDecimal value1 = BigDecimal.valueOf(Double.valueOf(str1));
        BigDecimal value2 = BigDecimal.valueOf(Double.valueOf(str2));
        return isGreaterThan(value1, value2);
    }

    /**
     * 判断第一个数字是否小于或等于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotGreaterThan(Object v1, Object v2) {
        return !isGreaterThan(v1, v2);
    }

    /**
     * 判断第一个数字是否大于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isGreaterThan(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        return v1.compareTo(v2) > 0;
    }

    /**
     * 判断第一个数字是否小于或等于第二个数字
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotGreaterThan(BigDecimal v1, BigDecimal v2) {
        return !isGreaterThan(v1, v2);
    }

    /**
     * 判断第一个时间戳是否大于第二个时间戳
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isGreaterThan(Timestamp v1, Timestamp v2) {
        if (null == v1 || null == v2) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        return v1.compareTo(v2) > 0;
    }

    /**
     * 判断第一个时间戳是否小于或等于第二个时间戳
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isNotGreaterThan(Timestamp v1, Timestamp v2) {
        return !isGreaterThan(v1, v2);
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 判断是否不为闰年
     *
     * @param year
     * @return
     */
    public static boolean isNotLeapYear(int year) {
        return !isLeapYear(year);
    }

}
