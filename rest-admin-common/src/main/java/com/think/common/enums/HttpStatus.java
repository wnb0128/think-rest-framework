package com.think.common.enums;

/**
 * 定义返回状态
 */
public enum HttpStatus {
    /**
     * 成功
     */
    SUCCESS(200, "服务器连接成功"),
    /**
     * 失败
     */
    FAILED(-1, "服务器异常，请稍后再试"),
    /**
     * 成功
     */
    BU_SUCCESS(200, "请求成功"),
    /**
     * 失败
     */
    BU_FAILED(-1, "请求失败"),
    BU_DEALING(0, "处理中"),

    /**
     * 请求过于频繁，请稍后再试
     */
    FREQUENT(-1, "请求过于频繁，请稍后再试"),

    PARAM_FAIL_CODE(-2, "参数错误"),

    /**
     * 系统错误
     */
    ERROR(500, "服务器内部错误"),
    /**
     * 系统错误
     */
    INVALID(400, "请求参数出错"),
    TIME_OUT(400, "会话过期，请重新登录"),
    FRONT_PROMPT(405, "前端扩展提示语"),
    /**
     * 请求方法错误
     */
    METHOD_ERROR(501, "请求方法错误"),
    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(502, "系统繁忙，请稍后再试！"),
    /**
     * 数据转换异常
     */
    DATA_TRANSFER_ERROR(503, "数据转换异常"),

    /**
     * 文件上传错误
     */
    FILE_UPLOAD_ERROR(-3, "文件上传错误");

    /**
     * 名称
     */
    private final Integer code;

    /**
     * 值
     */
    private final String value;

    HttpStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 获取编码
     *
     * @return
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取提示文本
     *
     * @return
     */
    public String getText() {
        return value;
    }

    public static HttpStatus getValueByName(String value) {
        for (HttpStatus status : HttpStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
