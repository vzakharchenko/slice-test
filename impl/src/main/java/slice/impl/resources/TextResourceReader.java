package slice.impl.resources;

import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public interface TextResourceReader {

    public Map<String, AtomicInteger> readResources(List<Resource> resources) throws Exception;
}
