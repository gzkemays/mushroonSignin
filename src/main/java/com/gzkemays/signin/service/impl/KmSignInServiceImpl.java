package com.gzkemays.signin.service.impl;

import com.gzkemays.signin.exception.MushroomException;
import com.gzkemays.signin.po.KmUser;
import com.gzkemays.signin.po.vo.KmSinglyVO;
import com.gzkemays.signin.service.KmSignInService;
import com.gzkemays.signin.utils.MaysDataParse;
import com.gzkemays.signin.utils.MaysRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service
public class KmSignInServiceImpl implements KmSignInService {
    public static final String SINGLE_URL = "https://aic.gdkmxy.cn/wxaic/student/stuCenter/wxAccInt.aspx?Redirect=/yqtj/#/et?type=0";
    public static final String TOKEN_URL = "https://aic.gdkmxy.cn/yqtj/api/v1/SSO";
    public static final String SIGN_IN_URL = "https://aic.gdkmxy.cn/yqtj/api/v1/QuestionnaireResult?isAbnormal=false";
//    public static final String SIGN_IN_URL = "http://127.0.0.1:8888/testKmSigin";
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 10; LIO-AL00 Build/HUAWEILIO-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2691 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/2622 MicroMessenger/7.0.18.1740(0x2700123B) Process/toolsmp WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64";
//    public static final String COOKIE = "ASP.NET_SessionId=35iwfz5tungf0qocu1tnavvz; .ASPXFORMSAUTH=B6AC0919FF601145AEA69DAE30EA189CF05DCDDBB70C75F2CB8BD6A09E52B22696E292FBEBB48EA06E5226DF503098F31A1EE6E8C6BDD3DA05A3A57674FA86A17DDDCDE61AF9C6A5E22893600A1DB07F9CCB992B24C87E8DB6E3F94C9C3F42EDF45F5DE5";
    public static final String COOKIE = "ASP.NET_SessionId=pkuzthukcyz410brkdk5kz1o; .ASPXFORMSAUTH=D452E55B918BFB61DE66EADAAA9CBCA6D573C51ACD442E94FD9B89B3371FD46755933DAFD5848FE0B7AE6296D9BBBFA01FA4C16447CEF829FB6F7AC4C5B1464DD4CD0BFF82C540BDD511E741F737B72EE3404C85D05C3BFB79B8E3E6A703D8BA6456602D";
    @Override
    /**
     * 打卡
     * "Content-Type","application/json;charset=UTF-8" 为必须请求头
     */
    public void getKmSignIn(KmUser user, String token) throws Exception {
        String data = "{\"type\":\"0\",\"userId\":\""+user.getNumber()+"\",\"resultOptionCreateRequests\":[{\"questionId\":\"stu1_q1\",\"optionId\":\"\",\"value\":\""+user.getAddress()+"|["+user.getLatitude()+","+user.getLongitude()+"]\"},{\"questionId\":\"stu1_q2\",\"optionId\":\"\"},{\"questionId\":\"stu1_q3\",\"optionId\":\"\"},{\"questionId\":\"stu1_q4\",\"optionId\":\"\"},{\"questionId\":\"stu1_q5\",\"optionId\":\"\"},{\"questionId\":\"stu1_q6\",\"optionId\":\"\"},{\"questionId\":\"stu1_q7\",\"optionId\":\"\"},{\"questionId\":\"stu1_q8\",\"optionId\":\"\"},{\"questionId\":\"stu1_q9\",\"optionId\":\"\"},{\"questionId\":\"stu1_q10\",\"optionId\":\"stu1q10_o1\",\"value\":\"无\"},{\"questionId\":\"stu1_q11\",\"optionId\":\"stu1q11_o1\",\"value\":\"无\"},{\"questionId\":\"stu1_q12\",\"optionId\":\"stu1q12_o1\",\"value\":\"无\"},{\"questionId\":\"stu1_q20\",\"optionId\":\"stu1q20_o1\",\"value\":\"否\"},{\"questionId\":\"stu1_q13\",\"optionId\":\"\"},{\"questionId\":\"stu1_q14\",\"optionId\":\"\"},{\"questionId\":\"stu1_q15\",\"optionId\":\"\"},{\"questionId\":\"stu1_q16\",\"optionId\":\"\"},{\"questionId\":\"stu1_q17\",\"optionId\":\"\"},{\"questionId\":\"stu1_q18\",\"optionId\":\"\"},{\"questionId\":\"stu1_q19\",\"optionId\":\"\"}]}";
        HttpPost httpPost = MaysRequest.getPostJson(SIGN_IN_URL,data);
            httpPost.addHeader("User-Agent", USER_AGENT);
            httpPost.addHeader("Content-Type","application/json;charset=UTF-8");
            httpPost.addHeader("Authorization",token);
        String lastResult = MaysRequest.getResultFromRequest(httpPost);
        System.out.println("lastResult = " + lastResult);
    }


    @Override
    public String getToken(KmSinglyVO vo) throws Exception {
        HttpGet tokenGet = MaysRequest.getDataGet(TOKEN_URL,MaysDataParse.getMapFromObject(vo));
        String result = MaysRequest.getResultFromRequest(tokenGet);
        return "Bearer " + MaysDataParse.getColData(result,"data.accessToken.tokenContent");
    }

    @Override
    public String getCookie() {
        return null;
    }

    /**
     * 通过 Cookie 获取单点登录信息
     */
    @Override
    public KmSinglyVO getSingleMsg() throws Exception {
        KmSinglyVO vo = new KmSinglyVO();
        HttpGet httpGet = MaysRequest.getNoDataGet(SINGLE_URL);
            httpGet.setHeader("User-Agent", USER_AGENT);
            httpGet.setHeader("Cookie", COOKIE);
        // 截取重定向的数据
        String uri = MaysRequest.getRedirectLocation(httpGet);
        System.out.println("uri = " + uri);
        if (!uri.equals("none")){
            Map<Object, Object> map = MaysDataParse.getQueryParam(uri);
            if (map.get("sign") == null) throw new MushroomException(-1,"Cookie 已过期");
            return new KmSinglyVO(map);
        } throw new MushroomException(-1, "重定向失败");
    }
}
