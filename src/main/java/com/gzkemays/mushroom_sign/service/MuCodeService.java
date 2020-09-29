package com.gzkemays.mushroom_sign.service;

import com.gzkemays.mushroom_sign.po.MuCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-29
 */
public interface MuCodeService extends IService<MuCode> {
    boolean getCodeState (String code);
    boolean hasCode (String code);
    void saveOrUpdateCodeState(String auth);
    void updateCodeState (MuCode code);

}
