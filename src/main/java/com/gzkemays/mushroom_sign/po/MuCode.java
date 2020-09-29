package com.gzkemays.mushroom_sign.po;

import com.baomidou.mybatisplus.annotation.IdType;
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
     * code
     */
    private Long vipcode;

    /**
     * 使用状态
     */
    private Integer isUsed;


}
