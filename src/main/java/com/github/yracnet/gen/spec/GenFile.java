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
        if (layer == null) {
            mask = "/$project/error/$name";
        } else if ("ctrl".endsWith(layer)) {
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
    
    public String getContent() {
        return content == null ? "<<NONE>>" : content;
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
