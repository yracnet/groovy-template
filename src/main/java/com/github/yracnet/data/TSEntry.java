/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.data;

import com.github.yracnet.gen.spec.GenFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author wyujra
 */
public class TSEntry {

    private String path;
    private String java;
    private String name;
    private String content;
    private final List<String> reference = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addReference(String name) {
        reference.remove(name);
        reference.add(name);
    }

    public List<String> getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return "TSEntry{" + "path=" + path + ", name=" + name + ", content=" + content + ", reference=" + reference + '}';
    }

    public File getFile(File dir) {
        return new File(dir, path + "/" + name + ".tsx");
    }

    public boolean isReference(String className) {
        return reference.contains(className);
    }

    public GenFile asGenFile() {
        GenFile genFile = new GenFile();
        genFile.setContent(content);
        genFile.setName(name);
        genFile.setDir(path);
        return genFile;
    }

}
