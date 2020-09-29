package com.gzkemays.mushroom_sign.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.MuVip;
import com.gzkemays.mushroom_sign.po.dto.MuVipDTO;
import com.gzkemays.mushroom_sign.po.vo.MuDisposeVO;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuSignInVO;
import com.gzkemays.mushroom_sign.service.HttpGetService;
import com.gzkemays.mushroom_sign.utils.HttpUtils;
import com.gzkemays.mushroom_sign.utils.MuArticleUtils;
import com.gzkemays.mushroom_sign.utils.MuHttpPost;
import com.gzkemays.mushroom_sign.utils.MuWeekDisposeUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class HttpGetServiceImpl implements HttpGetService {
    /**
     * 获取 Authorization 用于登陆。
     * @param vo
     * @return Authorization
     */
    @Override
    public String getAuthorization(MuLoginVO vo) throws Exception {
        JSONObject[] res = setPostByJson((JSONObject) JSONObject.toJSON(vo),"token",null);
        JSONObject data = (JSONObject) res[0].get("data");
        String token ;
        if (data == null) {
            token = res[0].get("msg").toString();
        } else {
            token = data.get("token").toString();
        }
        return token;
    }

    /**
     * 获取 planId 赋予权限并进行操作
     * @param authorization
     * @return
     */
    @Override
    public String getPlanId(String authorization) throws Exception {
        String state = "{\"state\": \"\"}";
        JSONObject[] res = setPostByJson(JSONObject.parseObject(state),"plan",authorization);
        String data = res[0].get("data").toString();
        JSONObject json = JSONObject.parseObject(data.replace("[","").replace("]",""));
        return json.get("planId").toString();
    }

    /**
     * 获取打卡信息和 key
     * @param vo
     * @param key
     * @throws Exception
     */
    @Override
    public void getSignIn(MuSignInVO vo, String key) throws Exception {
        setPostByJson((JSONObject) JSONObject.toJSON(vo),"sign",key);
    }

    @Override
    public void getWeekReport(MuVipDTO dto, String key) throws Exception {
        MuDisposeVO vo = MuWeekDisposeUtils.getDisposeMsg(dto);
        System.out.println("vo = " + vo);
        setPostByJson((JSONObject)JSONObject.toJSON(vo),"post_week",key);
    }

    /**
     * 获取最新周数
     * @param user
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,String> getWeekMessage(MuUser user, String key) throws Exception {
        String req = "{\"planId\":\" " + user.getPlanId() + " \"}";
        JSONObject[] res = setPostByJson(JSONObject.parseObject(req), "week_msg", key);
        String data = res[0].get("data").toString();
        return MuWeekDisposeUtils.getDisposeTime(data);
    }

    /**
     * 数据的交互
     * @param json
     * @param method
     * @param key
     * @return
     * @throws Exception
     */
    private JSONObject[] setPostByJson (JSONObject json, String method, String key) throws Exception {
        CloseableHttpClient httpClient = HttpUtils.getIgnoeSSLClient();
        CloseableHttpResponse response;
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setSocketTimeout(20000)
                                                   .setConnectTimeout(20000)
                                                   .setConnectionRequestTimeout(20000)
                                                   .build();
        String result = "";
        try {
            HttpPost httpPost = MuHttpPost.getPost(method,json.toString(),key);
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(),"utf-8");
            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result);
        return new JSONObject[]{JSONObject.parseObject(result)};
    }
}
