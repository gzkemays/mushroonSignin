package com.gzkemays.signin.po.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class KmSinglyVO {
    private String type = "0";
    private String code;
    private String sign;
    private String timestamp;

    public KmSinglyVO(Map<Object, Object> map) {
        this.code = (String) map.get("code");
        this.sign = (String) map.get("sign");
        this.timestamp = (String) map.get("timestamp");
    }

    public KmSinglyVO(String code, String sign, String timestamp) {
        this.code = code;
        this.sign = sign;
        this.timestamp = timestamp;
    }
}
