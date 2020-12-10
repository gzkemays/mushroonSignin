package com.gzkemays.signin.po;

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
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MuVip implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("userId")
    private Long userId;


    /**
     * 周数
     */
    private String weeks;

    /**
     * 开始时间
     */
    @TableField("startTime")
    private String startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    private String endTime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 最后修改时间
     */
    private Date lastmodifytime;

    /**
     * 假删除状态
     */
    private Boolean isUsed;


}
