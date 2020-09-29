package com.gzkemays.mushroom_sign.config;

import com.gzkemays.mushroom_sign.service.MuSignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SignInTask {
    @Autowired
    private MuSignInService muSignInService;

    @Scheduled(cron = "0 30 07 * * ?")
    private void MushroomSignInTask () throws Exception {
        muSignInService.reDuty("START");
        System.out.println("\"打卡定时器\" = " + "上班打卡");
    }

    @Scheduled(cron = "0 30 17 * * ?")
    private void MushroomSignOutTask () throws Exception {
        muSignInService.reDuty("END");
        System.out.println("\"打卡定时器\" = " + "下班打卡");
    }

    // 会员版
    @Scheduled(cron = "0 30 11 ? * MON")
    private void MushroomVipDisposeWeek () throws Exception {
        muSignInService.weekArticle();
        System.out.println("\"VIP 周报填写\" = " + "VIP 周报填写");
    }
}
