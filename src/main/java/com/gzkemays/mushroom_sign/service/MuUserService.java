package com.gzkemays.mushroom_sign.service;

import com.gzkemays.mushroom_sign.po.MuUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-17
 */
public interface MuUserService extends IService<MuUser> {
    List<MuUser> getUser();
    boolean saveUser(MuUser user);
    void updateToken(String token);
    void updatePlan(String phone, String plan);
    void updateState(String phone, String state);

}
