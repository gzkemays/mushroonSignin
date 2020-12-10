package com.gzkemays.signin.controller;


import com.gzkemays.signin.exception.Result;
import com.gzkemays.signin.po.KmUser;
import com.gzkemays.signin.service.KmUserService;
import com.gzkemays.signin.utils.MuResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gzkemays
 * @since 2020-10-11
 */
@Controller
public class KmUserController {
    @Autowired
    private KmUserService kmUserService;
    @GetMapping("/kmsignin")
    public String getKmReg() {
        return "kmRegister";
    }
    @PostMapping("/regKm")
    @ResponseBody
    public Result postKmReg(KmUser user, Model model) {
        if (kmUserService.insertUser(user)) {
            return MuResultUtils.OK("注册成功");
        }
        return MuResultUtils.FAIL("注册失败");
    }
}

