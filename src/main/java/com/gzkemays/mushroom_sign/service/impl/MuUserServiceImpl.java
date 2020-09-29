package com.gzkemays.mushroom_sign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.service.MuUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-17
 */
@Service
public class MuUserServiceImpl extends ServiceImpl<MuUserMapper, MuUser> implements MuUserService {
    @Override
    public List<MuUser> getUser() {
        return baseMapper.selectList(null);
    }

    @Override
    public boolean saveUser(MuUser user) {
        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
            wrapper.select("phone").eq("phone",user.getPhone());
        if (baseMapper.selectOne(wrapper) == null) {
            baseMapper.insert(user);
            return true;
        }
        return false;

    }

    @Override
    public Long getIdByPhone(String phone) {
        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
            wrapper.eq("phone",phone).select("id");
        return baseMapper.selectOne(wrapper).getId();
    }

    @Override
    public void updateToken(String token) {
        MuUser user = new MuUser();
        user.setTempToken(token);
        baseMapper.update(user,null);
    }

    @Override
    public void updatePlan(String phone, String plan) {
        MuUser user = new MuUser();
        if (plan != null) {
            user.setPlanId(plan);
            QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
            wrapper.eq("phone",phone);
            baseMapper.update(user,wrapper);
        }
    }

    @Override
    public void updateState(String phone) {
        MuUser user = new MuUser();
        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
        wrapper.select("type").eq("phone",phone);
        String upType = baseMapper.selectOne(wrapper).getType();
        if (upType.equals("START")) {
            user.setType("END");
        } else {
            user.setType("START");
        }
        System.out.println("\" —————— 更新打卡状态 ——————\" = " + "更新打卡状态");
        baseMapper.update(user,wrapper);
    }
}
