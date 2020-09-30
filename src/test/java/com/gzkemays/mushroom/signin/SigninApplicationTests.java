package com.gzkemays.mushroom.signin;

import com.gzkemays.mushroom_sign.SigninApplication;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.mapper.MuVipMapper;
import com.gzkemays.mushroom_sign.po.MuCode;
import com.gzkemays.mushroom_sign.service.*;
import com.gzkemays.mushroom_sign.utils.MuVipCodeUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


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
    @Autowired
    private MuCodeService muCodeService;
    @Test
    void contextLoads() throws Exception {
        System.out.println("MuVipCodeUtils.stringMD5() = " + MuVipCodeUtils.adminGetVipCode("gzkemays"));
    }

    /**
     * 打卡
     */
    @Test
    void MushroomSignIn() throws Exception {
        muSignInService.reDuty("START");
    }

    /**
     * 生成激活码
     */
    @Test
    void ManualSetCode() {
        int i = 0;
        while ( i < 5) {
            muCodeService.saveOrUpdateCodeState("gzkemays");
            i++;
        }

    }


}
