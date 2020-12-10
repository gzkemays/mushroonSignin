package com.gzkemays.signin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gzkemays.signin.exception.MushroomException;
import com.gzkemays.signin.po.MuUser;
import com.gzkemays.signin.po.dto.MuVipDTO;
import com.gzkemays.signin.po.vo.MuDisposeVO;
import com.gzkemays.signin.po.vo.MuLoginVO;
import com.gzkemays.signin.po.vo.MuSignInVO;
import com.gzkemays.signin.service.HttpGetService;
import com.gzkemays.signin.service.MuUserService;
import com.gzkemays.signin.utils.HttpUtils;
import com.gzkemays.signin.utils.MuHttpPost;
import com.gzkemays.signin.utils.MuWeekDisposeUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;


@Service
public class HttpGetServiceImpl implements HttpGetService {
    @Autowired
    private MuUserService userService;
    /**
     * 获取 Authorization 用于登陆。
     * @param vo
     * @return Authorization
     */
    @Override
    public String getAuthorization(MuLoginVO vo) throws Exception {
        JSONObject[] res = setPostByJson((JSONObject) JSONObject.toJSON(vo),"token",null);
        String token;
        System.out.println("res = " + res[0]);
        System.out.println("res[0].get(\"msg\").toString() = " + res[0].get("msg").toString());
        if (res[0].get("msg").equals("Gateway")) {
            System.out.println("执行重试");
            token = getAuthorization(vo);
        } else {
            Integer code = (Integer) res[0].get("code");
            JSONObject data = (JSONObject) res[0].get("data");
            if (code != 200) {
                if (userService.isRegister(vo.getPhone()) && res[0].get("msg").toString().contains("密码错误")) {
                    return null;
                } else throw new MushroomException(0,res[0].get("msg"));
            } else {
                if (data == null) {
                    return null;
                } else {
                    token = data.get("token").toString();
                }
            }
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
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            Integer code =  (Integer)jsonObject.get("code");
//            System.out.println(code);
//            if (code != 200) {
//                throw new MushroomException(0,"1");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result);
        if (result.contains("504 Gateway Time-out") || result.contains("502 Gateway")) {
            System.out.println("Get GateWay Error");
            return new JSONObject[]{JSONObject.parseObject("{\"msg\":\"Gateway\"}")};
        }
        return new JSONObject[]{JSONObject.parseObject(result)};
    }
}
