package com.dxj.util;


import com.dxj.base.vo.Result;

/**
 * @author Sinkiang
 */
public class ResultUtil<T> {

    private Result<T> result;

    public ResultUtil() {
        result = new Result<>();
        result.setSuccess(true);
        result.setMessage("success");
        result.setCode(200);
    }

    public Result<T> setData(T t) {
        this.result.setResult(t);
        this.result.setCode(200);
        return this.result;
    }

    public Result<T> setSuccessMsg(String msg) {
        result.setSuccess(true);
        result.setMessage(msg);
        result.setCode(200);
        return this.result;
    }

    public Result<T> setData(T t, String msg) {
        result.setResult(t);
        result.setCode(200);
        result.setMessage(msg);
        return result;
    }

    public Result<T> setErrorMsg(String msg) {
        result.setSuccess(false);
        result.setMessage(msg);
        result.setCode(500);
        return result;
    }

    public Result<T> setErrorMsg(Integer code, String msg) {
        result.setSuccess(false);
        result.setMessage(msg);
        result.setCode(code);
        return result;
    }

    public static <T> Result<T> data(T t) {
        return new ResultUtil<T>().setData(t);
    }

    public static <T> Result<T> data(T t, String msg) {
        return new ResultUtil<T>().setData(t, msg);
    }

    public static <T> Result<T> success(String msg) {
        return new ResultUtil<T>().setSuccessMsg(msg);
    }

    public static <T> Result<T> error(String msg) {
        return new ResultUtil<T>().setErrorMsg(msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new ResultUtil<T>().setErrorMsg(code, msg);
    }
}
