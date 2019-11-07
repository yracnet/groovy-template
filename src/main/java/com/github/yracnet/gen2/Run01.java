/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen2;

import com.github.yracnet.data.TSEntry;
import com.github.yracnet.data.SourceEntry;
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

        String src = "/work/dev/bcb-01/R03/mrp/mrp-portal/mrp-portal-serv/src/main/java";
        Path root = Paths.get(src);
        List<SourceEntry> files = Files.find(root, 999, (p, f) -> f.isRegularFile() && p.getFileName().toString().endsWith(".java"))
                .map(it -> it.toFile())
                //.peek(System.out::println)
                .map(it -> SourceEntry.createSourceEntry(it))
                .filter(it -> it != null)
                .collect(Collectors.toList());
        System.out.println("--->" + src);
//        List<SourceEntry> datas = files.stream()
//                .filter(it-> !it.isInterface())
//                .collect(Collectors.toList());
        List<TSEntry> services = files.stream()
                .filter(it -> it.isInterface())
                .map(it -> ServiceVisitor.createService(it))
                //.peek(System.out::println)
                .collect(Collectors.toList());

        String out = "/work/dev/bcb-01/R03/generate/mrp-portal/mrp-portal-view/src/main";
        File dir = new File(out);
        services.forEach(it -> {
            Util.writeContent(it.getContent(), it.getFile(dir), false, null);
        });
        List<TSEntry> pojos = services.stream().map(it -> {
            List<SourceEntry> datas = files.stream()
                    .filter(it2 -> it.isReference(it2.getClassName()))
                    .collect(Collectors.toList());
            return DataVisitor.createData(it.getPath(), it.getName().replace("Serv", "Model"), datas);
        })
                //.peek(System.out::println)
                .collect(Collectors.toList());
        pojos.forEach(it -> {
            Util.writeContent(it.getContent(), it.getFile(dir), false, null);
        });

        //services.
//        List<TSEntry> datas = files.stream()
//                .filter(it-> it.isInterface())
//                .map(it -> DataVisitor.createData(it))
//                .peek(System.out::println)
//                .collect(Collectors.toList());
    }
}
