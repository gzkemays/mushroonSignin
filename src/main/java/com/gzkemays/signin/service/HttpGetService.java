package com.gzkemays.signin.service;

import com.gzkemays.signin.po.MuUser;
import com.gzkemays.signin.po.dto.MuVipDTO;
import com.gzkemays.signin.po.vo.MuLoginVO;
import com.gzkemays.signin.po.vo.MuSignInVO;

import java.util.Map;

public interface HttpGetService {
    String getAuthorization(MuLoginVO muUser) throws Exception;
    String getPlanId(String authorization) throws Exception;
    Map<String,String> getWeekMessage(MuUser vo, String key) throws Exception;
    void getSignIn(MuSignInVO vo, String key) throws Exception;
    void getWeekReport(MuVipDTO dto, String key) throws Exception;

}
