/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.data;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;

/**
 *
 * @author yracnet
 */
public class SourceMethod {

    private final String type;
    private final String name;
    private final int size;
    private final String arg;
    private final String signature;
	private String label;

    public SourceMethod(MethodDeclaration n) {
        type = n.getTypeAsString();
        name = n.getNameAsString();
        size = n.getParameters().size();
        String t = "";
        for (Parameter p : n.getParameters()) {
            t = t + "|" + p.getTypeAsString();
        }
        arg = size == 0 ? "|" : t;
        signature = "[" + size + "]" + type + ":" + name + arg;
    }

    public SourceMethod(MethodCallExpr n) {
        type = "*";
        name = n.getNameAsString();
        size = n.getArguments().size();
        String t = "|*";
        arg = t;
        signature = "[" + size + "]" + type + ":" + name + arg;
    }

}
