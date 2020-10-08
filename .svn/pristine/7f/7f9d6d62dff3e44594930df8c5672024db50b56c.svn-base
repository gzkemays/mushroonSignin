package com.gzkemays.mushroom_sign.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时填充策略
    @Override
    // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
    public void insertFill(MetaObject metaObject) {
        System.out.println("start insert fill");
        this.setFieldValByName("createtime",new Date(),metaObject);
        this.setFieldValByName("lastmodifytime",new Date(),metaObject);
    }
    // 更新时填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("start update fill");
        this.setFieldValByName("lastmodifytime",new Date(),metaObject);
    }
}
