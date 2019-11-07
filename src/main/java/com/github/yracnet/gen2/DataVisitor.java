/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen2;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.yracnet.data.SourceEntry;
import com.github.yracnet.data.TSEntry;
import java.util.List;

/**
 *
 * @author yracnet
 */
public class DataVisitor extends CommonVisitor {

    public static TSEntry createData(SourceEntry it) {
        TSEntry entry = new TSEntry();
        entry.setJava(it.getFullName());
        entry.setPath("data");
        entry.setName(it.getClassName());
        StringBuilder out = new StringBuilder();
        try {
            if (it.isFilter()) {
                FilterVisitor fv = new FilterVisitor(entry, out);
                fv.visit(it.getCunit(), null);
                out.append("\n");
            } else {
                DataVisitor dv = new DataVisitor(entry, out);
                dv.visit(it.getCunit(), null);
                out.append("\n");
                FeedbackVisitor fv = new FeedbackVisitor(entry, out);
                fv.visit(it.getCunit(), null);
                out.append("\n");
                ValidateVisitor vv = new ValidateVisitor(entry, out);
                vv.visit(it.getCunit(), null);
                out.append("\n");
            }
        } catch (Exception e) {
            out.append("Error: ")
                    .append(e.getMessage());
        }
        entry.setContent(out.toString());
        return entry;
    }

    static TSEntry createData(String path, String name, List<SourceEntry> datas) {
        TSEntry result = new TSEntry();
        result.setPath(path);
        result.setName(name);
        StringBuilder out = new StringBuilder();
        out.append("import { FieldFeedback, FieldFilter, FilterAbstract } from \"../Model\";\n");
        out.append("import { InputTextValidate } from \"ui-fast\";\n");
        out.append("\n");
        datas.forEach(it -> {
            TSEntry c = createData(it);
            out.append(c.getContent());
        });
        result.setContent(out.toString());
        return result;
    }

    DataVisitor(TSEntry entry, StringBuilder out) {
        super(entry, out);
    }

    @Override
    public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
        String name = n.getNameAsString();
        out.append("\nexport interface ").append(name).append(" {\n");
        Object o = super.visit(n, arg);
        out.append("   [attr: string]: any \n}");
        return o;
    }

    @Override
    public Object visit(VariableDeclarator n, Object arg) {
        String name = n.getNameAsString();
        String type = n.getTypeAsString();
        if (!name.equals("serialVersionUID")) {
            out.append("   ").append(name).append("?: ");
            type = detectType(type, name);
            out.append(type).append(",");
            out.append("\n");
        }
        return super.visit(n, arg);
    }

    private String detectType(String type, String name) {
        String result = type == null || type.contains("String")
                ? "string"
                : type.contains("Date")
                ? "Date"
                : type.contains("Long")
                || type.contains("Integer")
                || type.contains("Double")
                ? "number"
                : "any";
        if (name.startsWith("id") || name.endsWith("Id")) {
            result = result + " | string";
        }
        return result;
    }

}
