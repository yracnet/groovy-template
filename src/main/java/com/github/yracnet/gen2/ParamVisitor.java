/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen2;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.yracnet.data.TSEntry;

/**
 *
 * @author yracnet
 */
public class ParamVisitor extends CommonVisitor {
    boolean sw = false;

    ParamVisitor(TSEntry entry, StringBuilder out) {
        super(entry, out);
    }

    @Override
    public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
        if(sw == true){
            return null;
        }
        sw  =true;
        String name = n.getNameAsString();
        out.append("\nexport interface ").append(name).append(" {\n");
        Object o = super.visit(n, arg);
        out.append("\n}");
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
