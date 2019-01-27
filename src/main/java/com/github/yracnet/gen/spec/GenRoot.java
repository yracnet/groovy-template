/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen.spec;

import java.util.List;
import java.util.function.Consumer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yracnet
 */
@XmlRootElement(name = "files")
@XmlAccessorType(XmlAccessType.NONE)
public class GenRoot {

    @XmlElement(name = "file")
    private List<GenFile> genFileList;

    public List<GenFile> getGenFileList() {
        return genFileList;
    }

    public void setGenFileList(List<GenFile> genFileList) {
        this.genFileList = genFileList;
    }

    public void genFileEach(Consumer<GenFile> it) {
        genFileList.forEach(x -> {
            //x.init(this);
            it.accept(x);
        });
    }

}
