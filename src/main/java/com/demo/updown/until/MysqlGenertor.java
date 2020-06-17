package com.demo.updown.until;



import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class MysqlGenertor {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        // TODO 设置用户名
        gc.setAuthor("lkz");
        gc.setOpen(true);
        // service 命名方式
        gc.setServiceName("%sService");
        // service impl 命名方式
        gc.setServiceImplName("%sServiceImpl");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");//%省略以I开头
        gc.setXmlName("%sMapper");
        gc.setControllerName("%sController");
        gc.setFileOverride(true);//是否覆盖原来生成
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        gc.setSwagger2(true);//swagger注解
        mpg.setGlobalConfig(gc);


        // TODO 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // oracle数据库  数据库没有数据，实体类是没有属性的
        /*dsc.setUrl("jdbc:oracle:thin:@10.10.10.199:1521:orcl");
        // dsc.setSchemaName("public");
        dsc.setDbType(DbType.ORACLE);
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername("scott");
        dsc.setPassword("oracle");*/
        //mysql 数据库
        dsc.setUrl("jdbc:mysql://10.10.10.61:3306/flowable?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("jf123456");
        mpg.setDataSource(dsc);

        // TODO 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("com.jianfan");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        pc.setServiceImpl("serviceImpl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);



        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//·驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);//自动lombok
        // 设置逻辑删除键 数据库必须有这个字段
        strategy.setLogicDeleteFieldName("deleted");
        // TODO 指定生成的bean的数据库表名
        strategy.setInclude("act_ru_task");//可以指定多个表名
        //strategy.setSuperEntityColumns("id");
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        //****如下是配置 这个要和数据库字段相匹配
        //TableFill createField = new TableFill("gmt_create", FieldFill.INSERT);
        //TableFill modifiedField = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        //tableFillList.add(createField);
        //tableFillList.add(modifiedField);
       // strategy.setTableFillList(tableFillList);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
