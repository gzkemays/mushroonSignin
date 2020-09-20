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
    @Scheduled(cron = "0 10 00 * * ?")
    private void MushroomSignInTask () throws Exception {
        muSignInService.reDuty("START");
        System.out.println("\"打卡定时器\" = " + "上班打卡");
    }

    @Scheduled(cron = "0 11 00 * * ?")
    private void MushroomSignOutTask () throws Exception {
        muSignInService.reDuty("END");
        System.out.println("\"打卡定时器\" = " + "下班打卡");
    }
}
