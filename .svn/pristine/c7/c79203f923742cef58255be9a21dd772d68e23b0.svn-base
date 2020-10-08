package com.gzkemays.mushroom_sign.code.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    public static final String PARENT_PATH = System.getProperty("user.dir")+"/src/main/java";
    public static final String AUTHOR = "gzkemays";
    public static final String DATABASE = "mushroom";
    public static final String DATA_PORT = "4096";
    public static final String[] DATA_COLUMN = new String[]{"mu_user"};
    public static final String DATASOURCE_URL = "jdbc:mysql://127.0.0.1:"+DATA_PORT+"/"+DATABASE+"?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
    public static final String DATASOURCE_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DATASOURCE_USER = "root";
    public static final String DATASOURCE_PWD = "gzkemays";
    public static final String PROJECT_PARENT = "com.gzkemays.mushroom_sign";

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        // 指定生成目录
        gc.setOutputDir(PARENT_PATH);

        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setFileOverride(false);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setServiceName("%sService");
        // 全局配置
        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DATASOURCE_URL);
        dsc.setUsername(DATASOURCE_USER);
        dsc.setPassword(DATASOURCE_PWD);
        dsc.setDriverName(DATASOURCE_DRIVER);
        dsc.setDbType(DbType.MYSQL);
        // 数据源配置
        mpg.setDataSource(dsc);

        PackageConfig pc = new PackageConfig();
        pc.setParent(PROJECT_PARENT);
        pc.setEntity("po");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        // 配置包的生成规则
        mpg.setPackageInfo(pc);

        StrategyConfig sc = new StrategyConfig();
        // 包含的表名
        sc.setInclude(DATA_COLUMN);
        // 下划线转换成驼峰
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        // 包的命名规则
        sc.setNaming(NamingStrategy.underline_to_camel);
        // 启用 Lombok
        sc.setEntityLombokModel(true);
        List<TableFill> tableFills = new ArrayList<>();
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time",FieldFill.INSERT_UPDATE);
        tableFills.add(createTime);
        tableFills.add(updateTime);
        sc.setTableFillList(tableFills);
        // 生成策略
        mpg.setStrategy(sc);

        mpg.execute();
    }
}
