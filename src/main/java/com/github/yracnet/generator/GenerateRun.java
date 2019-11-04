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
        String model = "/work/dev/bcb-03/mrp/mrp-modelo/src/main/resources/modelo.jpa";
        String output = "/work/dev/bcb-03/generate";
        Generate gen = new Generate(model, output);
        gen.setGroupId("bo.gob.bcb.grh.mrp");
        gen.setArtifactId("mrp");
        gen.setModule("portal");
        gen.setNamespace("psv");
        gen.setContextPath("sirepo-portal-rest-0.1.0-01");
        gen.setBasePkg("bo.gob.bcb.grh.mrp");
        gen.addTemplateDirectory("app.javaee");
        //gen.addTemplateDirectory("app.javaee");
        //gen.addTemplateDirectory("app.react");
        //gen.addTemplateDirectory("app.react.module");
        //gen.addTemplateDirectory("app.react.module.page");
        //gen.addTemplateDirectory("app.react.module.part");
        gen.removePrefix("Mrp");
        gen.generate();
    }
}
