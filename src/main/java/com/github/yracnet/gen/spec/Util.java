/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen.spec;

import com.github.yracnet.jpa.spec.Entity;
import com.github.yracnet.jpa.spec.EntityMappings;
import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author yracnet
 */
public class Util {

    public static File getProjectPath() {
        String path = Util.class.getResource("/").getPath();
        System.out.println("--->" + path);
        return new File(path + "/../..");
    }

    public static File getTemplateFile(String name) {
        File base = getProjectPath();
        return new File(base, "/src/main/resources/template/layer/" + name);
    }

    public static File getResourceFile(String name) {
        File base = getProjectPath();
        return new File(base, "/src/main/resources/" + name);
    }

    public static String createJsonString(File jpaModel) throws RuntimeException {
        try {
            File modelXml = new File(jpaModel.getParent(), "model.xml");
            File modelJson = new File(jpaModel.getParent(), "model.json");

            JAXBContext jc = JAXBContext.newInstance(new Class<?>[]{EntityMappings.class, Entity.class});
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Source source = new StreamSource(jpaModel);
            EntityMappings entityMapper = unmarshaller.unmarshal(source, EntityMappings.class).getValue();
            Marshaller marshaller = jc.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(entityMapper, modelXml);

            JsonBuilder builder = new JsonBuilder(entityMapper);
            String jsonString = builder.toPrettyString();
            Files.write(Paths.get(modelJson.toURI()), jsonString.getBytes(), StandardOpenOption.CREATE);
            return jsonString;
        } catch (IOException | JAXBException | NullPointerException e) {
            throw new RuntimeException("Error Read and Write model: " + jpaModel, e);
        }
    }

    public static void writeContent(String content, File file) {
        try {
            Files.write(Paths.get(file.toURI()), content.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.out.println("Error: " + file + " create: " + e.getMessage());
        }
    }
//public static GenFileRoot read

    public static GenFileRoot readFileArray(File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(new Class<?>[]{GenFileRoot.class, GenFile.class});
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Source source = new StreamSource(file);
        return unmarshaller.unmarshal(source, GenFileRoot.class).getValue();
    }
}
