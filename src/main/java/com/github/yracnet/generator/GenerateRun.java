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
        String model = "/work/dev/bcb-01/r3/2019A-01/sirepo-modelo/src/main/resources/modelo.jpa";
        String output = "/work/dev/bcb-01/r3/generate";
        Generate gen = new Generate(model, output);
        gen.setGroupId("bo.gob.bcb.grh.sirepo");
        gen.setArtifactId("sirepo");
        gen.setModule("portal");
        gen.setNamespace("psv");
        gen.setContextPath("sirepo-portal-rest-0.1.0-01");
        gen.addTemplateDirectory("app.react");
        gen.addTemplateDirectory("app.react.module");
        gen.addTemplateDirectory("app.react.module.page");
        gen.addTemplateDirectory("app.react.module.part");
        gen.removePrefix("Form");
        gen.generate();
    }
}
