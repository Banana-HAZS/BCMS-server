package com.banana;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Description： mybatis-plus代码生成器
 * Author: ZhaiJianYu
 * Date:  2024/02/28 11:49
 * Version: 1.0.0
 * Modified By:
 */
public class IBankCodeGenerator {
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(".\\src\\main\\java\\");  //生成路径(一般都是生成在此项目的src/main/java下面)
        gc.setAuthor("ZhaiJianYu"); //设置作者
        gc.setOpen(false);
        gc.setFileOverride(true); //第二次生成会把第一次生成的覆盖掉
        autoGenerator.setGlobalConfig(gc);

        //2、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/bcms");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("banana666");
        autoGenerator.setDataSource(dsc);

        // 3、包配置（包名配置）
        String moduleName = "info";
        String mapperLocation = ".\\src\\main\\resources\\mapper\\" + moduleName;
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.banana");
        pc.setModuleName(moduleName);
//        设置xml文件路径，目前OutputFile包不兼容
//        pc.setPathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation));
        autoGenerator.setPackageInfo(pc);

        // 4、配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 注释掉的是要生成的
//        templateConfig.setController(null);
//        templateConfig.setService(null);
//        templateConfig.setEntity(null);
//        templateConfig.setServiceImpl(null);
//        templateConfig.setMapper(null);
//        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 表名前缀
        // strategy.setTablePrefix("t_");
        //使用lombok
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 控制要生成的表，如果要生成多个,这里可以传入String[]
        String[] tableNames = {"delay_records"};
        strategy.setInclude(tableNames);
        autoGenerator.setStrategy(strategy);

        //5、执行
        autoGenerator.execute();

    }
}
