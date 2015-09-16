package slice.impl.filter;

import org.springframework.core.io.Resource;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 *
 */
public class TextResourceFilterTest {
    private TextResourceFilter textResourceFilter = new TextResourceFilter();

    @Test
    public void test1() {
        Resource resource = mock(Resource.class);
        when(resource.getFilename()).thenReturn("test.txt");
        assertTrue(textResourceFilter.filter(resource));
        when(resource.getFilename()).thenReturn("test.test");
        assertFalse(textResourceFilter.filter(resource));
        when(resource.getFilename()).thenReturn("/test");
        assertFalse(textResourceFilter.filter(resource));
    }
}
