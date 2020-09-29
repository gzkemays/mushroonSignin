package com.gzkemays.mushroom_sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.exception.MushroomException;
import com.gzkemays.mushroom_sign.mapper.MuCodeMapper;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.po.MuCode;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.MuVip;
import com.gzkemays.mushroom_sign.mapper.MuVipMapper;
import com.gzkemays.mushroom_sign.po.dto.MuVipDTO;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuVipCodeRegVO;
import com.gzkemays.mushroom_sign.po.vo.MuVipRegVO;
import com.gzkemays.mushroom_sign.service.HttpGetService;
import com.gzkemays.mushroom_sign.service.MuCodeService;
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
import java.util.Objects;

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
    @Autowired
    private MuUserService muUserService;
    @Autowired
    private MuCodeMapper muCodeMapper;
    @Autowired
    private MuCodeService muCodeService;
    @Autowired
    private HttpGetService httpGetService;

    @Override
    public MuVip getMsgByUserId(Long id) {
        QueryWrapper<MuVip> wrapper = new QueryWrapper<>();
            wrapper.eq("userId",id);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean useCodeRegVip(MuVipCodeRegVO muVipCodeRegVO) throws Exception {
        String code = muVipCodeRegVO.getVipcode().trim();
        QueryWrapper<MuCode> muCodeQueryWrapper = new QueryWrapper<>();
            muCodeQueryWrapper.eq("vipcode", code);
        QueryWrapper<MuUser> muUserQueryWrapper = new QueryWrapper<>();
            muUserQueryWrapper.eq("phone", muVipCodeRegVO.getPhone());
        if (!Objects.equals(code, "") && muCodeMapper.selectOne(muCodeQueryWrapper) == null){
            throw new MushroomException(0,"没有该邀请码");
        }
        else if (!Objects.equals(code, "") && !muCodeService.getCodeState(code)){
            throw new MushroomException(0,"邀请码不可用");
        }
        else if (muUserMapper.selectOne(muUserQueryWrapper) != null){
            throw new MushroomException(0,"手机号已被注册");
        } else {
            // 邀请码存在且可用，且该手机号未注册。
            // 注册账号
            MuUser muUser = new MuUser();
                BeanUtils.copyProperties(muVipCodeRegVO, muUser);
            MuLoginVO loginVO = new MuLoginVO();
                BeanUtils.copyProperties(muUser, loginVO);
            String token = httpGetService.getAuthorization(loginVO);
            if (token.length() < 10) {
                throw new MushroomException(0, token);
            }
            muUserService.saveUser(muUser);
            // 注册VIP
            if (muCodeMapper.selectOne(muCodeQueryWrapper) != null){
                MuVipRegVO vipRegVO = new MuVipRegVO();
                BeanUtils.copyProperties(muVipCodeRegVO, vipRegVO);
                this.registerVip(vipRegVO);
                // 更新邀请码状态
                MuCode muCode = new MuCode();
                muCode.setVipcode(code);
                muCodeService.updateCodeState(muCode);
            }

            return true;
        }
    }

    @Override
    public boolean registerVip(MuVipRegVO vipRegVO) {
        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
            wrapper.select("id").eq("phone",vipRegVO.getPhone());
        MuUser muUser = muUserMapper.selectOne(wrapper);

        if (muCodeService.hasCode(vipRegVO.getVipcode())) {
            if (!muCodeService.getCodeState(vipRegVO.getVipcode())) throw new MushroomException(0,"激活码已被使用");
            if (this.userIsVip(vipRegVO)) throw new MushroomException(0,"您已是VIP用户");
            if (muUser != null) {
                MuVip muVip = new MuVip();
                muVip.setUserId(muUser.getId());
                muVip.setWeeks(vipRegVO.getWeeks());
                baseMapper.insert(muVip);
                MuCode muCode = new MuCode();
                    muCode.setVipcode(vipRegVO.getVipcode());
                muCodeService.updateCodeState(muCode);
                return true;
            } else {
                throw new MushroomException(0,"没有该账号！");
            }
        } else {
            throw new MushroomException(0,"没有该激活码！");
        }

    }

    @Override
    public boolean userIsVip(MuVipRegVO vipRegVO) {
        Long userId = muUserService.getIdByPhone(vipRegVO.getPhone());
        QueryWrapper<MuVip> wrapper = new QueryWrapper();
            wrapper.eq("userId",userId);
        return baseMapper.selectOne(wrapper) != null;
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
