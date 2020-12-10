package com.gzkemays.signin.po.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class MuVipCodeRegVO {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

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
     * 手机系统
     */
    private String device;

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
    @TableField("planId")
    private String planId;

    /**
     * 临时 token
     */
    @TableField(value = "temp_token",exist = false)
    private String tempToken;

    /**
     * 打卡状态
     */
    private String type;

    /**
     * 邀请码
     */
    private String vipcode;

    /**
     * 周报周次
     */
    private String weeks;
}
