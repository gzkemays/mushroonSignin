package com.gzkemays.signin.po.vo;

import lombok.Data;

@Data
public class MuDisposeVO {


    /**
     * 数据类型
     */
    private String reportType = "week";

    /**
     * 地址
     */
    private String address = "";

    /**
     * 周数
     */
    private String weeks;

    /**
     * 纬度
     */
    private String latitude = "0.0";

    /**
     * 用户的 planId
     */
    private String planId;


    /**
     * 伪数据
     */
    private String yearmonth = "";

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 伪数据
     */
    private String longitude = "0.0";

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
