/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen2;

import com.github.yracnet.data.TSEntry;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.yracnet.data.SourceEntry;
import com.github.yracnet.gen.spec.FactoryAbstract;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yracnet
 */
public class ServiceVisitor extends CommonVisitor {

    public static TSEntry createService(SourceEntry it) {
        TSEntry entry = new TSEntry();
        entry.setJava(it.getFullName());
        String path = FactoryAbstract.toPath(it.getClassName());
        path = path.replace("/serv", "");
        entry.setPath(path);
        entry.setName(it.getClassName());
        StringBuilder out = new StringBuilder();
        try {
            ServiceVisitor sv = new ServiceVisitor(entry, out);
            System.out.println("Process: " + it);
            sv.visit(it.getCunit(), null);
        } catch (Exception ex) {
            out.append("Error: ")
                    .append(ex.getMessage());
        }
        String content = out.toString();
        List<String> ref = entry.getReference();
        Collections.sort(ref);
        String modelNames = String.join(", ", ref);
        content = content.replace("#MODEL_NAMES#", modelNames);
        entry.setContent(content);
        return entry;
    }

    ServiceVisitor(TSEntry entry, StringBuilder out) {
        super(entry, out);
    }

    private String rootPath = "";

    @Override
    public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
        out.append("// Source: ").append(entry.getJava());
        appendLN();
        out.append("import axios from 'axios';");
        appendLN();
        out.append("import { endpoint, processCaller, FnError, FnResultSet, FnResultObject, FnResultVoid } from \"../Endpoint\";");
        appendLN();
        String serviceModel = n.getNameAsString();
        serviceModel = serviceModel.replace("Serv", "Model");
        out.append("import { #MODEL_NAMES# } from \"./").append(serviceModel).append("\";");
        appendLN();
        System.out.println("======================================================");
        Optional<AnnotationExpr> pathExp = n.getAnnotationByName("Path");
        pathExp.ifPresent(it -> {
            SingleMemberAnnotationExpr s = (SingleMemberAnnotationExpr) it;
            rootPath = s.getMemberValue().asStringLiteralExpr().getValue();
            out.append("// @Root(\"").append(rootPath).append("\")");
            appendLN();
        });
        return super.visit(n, arg);
    }

    @Override
    public Object visit(MethodDeclaration n, Object arg) {
        String method = n.getAnnotationByName("POST").isPresent() ? "post"
                : n.getAnnotationByName("GET").isPresent() ? "get"
                : n.getAnnotationByName("DELETE").isPresent() ? "delete"
                : n.getAnnotationByName("PUT").isPresent() ? "put" : "get";

        Optional<AnnotationExpr> pathExp = n.getAnnotationByName("Path");
        //Parameter parameter = n.getParameter(0);
        pathExp.ifPresent(it -> {
            SingleMemberAnnotationExpr s = (SingleMemberAnnotationExpr) it;
            String subpath = s.getMemberValue().asStringLiteralExpr().getValue();
            appendLN();
            n.getAnnotations().forEach(it1 -> {
                append("// ").append(it1.toString());
                appendLN();
            });
            append("// ").append(n.getDeclarationAsString());
            appendLN();
            if (subpath.equals("/")) {
                subpath = "";
            }
            append("export const ").append(n.getNameAsString()).append(" = ");
            if (n.getParameters().isEmpty()) {
                append("( fnResult?: Fn").append(n.getTypeAsString());
                append(", fnError?: FnError) => {");
                appendLN();
                append("  let caller = axios.").append(method).append("(`${endpoint}/")
                        .append(rootPath)
                        .append("/")
                        .append(subpath)
                        .append("`);");
            } else {
                Parameter p = n.getParameter(0);
                append("( payload: ").append(p.getTypeAsString());
                append(", fnResult?: Fn").append(n.getTypeAsString());
                append(", fnError?: FnError) => {");
                appendLN();
                append("  let caller = axios.").append(method).append("(`${endpoint}/")
                        .append(rootPath)
                        .append("/")
                        .append(subpath)
                        .append("`, payload);");
            }
            appendLN();
            append("  processCaller(caller, fnResult, fnError);");
            appendLN();
            append("}");
            appendLN();
            if (n.getType() instanceof ClassOrInterfaceType) {
                ClassOrInterfaceType c = (ClassOrInterfaceType) n.getType();
                c.getTypeArguments().ifPresent(cc -> {
                    addReference(cc.get(0).asString());
                });
                //System.out.println("REF::::::---->" + n.getTypeAsString() + " - " + c.getTypeArguments().get());
            } else {
                addReference(n.getTypeAsString());
            }

            //if (!n.getTypeParameters().isEmpty()) {
            //    System.out.println("REF---->" + n.getTypeAsString() + " - " + n.getTypeParameters());
            //    addReference(n.getTypeParameter(0).asString());
            //}
            if (!n.getParameters().isEmpty()) {
                addReference(n.getParameter(0).getTypeAsString());
            }
        });
        return super.visit(n, arg);
    }

}
