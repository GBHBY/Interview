import com.gyb.demo.Spreadsheet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test2 {

    @Test
    public void testFormulaSpec() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("B", " =7"); // note leading space
        assertEquals("Not a formula", " =7", sheet.get("B"));
        assertEquals("Unchanged", " =7", sheet.getLiteral("B"));
    }

    @Test
    public void testConstantFormula() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=7");
        assertEquals("Formula", "=7", sheet.getLiteral("A"));
        assertEquals("Value", "7", sheet.get("A"));
    }

    @Test
    public void testParentheses() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=(7)");
        assertEquals("Parends", "7", sheet.get("A"));
    }

    @Test
    public void testDeepParentheses() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=((((10))))");
        assertEquals("Parends", "10", sheet.get("A"));
    }

    @Test
    public void testMultiply() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=2*3*4");
        assertEquals("Times", "24", sheet.get("A"));
    }

    @Test
    public void testAdd() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=71+2+3");
        assertEquals("Add", "76", sheet.get("A"));
    }

    @Test
    public void testPrecedence() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=7+2*3");
        assertEquals("Precedence", "13", sheet.get("A"));
    }

    @Test
    public void testFullExpression() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=7*(2+3)*((((2+1))))");
        assertEquals("Expr", "105", sheet.get("A"));
    }

    @Test
    public void testSimpleFormulaError() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=7*");
        assertEquals("Error", "#Error", sheet.get("A"));
    }

    @Test
    public void testParenthesisError() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "=(((((7))");
        assertEquals("Error", "#Error", sheet.get("A"));
    }


}
