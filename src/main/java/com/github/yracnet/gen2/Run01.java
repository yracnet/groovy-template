/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen2;

import com.github.yracnet.gen.spec.GenRoot;
import com.github.yracnet.data.SourceEntry;
import com.github.yracnet.data.TSEntry;
import com.github.yracnet.gen.spec.Util;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author wyujra
 */
public class Run01 {

    public static void main(String[] args) throws Exception {

        String out = "/work/dev/bcb-01/R03/generate/mrp-portal/mrp-portal-view/src/main";
        File output = new File(out);
        String src = "/work/dev/bcb-01/R03/mrp/mrp-portal/mrp-portal-serv/src/main/java";
        Path root = Paths.get(src);

        List<SourceEntry> files = Files.find(root, 999, (p, f) -> f.isRegularFile() && p.getFileName().toString().endsWith(".java"))
                .map(it -> it.toFile())
                //.peek(System.out::println)
                .map(it -> SourceEntry.createSourceEntry(it))
                .filter(it -> it != null)
                .collect(Collectors.toList());
        System.out.println("--->" + src);
        //----------------------------------------------------------------------
        files.stream()
                .filter(it -> it.isInterface())
                .map(it -> {
                    GenRoot genRoot = new GenRoot();
                    TSEntry service = ServiceVisitor.createService(it);
                    genRoot.addGenFile(service.asGenFile());
                    DataVisitor.createData(service, files, genRoot);
                    PageVisitor.createPage(service, genRoot);
                    return genRoot;
                })
                .forEach(it -> {
                    it.genFileEach(file -> {
                        Util.writeContent(file.getContent(), file.getFileOutput(output, "tsx"), false, null);
                    });
                });

    }
}
