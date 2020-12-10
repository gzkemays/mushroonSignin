package com.gzkemays.signin.po;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author gzkemays
 * @since 2020-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KmSingle implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录信息码
     */
    private String sign;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 获取单点登录的学号
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 最后修改时间
     */
    private Date lastmodifytime;

    /**
     * 可用状态
     */
    private Integer isUsed;


}
