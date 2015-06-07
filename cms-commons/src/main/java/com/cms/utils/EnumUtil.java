package com.cms.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class EnumUtil {
    public static Map<String,String> enumProp2Name(Class<? extends Enum> clz,String propName) {
        if(!clz.isEnum()) return null;
        try {
            Enum[] enums = clz.getEnumConstants();
            Map<String,String> rels = new HashMap<String,String>();
            for(Enum en:enums) {
                System.out.println(en.name()+":"+(String)PropertyUtils.getProperty(en, propName));
                rels.put(en.name(),(String)PropertyUtils.getProperty(en, propName));
            }
            return rels;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
