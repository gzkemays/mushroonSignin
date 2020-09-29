package com.gzkemays.mushroom_sign.po.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gzkemays.mushroom_sign.po.MuUser;
import lombok.Data;

import java.util.Date;

@Data
public class MuVipDTO {
    private static final long serialVersionUID=1L;

    private Long id;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 周数
     */
    private String weeks;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
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

    /**
     *  用户信息
     */
    private MuUser muUser;
}
