package com.gzkemays.signin.po.vo;

import lombok.Data;

@Data
public class MuLoginVO {
    private String phone;
    private String password;

    private String loginType = "android";
}
