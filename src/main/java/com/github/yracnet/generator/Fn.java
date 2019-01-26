/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class Fn {

    public Map deduce(String name) {
        return deduce(name, "");
    }

    public Map deduceName(String name) {
        return deduceName(name, "");
    }

    public Map deduce(String name, String sufix) {
        Map map = new HashMap();
        String nameFull = name + sufix;
        String var = toVar(nameFull);
        map.put("type", nameFull);
        map.put("var", var);
        map.put("text", toText(nameFull));
        map.put("const", toConst(nameFull));

        map.put("varList", var + "List");
        map.put("get", "get" + nameFull);
        map.put("set", "set" + nameFull);
        map.put("param", nameFull + " " + var);

        return map;
    }

    public Map deduceName(String name, String sufix) {
        name = toName(name);
        return deduce(name, sufix);
    }

    public Map deduceAttrName(Map attr) {
        String name = (String) attr.get("name");
        Map map = deduceName(name);
        map.put("type", attr.get("attributeType"));
        map.put("it", attr);
        return map;
    }

    public Map deduceRefName(Map attr, Map ref) {
        String connectedEntityId = (String) attr.get("connectedEntityId");
        ref = (Map)ref.get(connectedEntityId);
        String clazz = (String) ref.get("clazz");
        clazz = toName(clazz);
        String name = (String) attr.get("name");
        Map map = deduceName(name);
        map.put("type", clazz);
        map.put("it", attr);
        return map;
    }

    public String toName(String name) {
        name = name.replaceFirst("^Crm", "");
        name = name.replaceFirst("^crm", "");
        name = name.replaceFirst("^Mkt", "");
        name = name.replaceFirst("^mkt", "");
        return name;
    }

    public boolean isName(String name) {
        return name.startsWith("Crm") || name.startsWith("Mkt")
                || name.startsWith("crm") || name.startsWith("mkt");
    }

    public String toNameVar(String name) {
        name = toName(name);
        return toVar(name);
    }

    public String toVar(String name) {
        return name.toLowerCase().charAt(0) + name.substring(1);
    }

    public String toNameConst(String name) {
        name = toName(name);
        return toConst(name);
    }

    public String toConst(String name) {
        name = name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1_$2");
        return name.toUpperCase();
    }

    public String toNameLiteral(String name) {
        name = toName(name);
        return toLiteral(name);
    }

    public String toLiteral(String name) {
        return name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1 $2");
    }

    public String toNameText(String name) {
        name = toName(name);
        return toText(name);
    }

    public String toText(String name) {
        return name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1 $2");
    }

    public static void main(String arg[]) {
        Fn fn = new Fn();
        String values[] = {"AaaBbbb", "aaaBbbb", "xxxx"};
        for (String value : values) {
            System.out.println("===========>" + value);
            System.out.println("---->" + fn.toName(value));
            System.out.println("---->" + fn.toNameVar(value));
            System.out.println("---->" + fn.toNameConst(value));
            System.out.println("---->" + fn.toNameLiteral(value));
        }
    }
}
