package com.gzkemays.mushroom.signin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzkemays.signin.SigninApplication;
import com.gzkemays.signin.mapper.MuUserMapper;
import com.gzkemays.signin.mapper.MuVipMapper;
import com.gzkemays.signin.po.KmSingle;
import com.gzkemays.signin.po.KmUser;
import com.gzkemays.signin.po.vo.KmSinglyVO;
import com.gzkemays.signin.service.*;
import com.gzkemays.signin.utils.MaysDataParse;
import com.gzkemays.signin.utils.MaysRequest;
import com.gzkemays.signin.utils.MuArticleUtils;
import com.gzkemays.signin.utils.MuVipCodeUtils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SigninApplication.class)
class SigninApplicationTests {
    @Autowired
    private MuUserService muUserService;
    @Autowired
    private MuSignInService muSignInService;
    @Autowired
    private MuUserMapper muUserMapper;
    @Autowired
    private MuVipMapper muVipMapper;
    @Autowired
    private MuVipService muVipService;
    @Autowired
    private HttpGetService httpGetService;
    @Autowired
    private MuCodeService muCodeService;
    @Autowired
    private KmSignInService kmSignInService;
    @Autowired
    private KmUserService kmUserService;
    @Autowired
    private KmSingleService kmSingleService;
    @Test
    void contextLoads() throws Exception {
        System.out.println("MuVipCodeUtils.stringMD5() = " + MuVipCodeUtils.adminGetVipCode("gzkemays"));
    }

    /**
     * 打卡
     */
    @Test
    void MushroomSignIn() throws Exception {
        muSignInService.reDuty("START");
    }

    /**
     * 生成激活码
     */
    @Test
    void ManualSetCode() {
        int i = 0;
        while ( i < 5) {
            muCodeService.saveOrUpdateCodeState("gzkemays");
            i++;
        }
    }

    /**
     * 科贸打卡测试
     */
    @Test
    void KmSignInCode() throws Exception {
        /**
         * 通过 Cookie 获取单点登录信息
         */
        String path = "https://aic.gdkmxy.cn/wxaic/student/stuCenter/wxAccInt.aspx?Redirect=/yqtj/#/et?type=0";
        String userAgent = "Mozilla/5.0 (Linux; Android 10; LIO-AL00 Build/HUAWEILIO-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2691 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/2622 MicroMessenger/7.0.18.1740(0x2700123B) Process/toolsmp WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64";
        String cookie = "ASP.NET_SessionId=pkuzthukcyz410brkdk5kz1o; .ASPXFORMSAUTH=C0A0D6564564573BE87CEA76BA4343501B34CF8964F5F8C34F32FBEB12C076708D884E0CBB5B5AB8230EB246B6F6BCEDCE10F0C3E38946A5289A020E2C966E2779AB964472337EE48ED9DCC758CEF27A9897EF957BD6672386195C45303A255012CD7468";
        HttpGet httpGet = MaysRequest.getNoDataGet(path);
            httpGet.setHeader("User-Agent", userAgent);
            httpGet.setHeader("Cookie", cookie);
        String uri = MaysRequest.getRedirectLocation(httpGet);
        System.out.println("uri = " + uri);

        /**
         * 通过单点登录信息获取 Token
         */
        Map<Object, Object> map = new HashMap<>();
        String[] strArr = null;
        strArr = uri.substring(uri.indexOf("&")).replaceFirst("&","").split("&");
        for (String str : strArr) {
            String[] tempArr = null;
            tempArr = str.split("=");
            if (tempArr.length > 1) {
                map.put(tempArr[0],tempArr[1]);
            }
        }
        map.put("type","0");
        /**
         * 发送请求获取 Token
         */
        String tokenURL = "https://aic.gdkmxy.cn/yqtj/api/v1/SSO";
        HttpGet tokenGet = MaysRequest.getDataGet(tokenURL,map);
        String result = MaysRequest.getResultFromRequest(tokenGet);
        String token = "Bearer " + MaysDataParse.getColData(result,"data.accessToken.tokenContent");
        System.out.println("token = " + token);

        /**
         * 发送签到请求
         */
        String data = "{\"type\":\"0\",\"userId\":\"18502002046\",\"resultOptionCreateRequests\":[{\"questionId\":\"stu1_q1\",\"optionId\":\"\",\"value\":\"广东省广州市越秀区先烈中路75号|[23.3535,113.68221]\"},{\"questionId\":\"stu1_q2\",\"optionId\":\"\"},{\"questionId\":\"stu1_q3\",\"optionId\":\"\"},{\"questionId\":\"stu1_q4\",\"optionId\":\"\"},{\"questionId\":\"stu1_q5\",\"optionId\":\"\"},{\"questionId\":\"stu1_q6\",\"optionId\":\"\"},{\"questionId\":\"stu1_q7\",\"optionId\":\"\"},{\"questionId\":\"stu1_q8\",\"optionId\":\"\"},{\"questionId\":\"stu1_q9\",\"optionId\":\"\"},{\"questionId\":\"stu1_q10\",\"optionId\":\"stu1q10_o1\",\"value\":\"无\"},{\"questionId\":\"stu1_q11\",\"optionId\":\"stu1q11_o1\",\"value\":\"无\"},{\"questionId\":\"stu1_q12\",\"optionId\":\"stu1q12_o1\",\"value\":\"无\"},{\"questionId\":\"stu1_q20\",\"optionId\":\"stu1q20_o1\",\"value\":\"否\"},{\"questionId\":\"stu1_q13\",\"optionId\":\"\"},{\"questionId\":\"stu1_q14\",\"optionId\":\"\"},{\"questionId\":\"stu1_q15\",\"optionId\":\"\"},{\"questionId\":\"stu1_q16\",\"optionId\":\"\"},{\"questionId\":\"stu1_q17\",\"optionId\":\"\"},{\"questionId\":\"stu1_q18\",\"optionId\":\"\"},{\"questionId\":\"stu1_q19\",\"optionId\":\"\"}]}";
        String signInURL = "https://aic.gdkmxy.cn/yqtj/api/v1/QuestionnaireResult?isAbnormal=false";
        HttpPost httpPost = MaysRequest.getStringPost(signInURL,data);
            httpPost.addHeader("Authorization",token);
            httpGet.addHeader("User-Agent", userAgent);
        String lastResult = MaysRequest.getResultFromRequest(httpPost);
        System.out.println("lastResult = " + lastResult);


    }

    @Test
    void JsonObjectSpilt() {
        // 测试的 JSON 数据
        String jsonStr = "{\"msg\":\"success\",\"code\":0,\"data\":{\"accessToken\":{\"expires\":\"2020-10-11T00:36:38.621919+08:00\"}}}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        System.out.println("jsonObject = " + jsonObject);
        String spiltStr = "data.accessToken.expires";
        String end = "";
        while (spiltStr.contains(".")) {
            int index = spiltStr.indexOf(".");
            String data = spiltStr.substring(0,index);
            spiltStr = spiltStr.substring(index).replaceFirst(".","");
            jsonObject = (JSONObject) jsonObject.get(data);
        }
        end = (String) jsonObject.get(spiltStr);
    }

    @Test
    void easyTest () throws IllegalAccessException {
        KmSinglyVO vo = new KmSinglyVO();
//        vo.setCode("123");
//        vo.setSign("1234");
//        vo.setTimestamp("12345");
        Map<Object, Object> map = new HashMap();
        map.put("sign","12345");
        map.put("code","123456");
        KmSinglyVO vo2 = (KmSinglyVO) MaysDataParse.getObjectFromMap(vo, map);

        System.out.println("vo2 = " + vo2);
    }

    /**
     * 学校打卡接口测试
     */
    @Test
    void KmSignInTest () throws Exception {
        KmSingle single = kmSingleService.getSingle();
        // 如果数据库没有初始数据则更新。
        if (single == null) {
            KmSinglyVO vo = kmSignInService.getSingleMsg();
            kmSingleService.updateSingle(vo);
        }
        single = kmSingleService.getSingle();
        KmSinglyVO singlyVO = new KmSinglyVO();
        BeanUtils.copyProperties(single,singlyVO);
        String token = kmSignInService.getToken(singlyVO);
        List<KmUser> list = kmUserService.getAllUserMsg();
        for (KmUser user : list) {
            kmSignInService.getKmSignIn(user, token);
        }
    }

    /**
     * 获取 code
     */
    @Test
    void GetWechatCode () throws Exception {
        String url = "https://aic.gdkmxy.cn/wxaic/student/stuCenter/Login.aspx";
        url = URLEncoder.encode(url);
        String codePath = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid=wx0a7312adaaa1df00&" +
                "redirect_uri=https://aic.gdkmxy.cn/wxaic/student/stuCenter/Login.aspx&"+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&m=oauth2" +
                "#wechat_redirect";
        HttpClient client = MaysRequest.getIgnoeSSLClient();
        HttpGet httpGet = MaysRequest.getNoDataGet(codePath);
        String uri = MaysRequest.getRedirectLocation(httpGet);
        HttpGet hg = MaysRequest.getNoDataGet(uri);
        String userAgent = "Mozilla/5.0 (Linux; Android 10; LIO-AL00 Build/HUAWEILIO-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.62 XWEB/2691 MMWEBSDK/200801 Mobile Safari/537.36 MMWEBID/2622 MicroMessenger/7.0.18.1740(0x2700123B) Process/toolsmp WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64";
        hg.setHeader("User-Agent",userAgent);
        String uri2 = MaysRequest.getResultFromRequest(hg);
        System.out.println("uri = " + uri);
        System.out.println("uri2 = " + uri2);
    }

    /**
     * 部分接口测试
     */
    @Test
    void CheckInterface () throws Exception {
        String jsonStr = "{\"type\":\"0\",\"userId\":\"18502002046\"}";
        JSONObject json3 = JSONObject.parseObject(jsonStr);

//        System.out.println("jsonStr = " + jsonStr);
//        String json2 = JSONObject.toJSONString(jsonStr);
//        json2 = json2.replace("\\","");
//        String json2_1 = json2.substring(1,json2.length()-1);
//        System.out.println("json2 = " + json2_1);
        System.out.println("json3 = " + json3);
    }
    /**
     * 上班打卡测试
     */
    @Test
    void GoSign()  throws Exception {
        muSignInService.reDuty("START");
        System.out.println("\"打卡定时器\" = " + "上班打卡");
    }

    /**
     * 下班打卡测试
     */
    @Test
    void EndSign() throws Exception {
        muSignInService.reDuty("END");
        System.out.println("\"打卡定时器\" = " + "下班打卡");
    }

    @Test
    void TestGateWayError () {
        String result = "<html>\n" +
                "<head><title>502 Bad Gateway</title></head>\n" +
                "<body bgcolor=\"white\">\n" +
                "<center><h1>502 Bad Gateway</h1></center>\n" +
                "<hr><center>nginx</center>\n" +
                "</body>\n" +
                "</html>\n";
        if (result.contains("504")|| result.contains("502")) {
            JSONObject[] jsonObjects = {JSONObject.parseObject("{\"msg\":\"timeout\"}")};
            System.out.println(jsonObjects[0].getString("msg"));
        }
    }

    @Test
    void kmSigin() throws Exception {
        KmSingle single = kmSingleService.getSingle();
        // 如果数据库没有初始数据则更新。
        if (single == null) {
            KmSinglyVO vo = kmSignInService.getSingleMsg();
            kmSingleService.updateSingle(vo);
        }
        single = kmSingleService.getSingle();
        KmSinglyVO singlyVO = new KmSinglyVO();
        BeanUtils.copyProperties(single,singlyVO);
        String token = kmSignInService.getToken(singlyVO);
        List<KmUser> list = kmUserService.getAllUserMsg();
        for (KmUser user : list) {
            kmSignInService.getKmSignIn(user, token);
        }
    }

    @Test
    void details() {
        MuArticleUtils.muArticleGenerator("实习",2000);
    }
}
