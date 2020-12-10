package com.gzkemays.signin.controller;


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

