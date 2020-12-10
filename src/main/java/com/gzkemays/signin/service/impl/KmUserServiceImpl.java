package com.gzkemays.signin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.signin.exception.MushroomException;
import com.gzkemays.signin.po.KmUser;
import com.gzkemays.signin.mapper.KmUserMapper;
import com.gzkemays.signin.service.KmUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gzkemays
 * @since 2020-10-11
 */
@Service
public class KmUserServiceImpl extends ServiceImpl<KmUserMapper, KmUser> implements KmUserService {

    @Override
    public boolean insertUser(KmUser user) {
        QueryWrapper<KmUser> wrapper = new QueryWrapper<>();
            wrapper.eq("number",user.getNumber());
        if (baseMapper.selectOne(wrapper) == null) {
            this.baseMapper.insert(user);
            return true;
        }
        else
            throw new MushroomException(0,"您已登记");
    }

    @Override
    public List<KmUser> getAllUserMsg() {
        return baseMapper.selectList(null);
    }
}
