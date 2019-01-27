/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen.spec;

import java.io.File;
import java.util.function.Consumer;
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
@XmlAccessorType(XmlAccessType.FIELD)
public class GenFile extends GenBase {

    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "type", required = true)
    private String type;
    @XmlValue
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFileOutput(File output, String artifactId, String module) {
        String project = artifactId + "-" + module;
        output = new File(output, project);
        output = getRealPath(output, project, name);
        return output;
    }

    public String getGenerateContent() {
        String generateContent = content;
        if ("java".equals(type)) {
            generateContent = "package " + getPkg() + ";\n" + content;
        }
        return generateContent;
    }

}
