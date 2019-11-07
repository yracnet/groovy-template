/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen2;

import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.yracnet.data.TSEntry;

/**
 *
 * @author wyujra
 */
public class CommonVisitor extends GenericVisitorAdapter {

    final TSEntry entry;
    final StringBuilder out;

    CommonVisitor(TSEntry entry, StringBuilder out) {
        this.entry = entry;
        this.out = out;
    }

    public void appendLN() {
        out.append("\n");
    }

    public StringBuilder append(String string) {
        return out.append(string);
    }

    public StringBuilder appendLN(String string) {
        return out.append(string).append("\n");
    }

    public void addReference(String name) {
        entry.addReference(name);
    }

}
