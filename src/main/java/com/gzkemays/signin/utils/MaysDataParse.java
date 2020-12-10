package com.gzkemays.signin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MaysDataParse {
    public static MaysDataParse maysDataParse = new MaysDataParse();
    /**
     * 获取 JSON 格式的数据对象
     * @param jsonStr
     * @param spiltStr
     * @return
     */
    public static String getColData (String jsonStr, String spiltStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        while (spiltStr.contains(".")) {
            int index = spiltStr.indexOf(".");
            String data = spiltStr.substring(0,index);
            spiltStr = spiltStr.substring(index).replaceFirst(".","");
            jsonObject = (JSONObject) jsonObject.get(data);
        }
        return jsonObject.getString(spiltStr);
    }

    /**
     * 截取重定向的参数
     */
    public static Map<Object, Object> getQueryParam (String uri) {
        Map<Object, Object> map = new HashMap<>();
        String[] strArr;
        strArr = uri.substring(uri.indexOf("&")).replaceFirst("&","").split("&");
        for (String str : strArr) {
            String[] tempArr;
            tempArr = str.split("=");
            if (tempArr.length > 1) {
                map.put(tempArr[0],tempArr[1]);
            }
        }
        return map;
    }

    /**
     * 对象转换成 Map
     */
    public static Map<Object, Object> getMapFromObject (Object object) throws IllegalAccessException {
        Map<Object, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String key = field.getName();
            Object value = field.get(object);
            map.put(key,value);
        }
        return map;
    }

    /**
     * map 转对象
     * @param target
     * @param source
     * @return
     * @throws IllegalAccessException
     */
    public static Object getObjectFromMap (Object target, Map<Object, Object> source) throws IllegalAccessException {
        Class<?> clazz = target.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(target,source.get(field.getName()));
        }
        return target;
    }

}
