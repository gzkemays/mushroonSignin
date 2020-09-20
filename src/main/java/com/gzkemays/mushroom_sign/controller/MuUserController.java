package com.gzkemays.mushroom_sign.controller;


import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.service.MuUserService;
import com.gzkemays.mushroom_sign.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
    @Autowired
    private MuUserService userService;
    @PostMapping(value = "/saveUser")
    @ResponseBody
    public String saveUser (MuUser user) {
        String msg;
        if (userService.saveUser(user)) {
            msg = "谢谢参与!";
        } else {
            msg = "你已经在测试名单，请勿捣乱！";
        }
        return msg;
    }
    @GetMapping("/reg")
    public String getRegUUID (HttpServletRequest request, HttpSession session) {
        String uuid = UUIDUtils.getUUID();
        ArrayList<String> saveList = (ArrayList<String>) session.getAttribute("session");
        if (saveList.size() == 0) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(uuid);
        } else {
            saveList.add(uuid);
            request.setAttribute("session",saveList);
        }
        return uuid;
    }
    @GetMapping("/")
    public String getIndexPage () {
        return "register";
    }
}

