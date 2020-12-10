package com.gzkemays.signin.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2020-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MuCode implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * code ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布者
     */
    private String auth;

    /**
     * code
     */
    private String vipcode;

    /**
     * 插入时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastmodifytime;

    /**
     * 使用状态
     */
    private Boolean isUsed;


}
