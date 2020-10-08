package com.gzkemays.mushroom_sign.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MuUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date lastmodifytime;

    /**
     * 假删除状态
     */
    private Boolean isUsed;


}
