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
    // 根据 userId 返回 VIP 信息
    MuVip getMsgByUserId(Long id);
    // 使用激活码注册时
    boolean useCodeRegVip(MuVipCodeRegVO muVipCodeRegVO) throws Exception;
    // 使用激活码注册已有账号
    boolean registerVip(MuVipRegVO vipRegVO);
    // 判断用户是否已经是 VIP
    boolean userIsVip(MuVipRegVO vipRegVO);
    // 更新周次
    void updateVipWeekTime(MuVipDTO dto);
    // 更新发布状态
    void updateVipState (MuVipDTO dto);
    // 初始化发布状态（每周任务开始时启动）
    void initVipState ();

}
