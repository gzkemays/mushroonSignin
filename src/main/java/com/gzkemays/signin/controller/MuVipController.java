package com.gzkemays.signin.controller;


import com.gzkemays.signin.exception.Result;
import com.gzkemays.signin.po.vo.MuVipRegVO;
import com.gzkemays.signin.service.MuVipService;
import com.gzkemays.signin.utils.MuResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

