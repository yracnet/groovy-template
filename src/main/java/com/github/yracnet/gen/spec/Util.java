/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen.spec;

import com.github.yracnet.jpa.spec.Entity;
import com.github.yracnet.jpa.spec.EntityMappings;
import groovy.json.JsonBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author yracnet
 */
public class Util {

    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(new Class<?>[]{GenRoot.class, GenFile.class, EntityMappings.class, Entity.class});
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    public static File getProjectPath() {
        String path = Util.class.getResource("/").getPath();
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

    public static Source removeNamespace(File jpaModel) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            File xsltFile = getResourceFile("template/orm/remove-ns.xsl");
            Source xslt = new StreamSource(xsltFile);
            Transformer transformer = factory.newTransformer(xslt);
            Source source = new StreamSource(jpaModel);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Result result = new StreamResult(byteArrayOutputStream);
            //transformer.transform(source, new StreamResult(new File("output.xml")));
            transformer.transform(source, result);
            return new StreamSource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        } catch (TransformerException e) {
            throw new RuntimeException("Error remove Namespace", e);
        }
    }

    public static String createJsonString(File jpaModel) throws RuntimeException {
        try {
            File modelXml = new File(jpaModel.getParent(), "model.xml");
            File modelJson = new File(jpaModel.getParent(), "model.json");

            Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
            unmarshaller.setListener(new Unmarshaller.Listener() {

                @Override
                public void beforeUnmarshal(Object target, Object parent) {
                    if (target instanceof Entity && parent instanceof EntityMappings) {
                        EntityMappings ex = (EntityMappings) parent;
                        Entity ei = (Entity) target;
                        ei.setPkg(ex.getPpkg() + "." + ex.getEpkg());
                    }
                }

            });
            //Source source = new StreamSource(jpaModel);
            Source source = removeNamespace(jpaModel);
            EntityMappings entityMapper = unmarshaller.unmarshal(source, EntityMappings.class).getValue();
            Marshaller marshaller = JAXB_CONTEXT.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(entityMapper, modelXml);

            JsonBuilder builder = new JsonBuilder(entityMapper);
            String jsonString = builder.toPrettyString();
            Files.write(Paths.get(modelJson.toURI()), jsonString.getBytes(), StandardOpenOption.CREATE);
            if(modelXml.exists()){
                modelXml.delete();
            }
            if(modelJson.exists()){
                modelJson.delete();
            }
            return jsonString;
        } catch (IOException | JAXBException | NullPointerException e) {
            throw new RuntimeException("Error Read and Write model: " + jpaModel, e);
        }
    }

    public static void writeContent(String content, File file, boolean append, String comment) {
        try {
            if (file.exists()) {
                if (append == false) {
                    file.delete();
                }
            } else {
                file.getParentFile().mkdirs();
            }
            if (append == false) {
                Files.write(Paths.get(file.toURI()), content.getBytes(), StandardOpenOption.CREATE);
            } else {
                comment = "============================ " + comment + " ============================";
                String name = file.getName();
                if (name.matches(".*(java|js|ts|tsx)")) {
                    comment = "/* " + comment + " */";
                } else if (name.matches(".*(html|xhtml|xml)")) {
                    comment = "<!-- " + comment + " -->";
                }
                comment = "\n\n" + comment + "\n\n";
                Files.write(Paths.get(file.toURI()), comment.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(file.toURI()), content.getBytes(), StandardOpenOption.APPEND);
            }
            System.out.println("Write: " + file + " Append: " + append);
        } catch (IOException | NullPointerException e) {
            System.out.println("Error: " + file + " create: " + e.getMessage());
        }
    }
//public static GenRoot read

    public static GenRoot readRoot(File file) throws JAXBException {
        Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
        Source source = new StreamSource(file);
        return unmarshaller.unmarshal(source, GenRoot.class).getValue();
    }
}
