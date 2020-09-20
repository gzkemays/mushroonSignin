package com.gzkemays.mushroom_sign.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuSignInVO;
import com.gzkemays.mushroom_sign.service.HttpGetService;
import com.gzkemays.mushroom_sign.utils.HttpUtils;
import com.gzkemays.mushroom_sign.utils.MuHttpPost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;


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
        System.out.println("res = " + res[0].toString());
        JSONObject data = (JSONObject) res[0].get("data");
        String token ;
        if (data == null) {
            token = null;
        } else {
            token = data.get("token").toString();
        }
        System.out.println("data = " + data);
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

    @Override
    public void getSignIn(MuSignInVO vo, String key) throws Exception {
        System.out.println("登陆打卡！！！！");
        setPostByJson((JSONObject) JSONObject.toJSON(vo),"sign",key);
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
        return new JSONObject[]{JSONObject.parseObject(result)};
    }

}
