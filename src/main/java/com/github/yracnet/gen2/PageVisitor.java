/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen2;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.yracnet.data.SourceEntry;
import com.github.yracnet.data.TSEntry;
import com.github.yracnet.gen.spec.FactoryAbstract;
import com.github.yracnet.gen.spec.GenRoot;
import java.util.Optional;

/**
 *
 * @author yracnet
 */
public class PageVisitor extends CommonVisitor {

    static void createPage(TSEntry service, GenRoot genRoot) {

    }

    public static TSEntry createPage(SourceEntry it) {
        TSEntry entry = new TSEntry();
        entry.setJava(it.getFullName());
        entry.setPath("page");
        entry.setName(it.getClassName() + "Page");
        StringBuilder out = new StringBuilder();
        try {
            PageVisitor sv = new PageVisitor(entry, out);
            sv.visit(it.getCunit(), null);
        } catch (Exception e) {
            out.append("Error: ")
                    .append(e.getMessage());
        }
        entry.setContent(out.toString());
        return entry;
    }

    PageVisitor(TSEntry entry, StringBuilder out) {
        super(entry, out);
    }

    @Override
    public Object visit(MethodDeclaration n, Object arg) {
        String name = n.getNameAsString();
        name = FactoryAbstract.toType(name);
        out.append("export interface ").append(name).append("Props extends RouteComponentProps<{ id: string }> {\n");
        out.append("   hide?: boolean\n");
        out.append("}\n");
        appendLN();
        out.append("export interface ").append(name).append("State {\n");
        out.append("   hide?: boolean\n");
        out.append("}\n");
        appendLN();
        out.append("class ").append(name).append("Logic extends React.PureComponent<").append(name).append("Props,").append(name).append("State> {\n");
        out.append("   render(): React.ReactNode {\n");
        out.append("      return <h1>").append(name).append("Page</h1>\n");
        out.append("   }\n");
        out.append("}\n");
        appendLN();
        out.append("export const ").append(name).append("Page = withRouter(").append(name).append("Logic);");
        return super.visit(n, arg);
    }

}
