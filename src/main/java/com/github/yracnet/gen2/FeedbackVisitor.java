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
public class FeedbackVisitor extends CommonVisitor {

    FeedbackVisitor(TSEntry entry, StringBuilder out) {
        super(entry, out);
    }

    @Override
    public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
        String name = n.getNameAsString();
        out.append("\nexport interface ").append(name).append("Feedback {\n");
        Object o = super.visit(n, arg);
        //out.append("   [attr: string]: any\n}");
        out.append("\n}");
        return o;
    }

    @Override
    public Object visit(VariableDeclarator n, Object arg) {
        String name = n.getNameAsString();
        String type = n.getTypeAsString();
        if (!name.equals("serialVersionUID")) {
            out.append("   ").append(name).append("?: FieldFeedback,\n");
        }
        return super.visit(n, arg);
    }

}
