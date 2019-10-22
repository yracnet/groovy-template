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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author yracnet
 */
@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.NONE)
public class GenFile {

    @XmlAttribute(name = "layer")
    private String layer;
    @XmlAttribute(name = "dir")
    private String dir;
    @XmlAttribute(name = "pkg")
    private String pkg;
    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "type", required = true)
    private String type;
    @XmlAttribute(name = "skip")
    private boolean skip;
    @XmlAttribute(name = "append")
    private boolean append;
    @XmlAttribute(name = "comment")
    private String comment;
    @XmlValue
    private String content;

    public String getLayer() {
        return layer == null ? "view" : layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getDir() {
        return dir == null ? "." : dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getPkgAsDir() {
        return pkg == null ? "error" : pkg.replace("-", "/").replace(".", "/");
    }

    public String getPkg() {
        return pkg == null ? "error" : pkg.replace("-", ".").replace("/", ".");
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

//    public String getRealPkg() {
//        return pkg == null ? "." : pkg.replace(".", "/").replace("-", "/");
//    }
//
//    public String getRealDir() {
//        return dir == null ? "." : dir;
//    }
//
//    public String getRealName() {
//        return name == null ? "NaN" : name.contains(".") ? name : (name + ".err");
//    }
    public File getRealPath(File output, String project) {
        String mask = "/$project/error/$name";
        layer = layer == null ? "error" : layer;
        switch (layer) {
            case "view":
                mask = "/$project/$project-view/src/main/webapp/view/$name";
                break;
            case "view:part":
                mask = "/$project/$project-view/src/main/webapp/part/$dir/$name";
                break;
            case "view:ctrl":
                mask = "/$project/$project-view/src/main/webapp/ctrl/$name";
                break;
            case "view:comp":
                mask = "/$project/$project-view/src/main/webapp/comp/$name";
                break;
            case "view:serv":
                mask = "/$project/$project-view/src/main/webapp/serv/$name";
                break;
            case "view:rest":
                mask = "/$project/$project-view/src/main/java/$dir/$pkg/$name";
                break;
            case "test":
                mask = "/$project/$project-view/src/test/java/$dir/$pkg/$name";
                break;
            case "serv":
                mask = "/$project/$project-serv/src/main/java/$dir/$pkg/$name";
                break;
            case "serv:data":
                mask = "/$project/$project-serv/src/main/java/$dir/$pkg/$name";
                break;
            case "serv:filter":
                mask = "/$project/$project-serv/src/main/java/$dir/$pkg/$name";
                break;
            case "impl":
                mask = "/$project/$project-impl/src/main/java/$dir/$pkg/$name";
                break;
            case "impl:mapper":
                mask = "/$project/$project-impl/src/main/java/$dir/$pkg/$name";
                break;
            case "local":
                mask = "/$project/$project-local/src/main/java/$dir/$pkg/$name";
                break;
            case "local:validate":
                mask = "/$project/$project-local/src/main/java/$dir/$pkg/$name";
                break;
            case "model:entity":
                mask = "/$project-model/src/main/java/$dir/$pkg/$name";
                break;
            //default:
            //    mask = "/$project/error/$name";
                //} else if ("conf".equals(layer)) {
                //    mask = "/$project/$project-view/src/main/webapp/WEB-INF/$name";
                //} else if ("web-inf".equals(layer)) {
                //    mask = "/$project/$project-view/src/main/webapp/WEB-INF/$dir/$name";
                //} else if ("meta-inf".equals(layer)) {
                //    mask = "/$project/$project-impl/src/main/resources/META-INF/$dir/$name";
        }

        mask = mask.replace("$project", project);
        mask = mask.replace("$dir", getDir());
        mask = mask.replace("$pkg", getPkgAsDir());
        mask = mask.replace("$name", getName());
        mask = mask.replace("//", "/");
        mask = mask.replace("/./", "/");
        return new File(output, mask);
    }

    public String getName() {
        return name == null ? "NaN" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type == null ? "java" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content == null ? "<<NONE>>" : content.trim();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFileOutput(File output, String artifactId, String module) {
        String project = artifactId + "-" + module;
        output = getRealPath(output, project);
        return output;
    }

    public String getGenerateContent() {
        String generateContent = getContent();
        if ("java".equals(type) || name.endsWith(".java")) {
            generateContent = "package " + getPkg() + ";\n" + generateContent;
        }
        return generateContent;
    }

}
