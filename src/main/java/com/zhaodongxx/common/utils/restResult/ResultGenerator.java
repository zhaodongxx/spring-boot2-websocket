package com.zhaodongxx.common.utils.restResult;

/**
 * Created by zhaod on 2018/1/24 18:02
 *
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static RestResult genSuccessResult() {
        return new RestResult()
                .setCode(ResultCodeEnum.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> RestResult<T> genSuccessResult(T data) {
        return new RestResult()
                .setCode(ResultCodeEnum.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static RestResult genFailResult(String message) {
        return new RestResult()
                .setCode(ResultCodeEnum.FAIL)
                .setMessage(message);
    }
}
