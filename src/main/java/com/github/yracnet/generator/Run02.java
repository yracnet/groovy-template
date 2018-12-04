/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.generator;

import static com.github.yracnet.generator.Run01.getBasedir;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.XmlTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class Run02 {

    public static File getBasedir() {
        String path = Run01.class.getResource("/template").getPath();
        return new File(path);
    }

    public static void main(String[] arg) throws Exception {
        File basebir = getBasedir();
        File file = new File(basebir, "main.xml");
        //TemplateEngine engine = new SimpleTemplateEngine();
        TemplateEngine engine = new XmlTemplateEngine();
        Template template = engine.createTemplate(file);
        Map<String, Object> model = new HashMap<>(); 
        model.put("salutation", "1000");
        model.put("firstname", "Willyams");
        model.put("nickname", "yracnet");
        model.put("lastname", "Yujra");
        
        Writable output = template.make(model);
        Writer writer = new PrintWriter(System.out);
        output.writeTo(writer);

    }
}
