package com.gzkemays.mushroom_sign.service;

import com.gzkemays.mushroom_sign.po.MuVip;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gzkemays.mushroom_sign.po.dto.MuVipDTO;
import com.gzkemays.mushroom_sign.po.vo.MuVipCodeRegVO;
import com.gzkemays.mushroom_sign.po.vo.MuVipRegVO;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-27
 */
public interface MuVipService extends IService<MuVip> {
    MuVip getMsgByUserId(Long id);
    boolean useCodeRegVip(MuVipCodeRegVO muVipCodeRegVO) throws Exception;
    boolean registerVip(MuVipRegVO vipRegVO);
    void updateVipWeekTime(MuVipDTO dto);
    void updateVipState (MuVipDTO dto);
    void initVipState ();

}
