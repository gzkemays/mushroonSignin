package com.gzkemays.signin.service;

import com.gzkemays.signin.po.KmUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gzkemays
 * @since 2020-10-11
 */
public interface KmUserService extends IService<KmUser> {
    boolean insertUser (KmUser user);
    List<KmUser> getAllUserMsg ();
}
