package com.gzkemays.mushroom_sign.service;

import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuSignInVO;

public interface HttpGetService {
    String getAuthorization(MuLoginVO muUser) throws Exception;
    String getPlanId(String authorization) throws Exception;
    void getSignIn(MuSignInVO vo, String key) throws Exception;
}
