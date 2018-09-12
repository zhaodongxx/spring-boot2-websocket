package com.zhaodongxx.common.utils.restResult;

/**
 *
 * <p>封装统一的返回结果</p>
 * Created by zhaod on 2018/1/24 17:39
 *
 */
public class RestResult<T> {
    private ResultCodeEnum code;
    private String message;
    private  T data;

    public ResultCodeEnum getCode() {
        return code;
    }

    public RestResult() {
    }

    public RestResult setCode(ResultCodeEnum resultCode) {
        this.code = resultCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public RestResult setData(T data) {
        this.data = data;
        return this;
    }
}
