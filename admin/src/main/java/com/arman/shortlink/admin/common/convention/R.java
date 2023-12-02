package com.arman.shortlink.admin.common.convention;


import com.arman.shortlink.admin.common.enums.RespEnum;

/**
 * @author Arman
 */
public record R<T>(String code, String msg, T data) {

    public R(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private static <T> R<T> create(RespEnum respEnum) {
        return new R<>(respEnum.getCode(), respEnum.getMsg(), null);
    }

    public static <T> R<T> ok() {
        return create(RespEnum.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(RespEnum.SUCCESS.getCode(), RespEnum.SUCCESS.getMsg(), data);
    }

    public static <T> R<T> fail() {
        return create(RespEnum.SYSTEM_ERROR);
    }

    public static <T> R<T> fail(RespEnum respEnum) {
        return create(respEnum);
    }

    public static <T> R<T> fail(RespEnum respEnum, String msg) {
        return new R<>(respEnum.getCode(), msg, null);
    }

}
