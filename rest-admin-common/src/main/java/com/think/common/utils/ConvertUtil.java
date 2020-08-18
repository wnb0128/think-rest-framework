package com.think.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 *
 * @author : think
 * @Description : 工具类:数据转换
 * @date : 2020年6月25日 上午11:11:08
 */
public final class ConvertUtil {

    /**
     * 字符编码:UTF-8
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 分隔符:逗号
     */
    public static final String SEPARATOR_COMMA = ",";

    /**
     * 分隔符:分号
     */
    public static final String SEPARATOR_SEMICOLON = ";";

    /**
     * 分隔符:冒号
     */
    public static final String SEPARATOR_COLON = ":";

    /**
     * 转义前缀:ASCII
     */
    public static final String ESCAPE_PREFIX_ASCII = "\\\\u";

    /**
     * 将字符转换为字节数组
     *
     * @param c
     * @return
     */
    public static byte[] toByteArray(char c) {
        byte[] array = new byte[2];
        array[0] = (byte) ((c & 0xFF00) >> 8);
        array[1] = (byte) (c & 0xFF);
        return array;
    }

    /**
     * 将普通字符串转换为Unicode字符串
     *
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        if (null == str) {
            return null;
        }
        int len = str.length();
        if (len < 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (c > 255) {
                sb.append("\\u").append(Integer.toHexString(c));
            } else {

                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将Unicode字符串转换为普通字符串
     *
     * @param str
     * @return
     */
    public static String unicodeToString(String str) {
        if (ValidatorUtils.isNotContains(str, "\\u")) {
            return str;
        }
        String[] array = str.split("\\\\u");
        if (ValidatorUtils.isEmptyArray(array)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String tmp : array) {
            if (ValidatorUtils.isEmptyIgnoreBlank(tmp)) {
                sb.append(tmp);
                continue;
            }
            int data = Integer.parseInt(tmp, 16);
            sb.append((char) data);
        }
        return sb.toString();
    }

    /**
     * 将字符串转换为字符串数组
     *
     * @param str
     * @return
     */
    public static String[] toArray(String str) {
        return toArray(str, SEPARATOR_COMMA);
    }

    /**
     * 将字符串转换为字符串数组
     *
     * @param str
     * @param separator
     * @return
     */
    public static String[] toArray(String str, String separator) {
        if (null == str || null == separator) {
            return null;
        }
        return str.replaceAll("\\s*", "").replaceAll("　", "").split(separator);// 替换空白字符和全角空格
    }

    /**
     * 将字符串转换为整数数组
     *
     * @param str
     * @return
     */
    public static Integer[] toArrayInteger(String str) {
        return toArrayInteger(toArray(str));
    }

    /**
     * 将字符串转换为整数数组
     *
     * @param str
     * @param separator
     * @return
     */
    public static Integer[] toArrayInteger(String str, String separator) {
        return toArrayInteger(toArray(str, separator));
    }

    public static Integer[] toArrayInteger(String[] array) {
        if (null == array) {
            return null;
        }
        int len = array.length;
        if (len == 0) {
            return null;
        }
        try {
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++) {
                result[i] = Integer.valueOf(array[i]);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串转换为整数数组
     *
     * @param str
     * @return
     */
    public static Long[] toArrayLong(String str) {
        return toArrayLong(toArray(str));
    }

    /**
     * 将字符串转换为整数数组
     *
     * @param str
     * @param separator
     * @return
     */
    public static Long[] toArrayLong(String str, String separator) {
        return toArrayLong(toArray(str, separator));
    }

    public static Long[] toArrayLong(String[] array) {
        if (null == array) {
            return null;
        }
        int len = array.length;
        if (len == 0) {
            return null;
        }
        try {
            Long[] result = new Long[len];
            for (int i = 0; i < len; i++) {
                result[i] = Long.valueOf(array[i]);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param array
     * @return
     */
    public static String toHexString(byte[] array) {

        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将对象转换为字符串(忽略指定值)
     *
     * @param obj
     * @param ignoreValues
     * @return
     */
    public static String toStringIgnoreValues(Object obj, String[] ignoreValues) {
        return toStringIgnoreValues(obj, "", ignoreValues);
    }

    /**
     * 将对象转换为字符串(忽略指定值)
     *
     * @param obj
     * @param defaultValue
     * @param ignoreValues
     * @return
     */
    public static String toStringIgnoreValues(Object obj, String defaultValue, String[] ignoreValues) {
        if (null != obj) {
            String str = String.valueOf(obj);
            if (ValidatorUtils.isNotEquals(str, ignoreValues)) {

                return str.trim();
            }
        }
        return defaultValue;
    }

    /**
     * 将对象转换为字符串(忽略空值)
     *
     * @param obj
     * @return
     */
    public static String toStringIgnoreBlank(Object obj) {
        return toStringIgnoreBlank(obj, "");
    }

    /**
     * 将对象转换为字符串(忽略空值)
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String toStringIgnoreBlank(Object obj, String defaultValue) {
        if (null != obj) {
            String str = String.valueOf(obj);
            if (ValidatorUtils.isNotEmptyIgnoreBlank(str)) {

                return str.trim();
            }
        }
        return defaultValue;
    }

    /**
     * 将数组转换为字符串，默认以逗号分隔每项
     *
     * @param array
     * @return
     */
    public static String toString(Object[] array) {
        return toString(array, SEPARATOR_COMMA);
    }

    public static String toString(Object[] array, String separator) {
        if (ValidatorUtils.isEmpty(array)) {
            return null;
        }
        if (array.length == 1) {
            return String.valueOf(array[0]);
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Object obj : array) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(separator);
            }
            sb.append(obj);
        }
        return sb.toString();
    }

    /**
     * 将半角字符串转换成全角字符串
     *
     * @param str
     * @return
     */
    public static String toSBC(String str) {

        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ' ') {
                array[i] = '\u3000';
            } else if (array[i] < '\177') {
                array[i] = (char) (array[i] + 65248);
            }
        }
        return new String(array);
    }

    /**
     * 将全角字符串转换成半角字符串
     *
     * @param str
     * @return
     */
    public static String toDBC(String str) {
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '\u3000') {
                array[i] = ' ';
            } else if (array[i] > '\uFF00' && array[i] < '\uFF5F') {
                array[i] = (char) (array[i] - 65248);
            }
        }
        return new String(array);
    }

    /**
     * 将对象转换为整数，如果转换失败，则返回默认值
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Integer toInteger(Object obj, Integer defaultValue) {
        if (ValidatorUtils.isNotInteger(String.valueOf(obj))) {
            return defaultValue;
        }
        return Integer.valueOf(String.valueOf(obj));
    }

    /**
     * 将对象转换为整数，如果转换失败，则返回默认值
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Long toLong(Object obj, Long defaultValue) {

        if (ValidatorUtils.isNotInteger(String.valueOf(obj))) {

            return defaultValue;
        }
        return Long.valueOf(String.valueOf(obj));
    }

    /**
     * 将对象类型强制转换为指定类型
     *
     * @param clazz
     * @param value
     * @return
     */
    public static Object cast(Class<?> clazz, Object value) {
        try {
            return clazz.cast(value);
        } catch (ClassCastException e) {
            String str = String.valueOf(value);
            switch (ValueTypeEnum.valueOf(clazz.getSimpleName().toUpperCase())) {
                case SHORT:
                    return Short.parseShort(str);
                case INT:
                    return Integer.parseInt(str);
                case INTEGER:
                    return Integer.parseInt(str);
                case LONG:
                    return Long.parseLong(str);
                case DOUBLE:
                    return Double.parseDouble(str);
                case FLOAT:
                    return Float.parseFloat(str);
                case BOOLEAN:
                    return Boolean.parseBoolean(str);
                case STRING:
                    return str;
                default:
                    return value;
            }
        }
    }

    private enum ValueTypeEnum {
        SHORT("short"), INT("int"), INTEGER("integer"), LONG("long"), DOUBLE("double"), FLOAT("float"), BOOLEAN(
                "boolean"), CHAR("char"), STRING("string");
        private String value;

        ValueTypeEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.name().concat(":").concat(value);
        }
    }

    /**
     * 将普通字符串转换为ASCII编码的字符串
     *
     * @param str
     * @return
     */
    public static String toASCII(String str) {

        char[] array = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(toASCII(array[i]));
        }
        return sb.toString();
    }

    private static String toASCII(char c) {
        if (c > 255) {
            StringBuilder sb = new StringBuilder();
            sb.append("\\u");
            int code = c >> 8;
            String tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            code = c & 0xFF;
            tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {

                sb.append("0");
            }
            sb.append(tmp);
            return sb.toString();
        }
        return Character.toString(c);
    }

    /**
     * 将ASCII编码的字符串转换为普通字符串
     *
     * @param str
     * @return
     */
    public static String asciiToString(String str) {

        StringBuilder sb = new StringBuilder();
        int tmp = 0;
        int index = str.indexOf("\\u");
        while (index != -1) {

            sb.append(str, tmp, index);
            tmp = index + 6;
            sb.append(asciiToChar(str.substring(index, tmp)));
            index = str.indexOf("\\u", tmp);
        }
        sb.append(str.substring(tmp));
        return sb.toString();
    }

    private static char asciiToChar(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException("参数不正确，待转换的字符串必须是6个字符！");
        }
        if (!str.startsWith("\\u")) {
            throw new IllegalArgumentException("参数不正确，待转换的字符串前两位字符必须是\"\\u\"！");
        }
        String tmp = str.substring(2, 4);
        int code = Integer.parseInt(tmp, 16) << 8;
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16);
        return (char) code;
    }

    /**
     * 将流转换为字符串
     *
     * @param is
     * @return
     */
    public static String streamToString(InputStream is) {
        return streamToString(is, CHARSET);
    }

    /**
     * 将流转换为字符串
     *
     * @param is
     * @param charset
     * @return
     */
    public static String streamToString(InputStream is, String charset) {
        try {
            return IOUtils.toString(is, charset);
        } catch (IOException e) {

            return null;
        }
    }

    /**
     * 将对象转换为BigDecimal
     *
     * @param obj
     * @return
     */
    public static BigDecimal toBigDecimal(Object obj) {
        return null == obj ? null : toBigDecimal(String.valueOf(obj));
    }

    /**
     * 将字符串转换为BigDecimal
     *
     * @param str
     * @return
     */
    public static BigDecimal toBigDecimal(String str) {
        if (ValidatorUtils.isNotNumeric(str)) {
            return null;
        }
        return BigDecimal.valueOf(Double.parseDouble(str));
    }

    /**
     * 截取字符串
     *
     * @param str
     * @return
     */
    public static String substring(String str, int endIndex) {
        if (null == str) {
            return null;
        }
        int len = str.length();
        if (len < 1) {
            return str;
        }
        int index = endIndex > len ? len : endIndex;
        return str.substring(0, index);
    }

}
