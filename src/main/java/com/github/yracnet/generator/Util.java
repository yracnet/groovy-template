/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.generator;

import java.io.File;

/**
 *
 * @author yracnet
 */
public class Util {

    public static File getProjectPath() {
        String path = Util.class.getResource("/").getPath();
        System.out.println("--->"+path);
        return new File(path+"/../..");
    }

    public static File getTemplateFile(String name) {
        String path = Util.class.getResource("/template/layer/" + name).getPath();
        return new File(path);
    }

}
