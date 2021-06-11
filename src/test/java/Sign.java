import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;


public class Sign {
    @Test
   public void test(){
 /*       Map<String, String> valueNotNull = new HashMap<>(702);
        for (String key : excelMapA.keySet()) {
            if (StringUtils.isNotBlank(excelMapA.get(key))) {
                valueNotNull.put(key, excelMapA.get(key));
            }
        }
        int index = -1;
        char[] chars = null;
        StringBuilder tem = new StringBuilder();
        for (String key : valueNotNull.keySet()) {
            String value = valueNotNull.get(key);
            valueNotNull.put(key, cleanSpace(value));
            index = value.indexOf("=");
            String substring = value;
            if (index != -1) {
                substring = value.substring(index + 1);
            }
            chars = substring.toCharArray();
            for (int c = 0; c < chars.length; c++) {
                chars[c] = (char) (Integer.parseInt(recursion(String.valueOf(chars[c]), valueNotNull)) + '0');
            }
            excelMapA.put(key, tem.append(chars).toString());
            valueNotNull.put(key, tem.toString());
            tem.delete(0, tem.length());
        }*/
   }
}