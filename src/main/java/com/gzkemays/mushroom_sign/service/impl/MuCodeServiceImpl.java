package com.gzkemays.mushroom_sign.service.impl;

import com.gzkemays.mushroom_sign.po.MuCode;
import com.gzkemays.mushroom_sign.mapper.MuCodeMapper;
import com.gzkemays.mushroom_sign.service.MuCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gzkemays
 * @since 2020-09-29
 */
@Service
public class MuCodeServiceImpl extends ServiceImpl<MuCodeMapper, MuCode> implements MuCodeService {

    @Override
    public void insertOrUpdateCode() {

    }
}
