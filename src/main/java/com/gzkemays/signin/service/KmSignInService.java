package com.gzkemays.signin.service;

import com.gzkemays.signin.po.KmUser;
import com.gzkemays.signin.po.vo.KmSinglyVO;

/**
 * 科贸自动打卡
 */
public interface KmSignInService {
    void getKmSignIn(KmUser user, String token) throws Exception;
    String getToken (KmSinglyVO vo) throws Exception;
    String getCookie ();
    KmSinglyVO getSingleMsg () throws Exception;
}
