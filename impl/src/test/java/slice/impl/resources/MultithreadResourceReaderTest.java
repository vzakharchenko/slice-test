package slice.impl.resources;

import org.springframework.core.io.Resource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import slice.impl.resources.file.FileReaderFactory;
import slice.impl.resources.file.TextFileReader;
import slice.impl.resources.parse.ParseLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 *
 */
public class MultithreadResourceReaderTest {
    MultithreadResourceReader multithreadResourceReader = new MultithreadResourceReader();
    FileReaderFactory fileReaderFactory = mock(FileReaderFactory.class);
    TextFileReader textFileReader = mock(TextFileReader.class);

    @BeforeClass
    public void beforeTest() throws Exception {
        multithreadResourceReader.setFileReaderFactory(fileReaderFactory);
        multithreadResourceReader.afterPropertiesSet();
    }

    @BeforeMethod
    public void beforeClass() {
        reset(textFileReader, fileReaderFactory);
    }

    @Test
    public void test() throws Exception {

        List<Resource> resources = new ArrayList<>();

        Resource resource1 = mock(Resource.class);
        Resource resource2 = mock(Resource.class);
        resources.add(resource1);
        resources.add(resource2);

        Map<String, AtomicInteger> map1 = new HashMap<String, AtomicInteger>() {
            {
                put("test1", new AtomicInteger(3));
                put("test2", new AtomicInteger(1));
                put("test3", new AtomicInteger(5));
            }
        };

        Map<String, AtomicInteger> map2 = new HashMap<String, AtomicInteger>() {
            {
                put("test1", new AtomicInteger(1));
                put("test2", new AtomicInteger(5));
                put("test3", new AtomicInteger(2));
                put("test4", new AtomicInteger(1));
            }
        };
        when(textFileReader.call()).thenReturn(map1).thenReturn(map2);


        when(fileReaderFactory.build(any(Resource.class), any(ParseLine.class))).thenReturn(textFileReader);

        Map<String, AtomicInteger> map = multithreadResourceReader.readResources(resources);

        assertNotNull(map);
        assertEquals(map.get("test1").get(), 4);
        assertEquals(map.get("test2").get(), 6);
        assertEquals(map.get("test3").get(), 7);
        assertEquals(map.get("test4").get(), 1);
    }
}
