import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * @author gb
 * @version 1.0
 * description:
 * @date 2021/6/3 0:18
 */

public class Demo {
    private static Map<String, String> excelMap = new HashMap<>();
    private static final String defaultValue = "";
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");

    @Test
    public void test1() throws ScriptException {
       String a = "A";
        System.out.println(a.indexOf("b"));
    }

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
            return "ERROR";
        }
        return eval.toString();
    }
}
