package com.gzkemays.signin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.DATA_CONVERSION;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MaysRequest {
    private static CookieStore store;
    private static RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();

    /**
     * 基础设置
     * @return
     */
    public static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(20000)
                .setConnectTimeout(20000)
                .setConnectionRequestTimeout(20000)
                .build();
    }
    /**
     * Get 无参请求
     * @param url
     * @return
     */
    public static HttpGet getNoDataGet (String url) throws UnsupportedEncodingException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig());
        return httpGet;
    }

    /**
     * Get 有参请求
     * @param map
     * @param url
     * @return
     */
    public static HttpGet getDataGet (String url, Map<Object, Object> map) {
        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> list = getPairListByMap(map);
        if (list.size() > 0) {
            String param = URLEncodedUtils.format(list, "UTF-8");
            httpGet.setURI( URI.create(url + "?" + param) );
        }
        httpGet.setConfig(getRequestConfig());
        return httpGet;
    }


    /**
     * 无参 Post 请求
     * @param url
     * @return
     */
    public static HttpPost getHttpPost (String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        return httpPost;
    }

    /**
     * key value 的表单提交
     * @param url
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    public static HttpPost getUrlEncodedFormPost (String url, Map<Object, Object> map) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        List<NameValuePair> list = getPairListByMap(map);
        if (list.size() > 0){
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(urlEncodedFormEntity);
        }
        return httpPost;
    }

    /**
     * 正常 String 的 Entity 转换
     * @param url
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static HttpPost getStringPost (String url, String data) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(data);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        httpPost.setEntity(entity);
        return httpPost;
    }

    /**
     * Post 获取 Cookie
     */
    public static String getDataCookieByPost(String url, Map<Object, Object> map) throws Exception {
        HttpPost httpPost = getUrlEncodedFormPost(url, map);
        store = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        httpClient.execute(httpPost);
        return getCookie(store);
    }

    /**
     * 无参 Get 获取 Cookie
     */
    public static String getCookieByGet (String url) throws IOException {
        HttpGet httpGet = getNoDataGet(url);
        store = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        httpClient.execute(httpGet);
        return getCookie(store);
    }

    /**
     * 有参 Get 获取 Cookie
     */
    public static String getDataCookieByGet (String url, Map<Object, Object> map) throws IOException {
        HttpGet httpGet = getDataGet(url, map);
        store = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        httpClient.execute(httpGet);
        return getCookie(store);
    }

    /**
     * JSON POST 请求
     */
    public static HttpPost getPostJson(String url, String param) {
        StringEntity entity = new StringEntity(param,"utf-8");
            entity.setContentType("application/json");
            entity.setContentEncoding("utf-8");
        HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(getRequestConfig());
            httpPost.setEntity(entity);
        return httpPost;
    }

    /**
     * 通过 map 获取 NameValuePair
     * @param map
     * @return
     */
    public static List<NameValuePair> getPairListByMap (Map<Object, Object> map) {
        Iterator<Map.Entry<Object, Object>> entries = map.entrySet().iterator();
        List<NameValuePair> list = new ArrayList<>();
        while(entries.hasNext()) {
            Map.Entry<Object, Object> entry = entries.next();
            NameValuePair pai = new BasicNameValuePair(entry.getKey().toString(),entry.getValue().toString());
            list.add(pai);
        }
        return list;
    }

    /**
     * 获取拼接的 Cookie
     */
    public static String getCookie (CookieStore store) {
        List<Cookie> cookies = store.getCookies();
        StringBuilder tempcookies = new StringBuilder();
        for(Cookie c : cookies){
            tempcookies.append(c.getName() + "=" + c.getValue()).append(";");
        }
        return tempcookies.toString();
    }

    /**
     * 传入请求获取 String 返回结果
     */
    public static String getResultFromRequest (HttpPost httpPost) throws Exception {
        CloseableHttpResponse response;
        CloseableHttpClient httpClient = getIgnoeSSLClient();
        response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(),"UTF-8");
    }
    /**
     * 传入请求获取 String 返回结果
     */
    public static String getResultFromRequest (HttpGet httpGet) throws Exception {
        CloseableHttpResponse response;
        CloseableHttpClient httpClient = getIgnoeSSLClient();
        response = httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity(),"UTF-8");
    }

    /**
     * 获取重定向地址 Get
     */
    public static String getRedirectLocation(HttpGet httpGet) throws Exception {
        httpGet.setConfig(config);
        CloseableHttpClient httpClient = MaysRequest.getIgnoeSSLClient();
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int code = response.getStatusLine().getStatusCode();
        System.out.println("code = " + code);
        String localURI = "none";
        if (code == 302 || code == 301){
            Header header = response.getFirstHeader("location");
            localURI = header.getValue();
        }
        return localURI;
    }
    /**
     * 获取重定向地址 Post
     */
    public static String getRedirectLocation(HttpPost httpPost) throws Exception {
        httpPost.setConfig(config);
        CloseableHttpClient httpClient = MaysRequest.getIgnoeSSLClient();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int code = response.getStatusLine().getStatusCode();
        String localURI = "none";
        if (code == 302){
            Header header = response.getFirstHeader("location");
            localURI = header.getValue();
        }
        return localURI;
    }


    /**
     * 忽略 SSL 证书
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient getIgnoeSSLClient() throws Exception {
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        }).build();
        //创建httpClient
        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).
                setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        return client;
    }

}

