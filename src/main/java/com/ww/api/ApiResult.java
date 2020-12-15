package com.ww.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Sun
 * @create: 2020-12-14 14:56
 * @version: v1.0
 */
@Data
public class ApiResult<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;
    /**
     * 错误消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;


    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> resultInfo = new ApiResult<>();
        resultInfo.setCode(ApiStatus.OK.getCode());
        resultInfo.setMessage(ApiStatus.OK.getMsg());
        resultInfo.setData(data);


        return resultInfo;
    }

    private static <T> String getResultString(ApiResult<T> resultInfo) {
        String s = resultInfo.toString();
        if (s.length() < 500) {
            return s;
        } else {
            return s.substring(0, 500);
        }
    }

    public static <T> ApiResult<T> success() {
        ApiResult<T> resultInfo = new ApiResult<>();
        resultInfo.setCode(ApiStatus.OK.getCode());
        resultInfo.setMessage(ApiStatus.OK.getMsg());
        resultInfo.setData(null);

        return resultInfo;
    }

    public static <T> ApiResult<T> fail(int code, String msg) {
        ApiResult<T> resultInfo = new ApiResult<>();
        resultInfo.setCode(code);
        resultInfo.setMessage(msg);

        return resultInfo;
    }

    public static <T> ApiResult<T> fail(ApiStatus apiStatus) {
        ApiResult<T> resultInfo = new ApiResult<>();
        resultInfo.setCode(apiStatus.getCode());
        resultInfo.setMessage(apiStatus.getMsg());

        return resultInfo;
    }

}
