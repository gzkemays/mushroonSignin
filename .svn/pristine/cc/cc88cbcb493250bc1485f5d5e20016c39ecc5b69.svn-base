package com.gzkemays.mushroom_sign.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MethodExceptionHandler {
    @ExceptionHandler(MethodException.class)
    public Map<String, Object> handlerMethodException (MethodException e) {
        Map<String,Object> result = new HashMap<>();
        result.put("msg",e.getMessage());
        result.put("error","没有该方法");
        return result;
    }

    @ExceptionHandler(MethodRunTimeException.class)
    public Map<String, Object> handlerMethodRunTimeException (MethodRunTimeException e) {
        Map<String,Object> result = new HashMap<>();
        result.put("msg",e.getMessage());
        result.put("error","没有该方法");
        return result;
    }
}
