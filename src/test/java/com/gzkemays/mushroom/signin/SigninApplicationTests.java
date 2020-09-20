package com.gzkemays.mushroom.signin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.SigninApplication;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuSignInVO;
import com.gzkemays.mushroom_sign.service.HttpGetService;
import com.gzkemays.mushroom_sign.service.MuSignInService;
import com.gzkemays.mushroom_sign.service.MuUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

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
    @Test
    void contextLoads() throws Exception {
        muSignInService.reDuty("END");
    }


}
