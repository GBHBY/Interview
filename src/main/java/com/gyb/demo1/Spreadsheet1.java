package com.gyb.demo1;

import org.apache.commons.lang.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * @author gb
 * @version 1.0
 * description:
 * @date 2021/6/10 20:53
 */

public class Spreadsheet1 {
    /**
     * 存储真实数据
     **/
    private static Map<String, String> excelMapA = new HashMap<>(702);
    /**
     * 存储关系
     **/
    private static Map<String, String> excelMapB = new HashMap<>(702);
    private static final String defaultValue = "";
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");

    private static String ERROR20 = "=#Error";
    private static String ERROR = "#Error";

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
        B.stream().forEach(a -> excelMapB.put(a, defaultValue));
        list.stream().forEach(a -> excelMapB.put(a, defaultValue));
        excelMapA.putAll(excelMapB);
    }


    public void put(String column, String value) {
        excelMapB.put(column, value);
        excelMapA.put(column, value);
        replace();

    }


    public String get(String theCell) {

        String value = excelMapA.get(theCell);
        if (StringUtils.isBlank(value)) {
            excelMapA.put(theCell, value);
        } else {
            StringBuilder tempWithoutBlank = new StringBuilder(cleanSpace(value));
            /**=的位置**/
            int indexOf = -1;
            indexOf = value.indexOf("=");
            /**如果全为数字的话，那就清除首尾空格的字符串放进去**/
            if (StringUtils.isNumericSpace(String.valueOf(tempWithoutBlank))) {
                excelMapA.put(theCell, tempWithoutBlank.toString());
                /**获取“=”后的的字符串**/
            } else if (indexOf == 0) {
                String substring = value.substring(indexOf + 1, value.length());
                if (substring.indexOf("(") != -1 || substring.indexOf("*") != -1
                        || substring.indexOf("+") != -1 || substring.indexOf("-") != -1 || substring.indexOf("/") != -1) {
                    value = getValue(substring);
                    excelMapA.put(theCell, value);
                } else {
                    value = substring;
                    excelMapA.put(theCell, value);
                }
            } else {
                excelMapA.put(theCell, value);
            }
        }
        /**对测试20的处理**/
        if (value.equals(theCell)) {
            value = "#Circular";
            excelMapA.put(theCell, value);
        }

        return excelMapA.get(theCell);
    }


    public String getLiteral(String theCell) {
        return excelMapB.get(theCell);
    }

    /**
     * @param str
     * @return
     * @deprecated 将传进来的String表达式进行运算
     */
    public String getValue(String str) {
        Object eval = null;
        try {
            if (str.length() == 1) {
                char c = str.charAt(0);
                if (c >= 65 && c <= 90) {
                    return str;
                }
            }
            eval = engine.eval(str);
        } catch (ScriptException e) {
            return ERROR;
        }
        return eval.toString();
    }

    /**
     * @return
     * @deprecated 全部替换
     */
    public void replace() {
        Map<String, String> valueNotNull = new HashMap<>(702);
        for (String key : excelMapB.keySet()) {
            if (StringUtils.isNotBlank(excelMapB.get(key))) {
                valueNotNull.put(key, excelMapB.get(key));
            }
        }
        if (valueNotNull.size() > 1) {
            int index = -1;
            StringBuilder builder = new StringBuilder();
            StringBuilder builder1 = new StringBuilder();
            /**遍历映射**/
            for (String key : valueNotNull.keySet()) {
                /**如果不是纯数字，就意味着有表达式**/
                builder.delete(0, builder.length());
                builder.append(valueNotNull.get(key));
                if (!StringUtils.isNumericSpace(builder.toString())) {
                    index = builder.toString().indexOf("=");
                    if (index != -1) {
                        builder.delete(index, index + 1);
                    }
                    char[] chars = builder.toString().toCharArray();
                    int charLen = chars.length;
                    String[] arr = new String[charLen];
                    for (int i = 0; i < charLen; i++) {
                        arr[i] = String.valueOf(chars[i]);
                    }
                    for (int i = 0; i < charLen; i++) {
                        /**说明值中含有A-Z**/
                        if (chars[i] >= 65 && chars[i] <= 90) {
                            String recursion = recursion(String.valueOf(chars[i]), excelMapA);
                            arr[i] = getValue(recursion);
                        }
                    }
                    builder1.delete(0, builder1.length());
                    builder1.append("=");
                    for (int i = 0; i < charLen; i++) {
                        builder1.append(arr[i]);
                    }
                    excelMapA.put(key, builder1.toString());


                }
            }
        }
    }

    /**
     * @param value
     * @param map
     * @return
     * @deprecated 寻找map中某个键值对的值是别的键值对的key，获取该key的value
     */
    public String recursion(String value, Map<String, String> map) {
        Map<String, String> valueNotNull = new HashMap<>(702);
        for (String key : map.keySet()) {
            if (StringUtils.isNotBlank(map.get(key))) {
                valueNotNull.put(key, map.get(key));
            }
        }
        for (String str : valueNotNull.keySet()) {
            String substring1 = str;
            if (value.equals(substring1)) {
                return recursion(map.get(substring1), valueNotNull);
            }
        }
        StringBuilder builder = new StringBuilder(value);
        int index = value.indexOf("=");
        if (index != -1) {
            builder.delete(index, index + 1);
            value = builder.toString();
        }

        return value;
    }

    /**
     * @param str
     * @return
     * @deprecated 清除掉首尾空格
     */
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
