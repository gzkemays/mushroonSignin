package com.gzkemays.signin.enums;

import lombok.Getter;

@Getter
public enum CommonEnum {
    ERROR(-1,"系统发生未知错误，请联系管理员！");

    /** 错误码 */
    private Integer code;
    /** 错误信息 */
    private String msg;

    CommonEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
