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
        String model = "/work/dev/bcb-01/r1/temporal/modelo-v3.jpa";
        String output = "/work/dev/bcb-01/r1/temporal2";
        Generate gen = new Generate(model, output);
        gen.setGroupId("bo.gob.bcb.grh");
        gen.setArtifactId("postulate");
        gen.setModule("sitio");
        gen.setNamespace("psv");
        gen.setContextPath("postulate-sitio");
        gen.addTemplate("simple.crud2.serv");
        gen.addTemplate("simple.crud2.impl");
        gen.addTemplate("simple.crud2.local");
        gen.addTemplate("simple.crud2.view");
        gen.removePrefix("Form");
        gen.generate();
    }
}
