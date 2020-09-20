package com.gzkemays.mushroom_sign.utils;

import com.gzkemays.mushroom_sign.exceptions.MethodException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

public class MuHttpPost {
    static final String TOKEN_URL = "https://api.moguding.net:9000/session/user/v1/login";
    static final String PLAN_URL = "https://api.moguding.net:9000/practice/plan/v1/getPlanByStu";
    static final String SIGNIN_URL = "https://api.moguding.net:9000/attendence/clock/v1/save";
    static final String USER_AGENT = "Mozilla/5.0 (Linux; U; Android 9.0.0; zh-cn; MI 6X Build/OPR1.170623.027) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    static final String ACCEPT_LANG = "zh-CN,zh;q=0.8";
    static final String AUTHORIZATION = "";
    static final String TOKEN_ROLE = "";
    static final String PLAN_ROLE = "student";
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    static final String HOST = "api.moguding.net:9000";
    static final String CONN = "close";
    static final String ACCEPT_ENCODING = "gzip, deflate";
    static final String CACHE_CONTROL = "no-cache";


    public static HttpPost getPost (String method, String data, String key) throws MethodException {
        HttpPost httpPost;
        if (method.equals("token")) {
            httpPost = new HttpPost(TOKEN_URL);
            httpPost.addHeader("Authorization",AUTHORIZATION);
            httpPost.addHeader("roleKey",TOKEN_ROLE);
        } else if (method.equals("plan") || method.equals("sign")) {
            if (method.equals("plan")) {
                httpPost = new HttpPost(PLAN_URL);
            } else {
                httpPost = new HttpPost("127.0.0.1:8080");
                System.out.println("\"———————— 开始打卡 ——————\" = " + "打卡");
            }
            httpPost.addHeader("Authorization", key);
            httpPost.addHeader("roleKey",PLAN_ROLE);
        } else {
            throw new MethodException("没有定义该方法");
        }
        StringEntity entity = new StringEntity(data, HTTP.UTF_8);
        entity.setContentType("text/xml");
        httpPost.addHeader("Accept-Language",ACCEPT_LANG);
        httpPost.addHeader("User-Agent",USER_AGENT);
        httpPost.addHeader("Content-Type",CONTENT_TYPE);
        httpPost.addHeader("Host",HOST);
        httpPost.addHeader("Connection",CONN);
        httpPost.addHeader("Accept-Encoding",ACCEPT_ENCODING);
        httpPost.addHeader("Cache-Control",CACHE_CONTROL);
        httpPost.setEntity(entity);
        return httpPost;
    }
}