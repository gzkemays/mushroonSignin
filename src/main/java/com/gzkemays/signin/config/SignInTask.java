package com.gzkemays.signin.config;

import com.gzkemays.signin.po.KmSingle;
import com.gzkemays.signin.po.KmUser;
import com.gzkemays.signin.po.vo.KmSinglyVO;
import com.gzkemays.signin.service.KmSignInService;
import com.gzkemays.signin.service.KmSingleService;
import com.gzkemays.signin.service.KmUserService;
import com.gzkemays.signin.service.MuSignInService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class SignInTask {
    @Autowired
    private MuSignInService muSignInService;
    @Autowired
    private KmSignInService kmSignInService;
    @Autowired
    private KmUserService kmUserService;
    @Autowired
    private KmSingleService kmSingleService;

    // 蘑菇钉 - 上班打卡
    @Scheduled(cron = "0 30 08 * * ?")
    private void MushroomSignInTask () throws Exception {
        muSignInService.reDuty("START");
        System.out.println("\"打卡定时器\" = " + "上班打卡");
    }

    // 蘑菇钉 - 下班打卡
    @Scheduled(cron = "0 30 17 * * ?")
    private void MushroomSignOutTask () throws Exception {
        muSignInService.reDuty("END");
        System.out.println("\"打卡定时器\" = " + "下班打卡");
    }

    // 蘑菇钉 - 会员版 - 周报打卡
    @Scheduled(cron = "0 30 11 ? * MON")
    private void MushroomVipDisposeWeek () throws Exception {
        muSignInService.weekArticle();
        System.out.println("\"VIP 周报填写\" = " + "VIP 周报填写");
    }

    // 科贸打卡
    @Scheduled(cron = "0 0 08 * * ?")
    private void KmSignOutTask() throws Exception {
        KmSingle single = kmSingleService.getSingle();
        // 如果数据库没有初始数据则更新。
        if (single == null) {
            KmSinglyVO vo = kmSignInService.getSingleMsg();
            kmSingleService.updateSingle(vo);
        }
        single = kmSingleService.getSingle();
        KmSinglyVO singlyVO = new KmSinglyVO();
        BeanUtils.copyProperties(single,singlyVO);
        String token = kmSignInService.getToken(singlyVO);
        List<KmUser> list = kmUserService.getAllUserMsg();
        for (KmUser user : list) {
            kmSignInService.getKmSignIn(user, token);
        }
    }
}
