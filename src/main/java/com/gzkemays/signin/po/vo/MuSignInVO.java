package com.gzkemays.signin.po.vo;

import lombok.Data;

@Data
public class MuSignInVO {
    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 签到地址
     */
    private String address;

    /**
     * 发表信息
     */
    private String description;

    /**
     * 维度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 蘑菇钉planId
     */
    private String planId;


    /**
     * 打卡状态
     */
    private String type;

    /**
     * 手机系统
     */
    private String device;
}
