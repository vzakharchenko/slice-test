package slice.impl.resources.file;

import org.springframework.core.io.Resource;
import slice.impl.resources.parse.ParseLine;

/**
 *
 */
public interface FileReaderFactory {
    public TextFileReader build(Resource resource, ParseLine parseLine);
}
