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

    public static File getBasedir() {
        String path = Util.class.getResource("/template/layer").getPath();
        return new File(path);
    }
    
}
