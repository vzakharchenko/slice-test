package slice.impl.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import slice.api.EnglishWordController;
import slice.api.domain.WordStatus;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 *
 */
@ContextConfiguration(
        locations = {"classpath:englishWords.xml", "classpath:englishWordsTest.xml"})
public class EnglishWordControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EnglishWordController englishWordController;

    @Test
    public void testReadFiles() {
        WordStatus wordStatus = englishWordController.wordStatus("test1");
        assertNotNull(wordStatus);
        assertEquals(wordStatus.getCountCalled(), Integer.valueOf(1));
        assertEquals(wordStatus.getOccursTimes(), Integer.valueOf(4));

        wordStatus = englishWordController.wordStatus("test1");
        assertNotNull(wordStatus);
        assertEquals(wordStatus.getCountCalled(), Integer.valueOf(2));
        assertEquals(wordStatus.getOccursTimes(), Integer.valueOf(4));

        wordStatus = englishWordController.wordStatus("test2");
        assertNotNull(wordStatus);
        assertEquals(wordStatus.getCountCalled(), Integer.valueOf(1));
        assertEquals(wordStatus.getOccursTimes(), Integer.valueOf(2));


        wordStatus = englishWordController.wordStatus("test3");
        assertNotNull(wordStatus);
        assertEquals(wordStatus.getCountCalled(), Integer.valueOf(1));
        assertEquals(wordStatus.getOccursTimes(), Integer.valueOf(1));

        wordStatus = englishWordController.wordStatus("test4");
        assertNotNull(wordStatus);
        assertEquals(wordStatus.getCountCalled(), Integer.valueOf(1));
        assertEquals(wordStatus.getOccursTimes(), Integer.valueOf(1));

        wordStatus = englishWordController.wordStatus("someWord");
        assertNotNull(wordStatus);
        assertEquals(wordStatus.getCountCalled(), Integer.valueOf(1));
        assertEquals(wordStatus.getOccursTimes(), Integer.valueOf(0));
    }
}
