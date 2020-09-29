package com.gzkemays.mushroom_sign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.po.MuCode;
import com.gzkemays.mushroom_sign.mapper.MuCodeMapper;
import com.gzkemays.mushroom_sign.service.MuCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzkemays.mushroom_sign.utils.MuVipCodeUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-29
 */
@Service
public class MuCodeServiceImpl extends ServiceImpl<MuCodeMapper, MuCode> implements MuCodeService {

    @Override
    public boolean getCodeState(String code) {
        QueryWrapper<MuCode> wrapper = new QueryWrapper<>();
            wrapper.eq("vipcode",code);
        return baseMapper.selectOne(wrapper).getIsUsed();
    }

    @Override
    public boolean hasCode(String code) {
        QueryWrapper<MuCode> wrapper = new QueryWrapper<>();
            wrapper.eq("vipcode",code);
        if (baseMapper.selectOne(wrapper) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void saveOrUpdateCodeState(String auth) {
        MuCode muCode = new MuCode();
            muCode.setAuth(auth);
            muCode.setVipcode(MuVipCodeUtils.adminGetVipCode(auth));
        baseMapper.insert(muCode);
    }

    @Override
    public void updateCodeState(MuCode code) {
        QueryWrapper<MuCode> wrapper = new QueryWrapper<>();
            wrapper.eq("vipcode",code.getVipcode()).select("is_used");
        MuCode muCode = baseMapper.selectOne(wrapper);
        if (muCode != null) {
            QueryWrapper<MuCode> state = new QueryWrapper<>();
                state.select("is_used");
            muCode.setIsUsed(false);
            baseMapper.update(muCode,state);
        }
    }
}
