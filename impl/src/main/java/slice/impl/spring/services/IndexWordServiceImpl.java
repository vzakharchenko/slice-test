package slice.impl.spring.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import slice.impl.filter.ResourceFilter;
import slice.impl.filter.TextResourceFilter;
import slice.impl.resources.TextResourceReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class IndexWordServiceImpl implements IndexWordService, InitializingBean {

    private String pathToResources;


    private ResourceFilter resourceFilter = new TextResourceFilter();


    private Map<String, AtomicInteger> occursTimeMap;


    private TextResourceReader textResourceReader;

    public void setPathToResources(String pathToResources) {
        this.pathToResources = pathToResources;
    }

    public void setResourceFilter(ResourceFilter resourceFilter) {
        this.resourceFilter = resourceFilter;
    }

    public void setTextResourceReader(TextResourceReader textResourceReader) {
        this.textResourceReader = textResourceReader;
    }

    protected void indexFile(List<Resource> resources) throws Exception {
        occursTimeMap = textResourceReader.readResources(resources);
    }

    @Override
    public Integer occursTimes(String word) {
        AtomicInteger atomicInteger = occursTimeMap.getOrDefault(StringUtils.lowerCase(word), new AtomicInteger(0));
        return atomicInteger.get();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(new PathMatchingResourcePatternResolver()).getResources(pathToResources);
        List<Resource> resourceList = new ArrayList<>();
        for (Resource resource : resources) {
            if (resourceFilter == null || (isFiltered(resource))) {
                resourceList.add(resource);
            }
        }
        indexFile(resourceList);
    }

    private boolean isFiltered(Resource resource) {
        return (resourceFilter != null && resourceFilter.filter(resource));
    }
}
