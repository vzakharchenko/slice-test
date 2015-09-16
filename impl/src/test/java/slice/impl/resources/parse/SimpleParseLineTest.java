package slice.impl.resources.parse;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 *
 */
public class SimpleParseLineTest {
    private ParseLine parseLine = new SimpleParseLine();

    @Test
    public void test1() {
        List<String> parse = parseLine.parse("  \"test one two se, fg");
        assertNotNull(parse);
        assertEquals(parse.size(), 6);
        assertTrue(parse.contains("test"));
        assertTrue(parse.contains("one"));
        assertTrue(parse.contains("two"));
        assertTrue(parse.contains("se"));
        assertTrue(parse.contains("fg"));
    }

    @Test
    public void test2() {
        List<String> parse = parseLine.parse("\n" +
                "                              1896\n" +
                "\n" +
                "                          ****     ****\n");
        assertNotNull(parse);
        assertTrue(parse.contains("1896"));

    }
}
