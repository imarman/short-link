package com.arman.shortlink.common.convention;

import lombok.Getter;

import java.io.Serial;

/**
 * @author Arman
 * @date 2023/8/30
 */
@Getter
public class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2211470029146622964L;

    private final RespEnum respEnum;

    private final String msg;

    public BizException(RespEnum respEnum) {
        super(respEnum.getMsg());
        this.respEnum = respEnum;
        this.msg = respEnum.getMsg();
    }

    public BizException(RespEnum respEnum, String msg) {
        super(msg);
        this.respEnum = respEnum;
        this.msg = msg;
    }

}
