/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yracnet.gen.spec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yracnet
 */
public class FnContext {

    public Map deduce(String name) {
        return deduce(name, "");
    }

    public Map deduceName(String name) {
        return deduceName(name, "");
    }

    public Map deduce(String name, String classifier) {
        Map map = new HashMap();
        String nameSufix = name + classifier;
        nameSufix = nameSufix.replace("-", "$");
        nameSufix = upperFirst(nameSufix);
        String varSufix = toVar(nameSufix);
        map.put("type", nameSufix);
        map.put("var", varSufix);
        map.put("text", toText(nameSufix));
        map.put("const", toConst(nameSufix));
        map.put("path", toPath(nameSufix));
        map.put("uscore", toUnderscore(nameSufix));
        map.put("colon", toColon(nameSufix));
        map.put("dash", toDash(nameSufix));
        map.put("varList", varSufix + "List");
        map.put("get", "get" + nameSufix);
        map.put("set", "set" + nameSufix);
        map.put("param", nameSufix + " " + varSufix);
        return map;
    }

    public Map deduceName(String name, String classifier) {
        name = toName(name);
        return deduce(name, classifier);
    }

    public Map deduceName(String name, String classifier, String ns) {
        name = toName(name);
        name = ns + name;
        return deduce(name, classifier);
    }

    public Map deduceAttrName(Map attr) {
        String name = (String) attr.get("name");
        Map map = deduceName(name);
        String type = attr.get("attributeType").toString();
        map.put("type", type);
        int i = type.lastIndexOf(".");
        if (i > 0) {
            type = type.substring(i + 1);
        }
        type = type.matches("(long|Long|Integer|int|Double|double)") ? "number" : type.matches("(String)") ? "string" : type;
        map.put("jsType", type);
        map.put("it", attr);
        return map;
    }

    public Map deduceRefName(Map attr, Map ref) {
        String connectedEntityId = (String) attr.get("connectedEntityId");
        ref = (Map) ref.get(connectedEntityId);
        boolean coll = attr.get("collectionType") != null;
        String clazz = (String) ref.get("clazz");
        clazz = toName(clazz);
        String name = (String) attr.get("name");
        Map map = deduceName(name);
        map.put("type", clazz);
        map.put("it", attr);
        String jsClazz = coll? "Array<"+clazz+">" : clazz;
        map.put("jsType", jsClazz);
        return map;
    }


//======================REACT==============================


    public Map deduceReact(String name, String classifier) {
        Map map = new HashMap();
        String nameSufix = name + classifier;
        nameSufix = nameSufix.replace("-", "$");
        nameSufix = upperFirst(nameSufix);
        String varSufix = toVar(nameSufix);
        map.put("type", nameSufix);
        map.put("var", varSufix);
        //map.put("text", toText(nameSufix));
        //map.put("const", toConst(nameSufix));
        //map.put("path", toPath(nameSufix));
        //map.put("uscore", toUnderscore(nameSufix));
        //map.put("colon", toColon(nameSufix));
        //map.put("dash", toDash(nameSufix));
        //map.put("varList", varSufix + "List");
        //map.put("get", "get" + nameSufix);
        //map.put("set", "set" + nameSufix);
        //map.put("param", nameSufix + " " + varSufix);
        return map;
    }

    public Map deduceNameReact(String name, String classifier) {
        name = toName(name);
        return deduceReact(name, classifier);
    }

    public Map deduceNameReact(String name) {
        name = toName(name);
        return deduceReact(name, "");
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

    public String toPath(String name) {
        name = name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1/$2");
        return name.toLowerCase();
    }

    public String toDollar(String name) {
        name = name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1\\$$2");
        return name.toLowerCase();
    }

    public String toNameLiteral(String name) {
        name = toName(name);
        return toLiteral(name);
    }

    public String toLiteral(String name) {
        return name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1 $2");
    }

    public String toUnderscore(String name) {
        return name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1_$2").toLowerCase();
    }

    public String toColon(String name) {
        return name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1:$2").toLowerCase();
    }

    public String toDash(String name) {
        return name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1-$2").toLowerCase();
    }

    public String toNameText(String name) {
        name = toName(name);
        return toText(name);
    }

    public String toText(String name) {
        name = name.replace("$", " ");
        return name.replaceAll("([a-z0-9]+)([A-Z0-9]+)", "$1 $2");
    }

    public static void main(String arg[]) {
        FnContext fn = new FnContext();
        String values[] = {"AaaBbbb", "aaaBbbb", "xxxx"};
        for (String value : values) {
            System.out.println("===========>" + value);
            System.out.println("---->" + fn.toName(value));
            System.out.println("---->" + fn.toNameVar(value));
            System.out.println("---->" + fn.toNameConst(value));
            System.out.println("---->" + fn.toNameLiteral(value));
        }
    }

    private final List<String> prefix = new ArrayList<>();

    public void addPrefix(List<String> list) {
        prefix.addAll(list);
    }

    public String toName(String name) {
        for (String pre : prefix) {
            name = name.replaceFirst("^" + pre, "");
        }
        return name;
    }

    public boolean isName(String name) {
        for (String pre : prefix) {
            if (name.startsWith(pre)) {
                return true;
            }
        }
        return false;
    }

    public String pkg(String... part) {
        String all = "";
        for (String s : part) {
            all = all + "." + s;
        }
        all = all.replace("-", ".");
        all = all.replace("/", ".");
        return all.substring(1);
    }

    private String lowerFirst(String name) {
        return name.toLowerCase().charAt(0) + name.substring(1);
    }

    private String upperFirst(String name) {
        return name.toUpperCase().charAt(0) + name.substring(1);
    }
}
