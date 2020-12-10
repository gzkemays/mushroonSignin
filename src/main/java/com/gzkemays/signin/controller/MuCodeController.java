package com.gzkemays.signin.controller;


import com.gzkemays.signin.exception.Result;
import com.gzkemays.signin.po.vo.MuVipCodeRegVO;
import com.gzkemays.signin.service.MuVipService;
import com.gzkemays.signin.utils.MuResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-29
 */
@Controller
public class MuCodeController {
    @Autowired
    private MuVipService muVipService;

    @PostMapping("/useCodeReg")
    @ResponseBody
    private Result useCodeReg (MuVipCodeRegVO vipCodeRegVO, Model model) throws Exception {
        if (muVipService.useCodeRegVip(vipCodeRegVO)) {
            model.addAttribute("res", MuResultUtils.OK("注册成功！"));
            return MuResultUtils.OK("注册成功！");
        }
        return MuResultUtils.FAIL("注册失败");
    }

}

