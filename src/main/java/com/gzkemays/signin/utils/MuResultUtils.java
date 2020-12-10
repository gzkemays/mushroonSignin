package com.gzkemays.signin.utils;

import com.gzkemays.signin.enums.CommonEnum;
import com.gzkemays.signin.exception.MushroomException;
import com.gzkemays.signin.exception.Result;
import org.springframework.beans.BeanUtils;

public class MuResultUtils {
    public static Result OK (Object object) {
        Result result = new Result();
            result.setCode(1);
            result.setMsg("success");
            result.setData(object);
        return result;
    }
    public static Result ERROR (CommonEnum commonEnum) {
        Result result = new Result();
            BeanUtils.copyProperties(commonEnum, result);
        return result;
    }

    public static Result FAIL (String msg) {
        Result result = new Result();
            result.setCode(0);
            result.setMsg(msg);
        return result;
    }

    public static Result ERROR (MushroomException e) {
        Result result = new Result();
            result.setMsg(e.getMsg());
            result.setCode(e.getCode());
        return result;
    }

//    public static MushroomException ERROR (MushroomException e) {
//        return e;
//    }
}
