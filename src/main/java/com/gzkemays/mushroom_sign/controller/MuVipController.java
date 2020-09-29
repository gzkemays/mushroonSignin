package com.gzkemays.mushroom_sign.controller;


import com.gzkemays.mushroom_sign.po.vo.MuVipRegVO;
import com.gzkemays.mushroom_sign.service.MuVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-27
 */
@Controller

public class MuVipController {
    @Autowired
    private MuVipService muVipService;

    @GetMapping("/regVip")
    public String getVipRegPage() {
        return "vipreg";
    }

    @PostMapping("/regVip")
    @ResponseBody
    private String regVip (MuVipRegVO muVipRegVO, Model model) {
        if (muVipService.registerVip(muVipRegVO )) {
            return "注册成功。";
        }
        return "注册失败！";
    }
}

