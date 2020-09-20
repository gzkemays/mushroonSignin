package com.gzkemays.mushroom_sign.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuSignInVO;
import com.gzkemays.mushroom_sign.service.HttpGetService;
import com.gzkemays.mushroom_sign.service.MuSignInService;
import com.gzkemays.mushroom_sign.service.MuUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuSignInServiceImpl implements MuSignInService {
    @Autowired
    private MuUserMapper muUserMapper;
    @Autowired
    private MuUserService muUserService;
    @Autowired
    private HttpGetService httpGetService;
    @Override
    public void reDuty(String state) throws Exception {
        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
        List<MuUser> userList = muUserMapper.selectList(wrapper);
        int i = 0;
        for (MuUser muUser : userList) {
            MushroomSignIn(muUser, state);
            System.out.println("==================== i =================== " + i);
            i++;
        }

        System.out.println("\"打卡完毕\" = " + "打卡完毕 " + i);
    }

    private void MushroomSignIn (MuUser muUser, String state) throws Exception {
        System.out.println("muUser = " + muUser);
        MuLoginVO vo = new MuLoginVO();
        vo.setLoginType("android");
        vo.setPhone(muUser.getPhone());
        vo.setPassword(muUser.getPassword());
        // 登陆获取 token
        String token = httpGetService.getAuthorization(vo);
        System.out.println("token = " + token);
        if (token != null) {
            // 获取 planId
            String planId = httpGetService.getPlanId(token);
            System.out.println("planId = " + planId);
            // planId 插入数据中保存
            muUserService.updatePlan(muUser.getPhone(),planId);
            // 根据 phone 查询信息，并根据当前打卡状态查询（若当前为上班需要：“START”，那么就拥有 START 状态，如果不符合则不打卡且更新）
            QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
            wrapper .select("country","address","province","city","latitude","description","planId","type","device","longitude")
                    .eq("phone",muUser.getPhone()).eq("type",state);
            MuSignInVO muSignInVO = new MuSignInVO();
            MuUser user = muUserService.getBaseMapper().selectOne(wrapper);
            // 避免后来者重复打卡。
            if (user != null) {
                BeanUtils.copyProperties(user,muSignInVO);
                // 登陆打卡
                httpGetService.getSignIn(muSignInVO,token);
                // 更新打卡状态
                muUserService.updateState(muUser.getPhone(), state);
            }



        }

    }


}
