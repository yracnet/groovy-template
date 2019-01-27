/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.generator;

import com.github.yracnet.gen.spec.FnContext;
import static com.github.yracnet.gen.spec.Util.*;
import com.github.yracnet.jpa.spec.Entity;
import com.github.yracnet.jpa.spec.EntityMappings;
import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;

/**
 *
 * @author yracnet
 */
public class Run04 {

    public static void main(String[] arg) throws Exception {
        File dir = getProjectPath();
        File jpaModel = getTemplateFile("model2.jpa");
        File xmlModel = getTemplateFile("model2.xml");
        System.out.println("==>" + jpaModel);
        File jsonModel = new File(dir, "/src/main/resources/template/layer/model2.json");
        JAXBContext jc = JAXBContext.newInstance(new Class<?>[]{EntityMappings.class, Entity.class});
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Source source = new StreamSource(jpaModel);
        EntityMappings entityMapper = unmarshaller.unmarshal(source, EntityMappings.class).getValue();
        Marshaller marshaller = jc.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(entityMapper, xmlModel);

        JsonBuilder builder = new JsonBuilder(entityMapper);
        JsonSlurper jsonSlurper = new JsonSlurper();
        String jsonString = builder.toPrettyString();

        Files.write(Paths.get(jsonModel.getPath()), jsonString.getBytes(), StandardOpenOption.CREATE);

        Object mapper = jsonSlurper.parseText(builder.toString());
        File file = getTemplateFile("serv.xml");
        TemplateEngine engine = new SimpleTemplateEngine();
        Template template = engine.createTemplate(file);
        Map<String, Object> param = new HashMap<>();
        param.put("groupId", "dev.yracnet");
        param.put("artifactId", "customer");
        param.put("module", "manager");
        param.put("mapper", mapper);
        param.put("fn", new FnContext());
        Writable output = template.make(param);
        Writer writer = new PrintWriter(System.out);
        output.writeTo(writer);
        /**/
    }
}
