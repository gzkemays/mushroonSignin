package com.gzkemays.signin.service;

import com.gzkemays.signin.po.KmSingle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gzkemays.signin.po.vo.KmSinglyVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gzkemays
 * @since 2020-10-11
 */
public interface KmSingleService extends IService<KmSingle> {
    KmSingle getSingle();
    void updateSingle(KmSinglyVO vo);
}
