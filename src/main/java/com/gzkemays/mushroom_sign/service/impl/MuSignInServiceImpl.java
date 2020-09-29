package com.gzkemays.mushroom_sign.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzkemays.mushroom_sign.mapper.MuUserMapper;
import com.gzkemays.mushroom_sign.mapper.MuVipMapper;
import com.gzkemays.mushroom_sign.po.MuUser;
import com.gzkemays.mushroom_sign.po.MuVip;
import com.gzkemays.mushroom_sign.po.dto.MuVipDTO;
import com.gzkemays.mushroom_sign.po.vo.MuLoginVO;
import com.gzkemays.mushroom_sign.po.vo.MuSignInVO;
import com.gzkemays.mushroom_sign.service.HttpGetService;
import com.gzkemays.mushroom_sign.service.MuSignInService;
import com.gzkemays.mushroom_sign.service.MuUserService;
import com.gzkemays.mushroom_sign.service.MuVipService;
import com.gzkemays.mushroom_sign.utils.MuWeekDisposeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class MuSignInServiceImpl implements MuSignInService {
    @Autowired
    private MuUserMapper muUserMapper;
    @Autowired
    private MuVipMapper muVipMapper;
    @Autowired
    private MuUserService muUserService;
    @Autowired
    private MuVipService muVipService;
    @Autowired
    private HttpGetService httpGetService;

    /**
     * 重复执行，根据 state 判断上下班状态。
     * @author gzkemays
     * @param state
     * @throws Exception
     */
    @Override
    public void reDuty(String state) throws Exception {
        // 根据状态获取对象列表
        QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
            wrapper.eq("type",state);
        List<MuUser> userList = muUserMapper.selectList(wrapper);
        // 对列表中的用户逐个打卡
        for (MuUser muUser : userList) {
            MushroomSignIn(muUser);
        }
    }

    /**
     * 查询获取 VIP 列表，并执行发布。
     * @author gzkemays
     */
    @Override
    public void weekArticle() throws Exception {
        List<MuVip> vipList = muVipMapper.selectList(null);
        // 初始化状态（测试版请勿打开）
        muVipService.initVipState();
        for (MuVip muVip : vipList) {
            MushroomWeekArticle(muUserMapper.selectById(muVip.getUserId()));
        }
    }


    /**
     * 签到请求入口
     * @author gzkemays
     * @param muUser
     * @throws Exception
     */
    private void MushroomSignIn (MuUser muUser) throws Exception {
        MuLoginVO vo = new MuLoginVO();
            vo.setPhone(muUser.getPhone());
            vo.setPassword(muUser.getPassword());
        // 登陆获取 token
        String token = httpGetService.getAuthorization(vo);

        if (token != null) {
            // 获取 planId
            String planId = httpGetService.getPlanId(token);
            // planId 插入数据中保存
            muUserService.updatePlan(muUser.getPhone(),planId);
            // 根据 phone 查询信息，并根据当前打卡状态查询（若当前为上班需要：“START”，那么就拥有 START 状态，如果不符合则不打卡且更新）
            QueryWrapper<MuUser> wrapper = new QueryWrapper<>();
                wrapper .select("country","address","province","city","latitude","description","planId","type","device","longitude")
                        .eq("phone",muUser.getPhone());
            MuSignInVO muSignInVO = new MuSignInVO();
            MuUser user = muUserService.getBaseMapper().selectOne(wrapper);
            // 避免后来者重复打卡。
            if (user != null) {
                BeanUtils.copyProperties(user,muSignInVO);
                // 登陆打卡
                httpGetService.getSignIn(muSignInVO,token);
                // 更新打卡状态
                muUserService.updateState(muUser.getPhone());
            }
        }

    }

    /**
     * 周报发送入口
     * @param muUser
     * @throws Exception
     */
    private void MushroomWeekArticle (MuUser muUser) throws Exception {
        MuLoginVO vo = new MuLoginVO();
            vo.setPhone(muUser.getPhone());
            vo.setPassword(muUser.getPassword());
        // 登陆获取 token
        String token = httpGetService.getAuthorization(vo);
        // 获取 VIP 用户
        MuVip muVip = muVipService.getMsgByUserId(muUser.getId());
        if (muVip != null) {
            MuVipDTO dto = MuWeekDisposeUtils.getVipAllMsg(muVip,muUser,httpGetService.getWeekMessage(muUser,token));
            // 发布周报，
            if (!muVip.getIsUsed()) {
                // 叠加周次并更新当前周报时间
                muVipService.updateVipWeekTime(dto);
                // 发布
                httpGetService.getWeekReport(dto,token);
                // 如果 is_used 为 0 则发布，并将状态更新为 1。
                muVipService.updateVipState(dto);
            }

        }
    }

}
