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
public interface Factory {
    
    
    public void addPrefix(List<String> list);

    public Map deduce(String name);

    public Map deduceName(String name);

    public Map deduce(String name, String classifier);

    public Map deduceName(String name, String classifier);

    public Map deduceName(String name, String classifier, String ns);

    public Map deduceAttrName(Map attr);

    public Map deduceRefName(Map attr, Map ref);

//======================REACT==============================
    public Map deduceReact(String name, String classifier);

    public Map deduceNameReact(String name, String classifier);

    public Map deduceNameReact(String name);

}
