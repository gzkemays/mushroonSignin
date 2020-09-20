package com.gzkemays.mushroom_sign.enums;

import lombok.Getter;

@Getter
public enum ErrorEnums {
    SUCCESS("可能参数异常"),
    FAIL("查询数据库异常,请通知管理员");
    private String msg;
    ErrorEnums(String msg) {
        this.msg = msg;
    }
}
