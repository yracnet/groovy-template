
package com.github.yracnet.generator;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class Run01 {

    public static File getBasedir() {
        String path = Run01.class.getResource("/template").getPath();
        return new File(path);
    }

    public static void main(String arg[]) throws Exception {
        File basebir = getBasedir();
        File file = new File(basebir, "T01.groovy");
        SimpleTemplateEngine engine = new SimpleTemplateEngine();
        Map binding = new HashMap();
        binding.put("firstname", "Grace");
        binding.put("lastname", "Hopper");
        binding.put("accepted", true);
        binding.put("title", "Groovy for COBOL programmers");
        binding.put("values", new int[]{1,2,3,4});
        

        Template template = engine.createTemplate(file);
        Writable writable = template.make(binding);
        Writer writer = new PrintWriter(System.out);
        writable.writeTo(writer);

    }

}
