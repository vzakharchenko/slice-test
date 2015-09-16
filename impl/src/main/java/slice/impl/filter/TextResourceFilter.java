package slice.impl.filter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

/**
 *
 */
public class TextResourceFilter implements ResourceFilter {

    public static final String TEXTFILE = "txt";

    @Override
    public boolean filter(Resource resource) {
        String extension = FilenameUtils.getExtension(resource.getFilename());
        return StringUtils.equalsIgnoreCase(extension, TEXTFILE);
    }
}
