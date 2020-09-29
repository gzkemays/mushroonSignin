package com.gzkemays.mushroom.signin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.SigninApplication;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.mapper.MuVipMapper;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.MuVip;
import com.gzkemays.mushroom_sign.po.dto.MuVipDTO;
import com.gzkemays.mushroom_sign.po.vo.MuDisposeVO;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.service.HttpGetService;
import com.gzkemays.mushroom_sign.service.MuSignInService;
import com.gzkemays.mushroom_sign.service.MuUserService;
import com.gzkemays.mushroom_sign.service.MuVipService;
import com.gzkemays.mushroom_sign.utils.MuArticleUtils;
import com.gzkemays.mushroom_sign.utils.MuVipCode;
import com.gzkemays.mushroom_sign.utils.MuWeekDisposeUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SigninApplication.class)
class SigninApplicationTests {
    @Autowired
    private MuUserService muUserService;
    @Autowired
    private MuSignInService muSignInService;
    @Autowired
    private MuUserMapper muUserMapper;
    @Autowired
    private MuVipMapper muVipMapper;
    @Autowired
    private MuVipService muVipService;
    @Autowired
    private HttpGetService httpGetService;
    @Test
    void contextLoads() throws Exception {
//        // 模拟用户登陆
//        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
//            wrapper.eq("phone","18925800360");
//        MuUser muUser = muUserMapper.selectOne(wrapper);
//        MuLoginVO muLoginVO = new MuLoginVO();
//            BeanUtils.copyProperties(muUser,muLoginVO);
//        String key = httpGetService.getAuthorization(muLoginVO);
//        // 获取 VIP 用户
//        MuVip muVip = muVipService.getMsgByUserId(muUser.getId());
//        if (muVip != null) {
//            MuVipDTO dto = MuWeekDisposeUtils.getVipAllMsg(muVip,muUser,httpGetService.getWeekMessage(muUser,key));
//            // 叠加周次并更新当前周报时间
//            muVipService.updateVipWeekTime(dto);
//            // 发布周报，如果 is_used 为 0 则发布
//            if (!muVip.getIsUsed()) {
//                httpGetService.getWeekReport(dto,key);
//                muVipService.updateVipState(dto);
//            }
//        }
//        muVipService.initVipState();
        System.out.println("MuVipCode.stringMD5() = " + MuVipCode.adminGetVipCode("gzkemays"));








    }


}
