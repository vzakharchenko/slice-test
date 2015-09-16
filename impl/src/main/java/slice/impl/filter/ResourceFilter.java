package slice.impl.filter;

import org.springframework.core.io.Resource;

/**
 *
 */
public interface ResourceFilter {
    public boolean filter(Resource resource);
}
