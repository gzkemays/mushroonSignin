package com.gzkemays.mushroom_sign.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDUtils {
    private static ThreadLocal<String> tlSDF = new ThreadLocal<>();
    /**
     * 生成 UUID
     */
    public static String getUUID(){
        tlSDF.set(new SimpleDateFormat("DHHmmss").format(new Date()));
        Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
        if(uuidHashCode<0){
            uuidHashCode = -1*uuidHashCode;
        }
        String date = tlSDF.get();
        return date+uuidHashCode;
    }
}
