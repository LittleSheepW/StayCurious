package com.ww.api;


/**
 * @author: Sun
 * @create: 2020-12-14 14:58
 * @version: v1.0
 */
public enum ApiStatus {

    /**
     * 一些未知异常情况，比如，解析失败等
     */
    UNKNOWN_ERROR(-1, "操作失败"),
    INVALID_TOKEN(-2, "无效的token"),
    ACCESS_DENIED(-3, "权限不足"),

    /**
     * 操作成功
     */
    OK(0, "成功")

    ;

    private int code;
    private String msg;

    ApiStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
