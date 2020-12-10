package com.gzkemays.signin.service;

import com.gzkemays.signin.po.MuUser;
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
    // true 已注册，false 未注册。
    boolean isRegister(String phone);
    boolean saveUser(MuUser user);
    Long getIdByPhone(String phone);
    void updateToken(String token);
    void updatePlan(String phone, String plan);
    void updateState(String phone);

}
