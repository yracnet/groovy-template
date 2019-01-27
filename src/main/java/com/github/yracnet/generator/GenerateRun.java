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
        String model = "/work/github/yracnet/app-fn/app-fn-local/src/main/java/dev/yracnet/app/local/model.jpa";
        String output = "/work/github/yracnet/app-fn/";
        Generate gen = new Generate(model, output);
        gen.setGroupId("dev.yracnet");
        gen.setArtifactId("app-fn");
        gen.setModule("manager");
        gen.addTemplate("simple.crud.serv");
        //gen.addTemplate("simple.crud.impl");
        //gen.addTemplate("simple.crud.local");
        //gen.addTemplate("simple.crud.view");
        gen.removePrefix("Cap");
        gen.generate();
    }
}
