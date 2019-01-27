/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen.spec;

import java.io.File;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author yracnet
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class GenBase {

    @XmlAttribute(name = "layer")
    private String layer;
    @XmlAttribute(name = "dir")
    private String dir;
    @XmlAttribute(name = "pkg")
    private String pkg;

    public GenBase() {
    }

    public GenBase(GenBase other) {
        init(other);
    }

    public void init(GenBase other) {
        this.layer = other.layer;
        this.dir = other.dir;
        this.pkg = other.pkg;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getRealPkg() {
        return pkg == null ? "." : pkg.replace(".", "/");
    }

    public String getRealDir() {
        return dir == null ? "." : dir;
    }

    public File getRealPath(File output, String project, String name) {
        String mask = "/$project/error/$name";
        if ("ctrl".endsWith(layer)) {
            mask = "/$project/$project-view/src/main/webapp/ctrl/$name";
        } else if ("view".endsWith(layer)) {
            mask = "/$project/$project-view/src/main/webapp/view/$name";
        } else if ("part".endsWith(layer)) {
            mask = "/$project/$project-view/src/main/webapp/part/$dir/$name";
            //} else if ("conf".endsWith(layer)) {
            //    mask = "/$project/$project-view/src/main/webapp/WEB-INF/$name";
            //} else if ("web-inf".endsWith(layer)) {
            //    mask = "/$project/$project-view/src/main/webapp/WEB-INF/$dir/$name";
            //} else if ("meta-inf".endsWith(layer)) {
            //    mask = "/$project/$project-impl/src/main/resources/META-INF/$dir/$name";
        } else if ("test".endsWith(layer)) {
            mask = "/$project/$project-view/src/test/java/$dir/$pkg/$name";
        } else if ("rest".endsWith(layer)) {
            mask = "/$project/$project-view/src/main/java/$dir/$pkg/$name";
        } else if ("serv".endsWith(layer)) {
            mask = "/$project/$project-serv/src/main/java/$dir/$pkg/$name";
        } else if ("data".endsWith(layer)) {
            mask = "/$project/$project-serv/src/main/java/$dir/$pkg/$name";
        } else if ("filter".endsWith(layer)) {
            mask = "/$project/$project-serv/src/main/java/$dir/$pkg/$name";
        } else if ("impl".endsWith(layer)) {
            mask = "/$project/$project-impl/src/main/java/$dir/$pkg/$name";
        } else if ("entity".endsWith(layer)) {
            mask = "/$project/$project-impl/src/main/java/$dir/$pkg/$name";
        }

        mask = mask.replace("$project", project);
        mask = mask.replace("$dir", getRealDir());
        mask = mask.replace("$pkg", getRealPkg());
        mask = mask.replace("$name", name);
        mask = mask.replace("//", "/");
        mask = mask.replace("/./", "/");
        return new File(output, mask);
    }

}
