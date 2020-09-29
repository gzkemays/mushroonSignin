package com.gzkemays.mushroom_sign.service;

import com.alibaba.fastjson.JSONObject;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.MuVip;
import com.gzkemays.mushroom_sign.po.dto.MuVipDTO;
import com.gzkemays.mushroom_sign.po.vo.MuDisposeVO;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuSignInVO;

import java.util.ArrayList;
import java.util.Map;

public interface HttpGetService {
    String getAuthorization(MuLoginVO muUser) throws Exception;
    String getPlanId(String authorization) throws Exception;
    Map<String,String> getWeekMessage(MuUser vo, String key) throws Exception;
    void getSignIn(MuSignInVO vo, String key) throws Exception;
    void getWeekReport(MuVipDTO dto, String key) throws Exception;

}
