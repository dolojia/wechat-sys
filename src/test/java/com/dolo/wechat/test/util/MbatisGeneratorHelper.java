package com.dolo.wechat.test.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * 描述: MbatisGeneratorHelper自动代码生成
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:56
 * E-mail: dolojia@gmail.com
 **/
public class MbatisGeneratorHelper {
    public static void main(String[] args) {
        System.out.println("======================start======================");
        Properties props = System.getProperties(); // 系统属性
        // 本机
//        String author = props.getProperty("user.name");
        String author = "dolojia";
        // 自定义包路径
        String parentPackageName = "com.dolo.wechat";
        String entityChildPackageName = "entity";
        String servicePackageName = "";
        String mapperPackageName = "";
        // 设置需要生成的表名
        String[] tableNames = new String[]{"annual_rate","admin_user","admin_role","admin_menu","access_token"};
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new OracleTypeConvert());
        dsc.setDriverName("com.mysql.jdbc.Driver");

        // 数据库start
        dsc.setUrl("jdbc:mysql://xxxxx/wechat-db?useSSL=false");
        dsc.setUsername("xxxx");
        dsc.setPassword("xxxx");
        mapperPackageName = "mapper";
        servicePackageName = "service";
        // 数据库end

        // 代码生成
        MbatisGeneratorHelper.generator(dsc, author, parentPackageName, servicePackageName, entityChildPackageName,
                mapperPackageName, tableNames);
    }

    /**
     * @title： mybatis_plus代码生成器
     * @author： lin xuan
     * @date：2018/3/23
     */
    public static void generator(DataSourceConfig dsc, String author, String parentPackageName,
                                 String servicePackageName, String entityChildPackageName, String mapperPackageName, String[] tableNames) {
        AutoGenerator mpg = new AutoGenerator();
        // 项目绝对路径
        String PROJECT_DIR = System.getProperty("user.dir");
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(PROJECT_DIR + "/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 开启 activeRecord 模式
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(author);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        // strategy.setTablePrefix(new String[] { "bmd_", "mp_" });
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
        // 字段名生成策略 生成全部表的话把这段禁用
        strategy.setInclude(tableNames);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // 自定义包路径
        pc.setParent(parentPackageName);
        // 这里是控制器包名，默认 web
        pc.setController(null);
        pc.setEntity(entityChildPackageName);
        pc.setMapper(mapperPackageName);
        pc.setService(servicePackageName);
        pc.setServiceImpl(servicePackageName + ".impl");

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String mapperPath = PROJECT_DIR + "/src/main/resources/mapper/" + tableInfo.getEntityName()
                        + "Mapper.xml";
                return mapperPath;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
        System.out.println("======================end======================");
    }
}