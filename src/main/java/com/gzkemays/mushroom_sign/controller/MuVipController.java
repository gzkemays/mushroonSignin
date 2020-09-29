package com.gzkemays.mushroom_sign.controller;


import com.gzkemays.mushroom_sign.exception.MushroomException;
import com.gzkemays.mushroom_sign.exception.Result;
import com.gzkemays.mushroom_sign.po.vo.MuVipCodeRegVO;
import com.gzkemays.mushroom_sign.po.vo.MuVipRegVO;
import com.gzkemays.mushroom_sign.service.MuVipService;
import com.gzkemays.mushroom_sign.utils.MuResultUtils;
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
@RestController
public class MuVipController {
    @Autowired
    private MuVipService muVipService;
    @PostMapping("/activeVip")
    @ResponseBody
    private Result activeVip (MuVipRegVO muVipRegVO) throws Exception {
        System.out.println("muVipRegVO = " + muVipRegVO);
        if (muVipService.registerVip(muVipRegVO)) {
            return MuResultUtils.OK("激活成功！");
        }
        return MuResultUtils.FAIL("激活失败！");
    }
}

