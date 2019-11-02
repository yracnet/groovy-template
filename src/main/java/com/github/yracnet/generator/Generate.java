package com.github.yracnet.generator;

import com.github.yracnet.gen.spec.FactoryImpl;
import com.github.yracnet.gen.spec.GenRoot;
import com.github.yracnet.gen.spec.Util;
import groovy.json.JsonSlurper;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.xml.bind.JAXBException;
import org.codehaus.groovy.control.CompilationFailedException;
import com.github.yracnet.gen.spec.Factory;

public class Generate {

    private final File model;
    private final File output;
    private String groupId;
    private String artifactId;
    private String module;
    private String contextPath;
    private String namespace;
    private final List<File> templateList = new ArrayList<>();
    private final List<String> prefixList = new ArrayList<>();

    public Generate(String model, String output) {
        this.model = new File(model);
        this.output = new File(output);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        if (contextPath != null) {
            contextPath = contextPath.replaceAll("(^[/]*)|([/]*$)", "");
        }
        this.contextPath = contextPath;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void templateEach(Consumer<File> fn) {
        templateList.forEach(fn);
    }

    public void generate() {
        File parent = model.getParentFile();
        String jsonString = Util.createJsonString(model);
        JsonSlurper jsonSlurper = new JsonSlurper();
        Object mapper = jsonSlurper.parseText(jsonString);
        Factory fn = new FactoryImpl();
        fn.addPrefix(prefixList);
        TemplateEngine engine = new SimpleTemplateEngine();
        Map<String, Object> param = new HashMap<>();
        param.put("groupId", groupId);
        param.put("artifactId", artifactId);
        param.put("module", module);
        param.put("contextPath", contextPath);
        param.put("ns", namespace);
        param.put("mapper", mapper);
        param.put("fn", fn);
        templateEach(file -> {
            try {
                Template template = engine.createTemplate(file);
                Writable out = template.make(param);
                File x = new File(parent, file.getName());
                System.out.println("::::::::::::::::::--->" + x);
                if (x.exists()) {
                    x.delete();
                }
                //out.writeTo(new PrintWriter(System.out));
                out.writeTo(new FileWriter(x));
                GenRoot item = Util.readRoot(x);
                item.genFileEach(genFile -> {
                    File outFile = genFile.getFileOutput(output, artifactId, module);
                    if (genFile.isSkip()) {
                        System.out.println("Write Skip: " + outFile);
                    } else {
                        String outContent = genFile.getGenerateContent();
                        Util.writeContent(outContent, outFile, genFile.isAppend(), genFile.getComment());
                    }
                });
            } catch (IOException | JAXBException | ClassNotFoundException | CompilationFailedException e) {
                System.out.println("Error Process: " + file + ":" + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void addTemplate(String template) {
        template = template.replace(".", "/") + ".xml";
        File it = Util.getResourceFile(template);
        templateList.add(it);
    }

    public void addTemplateDirectory(String template) {
        template = template.replace(".", "/");
        File directory = Util.getResourceFile(template);
        if (directory.isDirectory()) {
            File files[] = directory.listFiles(it -> it.isFile());
            for (File it : files) {
                templateList.add(it);
            }
        }
    }

    public void removePrefix(String prefix) {
        prefixList.add(prefix);
        prefixList.add(prefix.toLowerCase());
        prefixList.add(prefix.toUpperCase());
    }

}
