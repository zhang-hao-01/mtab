package com.mtab.common;

import lombok.Data;

/**
 * 统一响应结果
 *
 * @author zhanghao
 */
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(1);
        r.setMsg("ok");
        r.setData(data);
        return r;
    }

    public static <T> Result<T> success(String msg, T data) {
        Result<T> r = new Result<>();
        r.setCode(1);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setCode(0);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }

    public static <T> Result<T> error(String msg, T data) {
        Result<T> r = new Result<>();
        r.setCode(0);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
