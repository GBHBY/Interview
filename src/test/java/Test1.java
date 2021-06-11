import com.gyb.demo.Spreadsheet;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Test1 {

    @Test
    public void testThatCellsAreEmptyByDefault() {
        Spreadsheet sheet = new Spreadsheet();
        assertEquals("", sheet.get("A"));
        assertEquals("", sheet.get("ZX"));
    }

    @Test
    public void testThatTextCellsAreStored() {
        Spreadsheet sheet = new Spreadsheet();
        String theCell = "A";

        sheet.put(theCell, "A string");
        assertEquals("A string", sheet.get(theCell));

        sheet.put(theCell, "A different string");
        assertEquals("A different string", sheet.get(theCell));

        sheet.put(theCell, "");
        assertEquals("", sheet.get(theCell));
    }

    @Test
    public void testThatManyCellsExist() {
        Spreadsheet sheet = new Spreadsheet();
        sheet.put("A", "First");
        sheet.put("X", "Second");
        sheet.put("ZX", "Third");

        assertEquals("A", "First", sheet.get("A"));
        assertEquals("X", "Second", sheet.get("X"));
        assertEquals("ZX", "Third", sheet.get("ZX"));

        sheet.put("A", "Fourth");
        assertEquals("A after", "Fourth", sheet.get("A"));
        assertEquals("X same", "Second", sheet.get("X"));
        assertEquals("ZX same", "Third", sheet.get("ZX"));
    }

    @Test
    public void testThatNumericCellsAreIdentifiedAndStored() {
        Spreadsheet sheet = new Spreadsheet();
        String theCell = "A";

        sheet.put(theCell, "X99"); // "Obvious" string
        assertEquals("X99", sheet.get(theCell));

        sheet.put(theCell, "14"); // "Obvious" number
        assertEquals("14", sheet.get(theCell));

        sheet.put(theCell, " 99 X"); // Whole string must be numeric
        assertEquals(" 99 X", sheet.get(theCell));

        sheet.put(theCell, " 1234 "); // Blanks ignored
        assertEquals("1234", sheet.get(theCell));

        sheet.put(theCell, " "); // Just a blank
        assertEquals(" ", sheet.get(theCell));
    }


    @Test
    public void testThatWeHaveAccessToCellLiteralValuesForEditing() {
        Spreadsheet sheet = new Spreadsheet();
        String theCell = "A";

        sheet.put(theCell, "Some string");
        assertEquals("Some string", sheet.getLiteral(theCell));

        sheet.put(theCell, " 1234 ");
        assertEquals(" 1234 ", sheet.getLiteral(theCell));

        sheet.put(theCell, "=7"); // Foreshadowing formulas:)
        assertEquals("=7", sheet.getLiteral(theCell));
    }


}
