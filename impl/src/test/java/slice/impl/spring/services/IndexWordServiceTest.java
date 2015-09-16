package slice.impl.spring.services;

import org.testng.annotations.Test;
import slice.impl.resources.MultithreadResourceReader;

import static org.testng.Assert.assertEquals;

/**
 *
 */
public class IndexWordServiceTest {


    @Test
    public void test() throws Exception {
        IndexWordServiceImpl indexWordService = new IndexWordServiceImpl();
        indexWordService.setPathToResources("classpath:files/*"); //read files text.txt,text2.txt
        MultithreadResourceReader resourceReader = new MultithreadResourceReader();
        indexWordService.setTextResourceReader(resourceReader);
        resourceReader.afterPropertiesSet();
        indexWordService.afterPropertiesSet();
        Integer test1 = indexWordService.occursTimes("test1");
        Integer test2 = indexWordService.occursTimes("test2");
        Integer test3 = indexWordService.occursTimes("test3");
        Integer test4 = indexWordService.occursTimes("test4");
        assertEquals(test1, Integer.valueOf(4));
        assertEquals(test2, Integer.valueOf(2));
        assertEquals(test3, Integer.valueOf(1));
        assertEquals(test4, Integer.valueOf(1));
    }
}
