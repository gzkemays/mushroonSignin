package com.gzkemays.mushroom_sign.controller;


import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.service.MuUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-17
 */
@Controller
public class MuUserController {

    @GetMapping("/")
    public String getIndexPage () {
        return "register";
    }
}

