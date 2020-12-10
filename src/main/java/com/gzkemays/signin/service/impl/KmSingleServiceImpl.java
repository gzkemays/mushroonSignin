package com.gzkemays.signin.service.impl;

import com.gzkemays.signin.po.KmSingle;
import com.gzkemays.signin.mapper.KmSingleMapper;
import com.gzkemays.signin.po.vo.KmSinglyVO;
import com.gzkemays.signin.service.KmSingleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gzkemays
 * @since 2020-10-11
 */
@Service
public class KmSingleServiceImpl extends ServiceImpl<KmSingleMapper, KmSingle> implements KmSingleService {

    @Override
    public KmSingle getSingle() {
        return this.baseMapper.selectById(1L);
    }

    @Override
    public void updateSingle(KmSinglyVO vo) {
        KmSingle kmSingle = new KmSingle();
        BeanUtils.copyProperties(vo,kmSingle);
        if (this.getSingle() == null) {
            this.baseMapper.insert(kmSingle);
        } else {
            kmSingle.setId(this.getSingle().getId());
            this.baseMapper.updateById(kmSingle);
        }
    }
}
