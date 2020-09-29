package com.gzkemays.mushroom_sign.exception;

import com.gzkemays.mushroom_sign.enums.CommonEnum;
import com.gzkemays.mushroom_sign.exception.MushroomException;
import com.gzkemays.mushroom_sign.exception.Result;
import com.gzkemays.mushroom_sign.utils.MuResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class MushroomExceptionHandler {
//    @ExceptionHandler(value = MushroomException.class)
//    @ResponseBody
//    public ModelAndView mushroomHandler (MushroomException e, HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("register");
//        modelAndView.addObject("msg",e.getMsg());
//        modelAndView.addObject("code",e.getCode());
//        modelAndView.addObject("url",request.getRequestURL());
//        return modelAndView;
//    }

    @ExceptionHandler(value = MushroomException.class)
    @ResponseBody
    public Result mushroomHandler (MushroomException e) {
        return MuResultUtils.ERROR(e);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler (Exception e, HttpServletRequest request) {
        return MuResultUtils.ERROR(CommonEnum.ERROR);
    }
}
