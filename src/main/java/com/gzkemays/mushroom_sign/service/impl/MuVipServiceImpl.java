package com.gzkemays.mushroom_sign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.MuVip;
import com.gzkemays.mushroom_sign.mapper.MuVipMapper;
import com.gzkemays.mushroom_sign.po.dto.MuVipDTO;
import com.gzkemays.mushroom_sign.po.vo.MuVipRegVO;
import com.gzkemays.mushroom_sign.service.MuUserService;
import com.gzkemays.mushroom_sign.service.MuVipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzkemays.mushroom_sign.utils.MuWeekDisposeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-27
 */
@Service
public class MuVipServiceImpl extends ServiceImpl<MuVipMapper, MuVip> implements MuVipService {
    @Autowired
    private MuUserMapper muUserMapper;

    @Override
    public MuVip getMsgByUserId(Long id) {
        QueryWrapper<MuVip> wrapper = new QueryWrapper<>();
            wrapper.eq("userId",id);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean registerVip(MuVipRegVO vipRegVO) {
        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
            wrapper.select("id").eq("phone",vipRegVO.getPhone());
        MuUser muUser = muUserMapper.selectOne(wrapper);
        if (muUser != null) {
            MuVip muVip = new MuVip();
                muVip.setUserId(muUser.getId());
                muVip.setWeeks(vipRegVO.getWeeks());
            baseMapper.insert(muVip);
            return true;
        }
        return false;
    }

    @Override
    public void updateVipWeekTime(MuVipDTO dto) {
        MuVip muVip = new MuVip();
        Integer weeks = MuWeekDisposeUtils.getWeekNumber(dto.getWeeks());
        dto.setWeeks("第"+ (weeks + 1) +"周");
        BeanUtils.copyProperties(dto,muVip);
        baseMapper.updateById(muVip);
    }

    @Override
    public void updateVipState(MuVipDTO dto) {
        QueryWrapper<MuVip> wrapper = new QueryWrapper<>();
            wrapper.eq("userId",dto.getMuUser().getId()).select("is_used");
        MuVip vip = baseMapper.selectOne(wrapper);
        BeanUtils.copyProperties(dto,vip);
        if (!vip.getIsUsed()) {
            vip.setIsUsed(true);
        }
        baseMapper.updateById(vip);
    }

    @Override
    public void initVipState() {
        QueryWrapper<MuVip> wrapper = new QueryWrapper<>();
            wrapper.select("is_used").select("id");
        List<MuVip> vipList = baseMapper.selectList(wrapper);
        for (MuVip vip : vipList) {
            vip.setIsUsed(false);
        }
        this.updateBatchById(vipList);
    }
}
