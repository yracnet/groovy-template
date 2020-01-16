/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.generator;

/**
 *
 * @author yracnet
 */
public class GenerateRun {

    public static void main(String[] args) {
        String model = "/work/dev/bcb-01/R05/param/param-model/src/main/script/model.jpa";
        String output = "/work/dev/bcb-01/R05/param-gen";
        Generate gen = new Generate(model, output);
        gen.setGroupId("bo.gob.bcb.param");
        gen.setArtifactId("param");
        gen.setModule("manager");
        gen.setNamespace("par");
        gen.setContextPath("/param/manager");
        gen.setBasePkg("bo.gob.bcb.param");
        gen.addTemplateDirectory("app.javaee");
        //gen.addTemplateDirectory("app.react.x1");
        //gen.addTemplateDirectory("app.react.module");
        //gen.addTemplateDirectory("app.react.module.page");
        //gen.addTemplateDirectory("app.react.module.part");
        gen.removePrefix("Par");
        gen.generate();
    }
}
