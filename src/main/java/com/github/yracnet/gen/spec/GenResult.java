/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author yracnet
 */
public class GenResult {

    private final List<GenRoot> genFileRoot = new ArrayList<>();

    public void genFileRootEach(Consumer<GenRoot> it) {
        genFileRoot.forEach(it);
    }

    public void genFileEach(Consumer<GenFile> it) {
        genFileRoot.forEach(x -> x.genFileEach(it));
    }

    public void addGenFileRoot(GenRoot item) {
        genFileRoot.add(item);
    }
}
