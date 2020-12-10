package com.gzkemays.signin.controller;

import com.gzkemays.signin.po.KmTest;
import com.gzkemays.signin.service.KmSignInService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
public class TestController {


    @Autowired
    private KmSignInService kmSignInService;
    @PostMapping("/testKmSigin")
    @ResponseBody
    public void getKmSigin(HttpServletRequest request, @RequestBody KmTest test1) {
        Enumeration<String> names = request.getHeaderNames();
        System.out.println("test = " + test1);
        while(names.hasMoreElements()) {
            String key = names.nextElement();
            String value = request.getHeader(key);
            System.out.println("key = " + key);
            System.out.println("value = " + value);
        }
    }
}
