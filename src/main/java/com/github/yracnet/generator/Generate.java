package com.github.yracnet.generator;

import com.github.yracnet.gen.spec.FnContext;
import com.github.yracnet.gen.spec.GenFileRoot;
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

public class Generate {

    private final File model;
    private final File output;
    private String groupId;
    private String artifactId;
    private String module;
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

    public void templateEach(Consumer<File> fn) {
        templateList.forEach(fn);
    }

    public void generate() {
        File parent = model.getParentFile();
        String jsonString = Util.createJsonString(model);
        JsonSlurper jsonSlurper = new JsonSlurper();
        Object mapper = jsonSlurper.parseText(jsonString);
        FnContext fn = new FnContext();
        fn.addPrefix(prefixList);
        TemplateEngine engine = new SimpleTemplateEngine();
        Map<String, Object> param = new HashMap<>();
        param.put("groupId", groupId);
        param.put("artifactId", artifactId);
        param.put("module", module);
        param.put("mapper", mapper);
        param.put("fn", fn);
        templateEach(file -> {
            try {
                Template template = engine.createTemplate(file);
                Writable out = template.make(param);
                File x = new File(parent, file.getName());
                //out.writeTo(new PrintWriter(System.out));
                out.writeTo(new FileWriter(x));
                GenFileRoot item = Util.readFileArray(x);
                item.genFileEach(genFile -> {
                    File outFile = genFile.getFileOutput(output, artifactId, module);
                    String outContent = genFile.getGenerateContent();
                    Util.writeContent(outContent, outFile);
                });
            } catch (IOException | JAXBException | ClassNotFoundException | CompilationFailedException e) {
                System.out.println("Error Process: " + file + ":" + e.getMessage());
            }
        });
    }

    public void addTemplate(String template) {
        template = template.replace(".", "/") + ".xml";
        File it = Util.getResourceFile(template);
        templateList.add(it);
    }

    public void removePrefix(String prefix) {
        prefixList.add(prefix);
        prefixList.add(prefix.toLowerCase());
        prefixList.add(prefix.toUpperCase());
    }

}
