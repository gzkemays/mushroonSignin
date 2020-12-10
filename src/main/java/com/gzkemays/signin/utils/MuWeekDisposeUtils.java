package com.gzkemays.signin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzkemays.signin.po.MuUser;
import com.gzkemays.signin.po.MuVip;
import com.gzkemays.signin.po.dto.MuVipDTO;
import com.gzkemays.signin.po.vo.MuDisposeVO;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MuWeekDisposeUtils {
    interface CopyTimeOperation {
        void copyTime(Map<String, String> map);
    }

    public static Map<String, String> getDisposeTime (String date) {
        JSONObject week = (JSONObject) JSON.parseArray(date).toArray()[0];
        String start = week.get("startTime").toString();
        String end = week.get("endTime").toString();
        Map<String, String> map = new HashMap<>();
            map.put("startTime", start);
            map.put("endTime", end);
        return map;
    }
    public static MuVipDTO getVipAllMsg (MuVip muVip, MuUser muUser, Map<String, String> map) {
        MuVipDTO muVipDTO = new MuVipDTO();
        muVipDTO.setMuUser(muUser);
        BeanUtils.copyProperties(muVip, muVipDTO);
        ((CopyTimeOperation) time -> {
            muVipDTO.setStartTime(time.get("startTime"));
            muVipDTO.setEndTime(time.get("endTime"));
        }).copyTime(map);
        return muVipDTO;
    }
    public static MuDisposeVO getDisposeMsg (MuVipDTO dto) {
        MuDisposeVO vo = new MuDisposeVO();
        Integer weekNum = getWeekNumber(dto.getWeeks());
        String title = "第" + weekNum + "周周报";
        String content = MuArticleUtils.muArticleGenerator("上班",120);
            vo.setTitle(title);
            vo.setPlanId(dto.getMuUser().getPlanId());
            vo.setContent(content);
        BeanUtils.copyProperties(dto,vo);
        return vo;
    }
    public static Integer getWeekNumber (String hasWeekText) {
        String reg = "[^0-9]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(hasWeekText);
        return Integer.parseInt(matcher.replaceAll("").trim());
    }
}
