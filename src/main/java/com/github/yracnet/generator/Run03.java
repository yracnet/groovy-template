/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.generator;

import static com.github.yracnet.generator.Util.*;
import groovy.json.JsonSlurper;
import groovy.lang.Writable;
import groovy.text.GStringTemplateEngine;
import groovy.text.SimpleTemplateEngine;
import groovy.text.XmlTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;
import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;
import groovy.xml.XmlUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.json.XML;
import org.json.XMLTokener;

/**
 *
 * @author yracnet
 */
public class Run03 {

    public static void main(String[] arg) throws Exception {
        File basebir = getBasedir();
        File xmlModel = new File(basebir, "model.jpa");
        File jsonModel = new File(basebir, "model.json");
        //slurper
        XmlSlurper xmlSlurper =  new XmlSlurper();
        JsonSlurper jsonSlurper = new JsonSlurper();
        //parse
        GPathResult xml = xmlSlurper.parse(xmlModel);
        Object json = jsonSlurper.parse(jsonModel);
        //String xmlString = XmlUtil.serialize(xml);
        //System.out.println("--->" + xmlString);
        //System.out.println("--->" + xml.text()   );

        File file = new File(basebir, "serv.xml");
        TemplateEngine engine = new SimpleTemplateEngine();
        Template template = engine.createTemplate(file);
        Map<String, Object> param = new HashMap<>();
        param.put("groupId", "dev.yracnet");
        param.put("artifactId", "customer");
        param.put("module", "manager");
        param.put("name", "CrmCustomer");
        param.put("values", new int[]{1, 2, 3, 4});
        param.put("xml", xml);
        param.put("json", json);
        param.put("fn", new Fn());
        Writable output = template.make(param);
        Writer writer = new PrintWriter(System.out);
        output.writeTo(writer);
        /**/
    }
}
