package com.gyb.demo;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * @author gb
 * @version 1.0
 * description:
 * @date 2021/6/10 19:19
 */

public class Spreadsheet {

    private static Map<String, String> excelMap = new HashMap<>(702);
    private static final String defaultValue = "";
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");

    static {
        List<String> A = new ArrayList<>(26);
        List<String> B = new ArrayList<>(26);
        List<String> list = new ArrayList<>(676);
        char demo = 'A';
        A.add(String.valueOf(demo));
        for (int i = 0; i < 25; i++) {
            A.add(String.valueOf(++demo));
        }
        B.addAll(A);
        int bSize = B.size();
        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                list.add(B.get(i) + B.get(j));

            }
        }
        B.stream().forEach(a -> excelMap.put(a, defaultValue));
        list.stream().forEach(a -> excelMap.put(a, defaultValue));
    }


    public void put(String column, String value) {
        excelMap.put(column, value);
    }


    public String get(String theCell) {

        String value = excelMap.get(theCell);
        if (StringUtils.isBlank(value)) {
            excelMap.put(theCell, value);
        } else {
            StringBuilder tempWithoutBlank = new StringBuilder(cleanSpace(value));
            /**=的位置**/
            int indexOf = -1;
            indexOf = value.indexOf("=");
            /**如果全为数字的话，那就清除首尾空格的字符串放进去**/
            if (StringUtils.isNumericSpace(String.valueOf(tempWithoutBlank))) {
                excelMap.put(theCell, tempWithoutBlank.toString());
                /**获取“=”后的的字符串**/
            } else if (indexOf == 0) {
                String substring = value.substring(indexOf + 1, value.length());
                if (substring.indexOf("(") != -1 || substring.indexOf("*") != -1
                        || substring.indexOf("+") != -1 || substring.indexOf("-") != -1 || substring.indexOf("/") != -1) {
                    value = getValue(substring);
                    excelMap.put(theCell, value);
                } else {
                    value = substring;
                    excelMap.put(theCell, value);
                }
            } else {
                excelMap.put(theCell, value);
            }
        }

        return excelMap.get(theCell);
    }

    public String getLiteral(String theCell) {
        return excelMap.get(theCell);
    }


    public String getValue(String str) {
        Object eval = null;
        try {
            eval = engine.eval(str);
        } catch (ScriptException e) {
            return "#Error";
        }
        if (eval != null) {
            return eval.toString();
        }
        return "";

    }

    public String cleanSpace(String str) {
        char one = str.charAt(0);
        char last = str.charAt(str.length() - 1);
        StringBuilder tempWithoutBlank = new StringBuilder(str);
        if (Character.isSpaceChar(one)) {
            tempWithoutBlank.delete(0, 1);
        }
        if (Character.isSpaceChar(last)) {
            tempWithoutBlank.delete(tempWithoutBlank.length() - 1, tempWithoutBlank.length());
        }
        return tempWithoutBlank.toString();
    }

}
