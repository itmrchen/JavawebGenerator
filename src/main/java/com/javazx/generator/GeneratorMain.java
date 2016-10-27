package com.javazx.generator;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;

/**
 * @author itmrchen
 * @date 2016/10/27
 * @time 13:19
 */
public class GeneratorMain {
    /**
     * 请直接修改以下代码调用不同的方法以执行相关生成任务.
     * <p>
     * 该生成器用于连接MySQL，生成办公用品采购后台的代码和页面。
     */
    public static void main(String[] args) throws Exception {
        //设置模板目录
        String templatePath = "template";
        GeneratorFacade g = new GeneratorFacade();
        //删除生成器的输出目录
        g.deleteOutRootDir();
        //设置模板位置目录
        g.getGenerator().addTemplateRootDir(templatePath);
        //设置要生成的表名
        g.generateByTable("content");
        //g.generateByAllTable();
        //打开文件夹
        Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
    }
}
