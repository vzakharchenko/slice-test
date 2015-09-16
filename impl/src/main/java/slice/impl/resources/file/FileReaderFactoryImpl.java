package slice.impl.resources.file;

import org.springframework.core.io.Resource;
import slice.impl.resources.parse.ParseLine;

/**
 *
 */
public class FileReaderFactoryImpl implements FileReaderFactory {

    @Override
    public TextFileReader build(Resource resource, ParseLine parseLine) {
        return new SimpleFileReader(resource, parseLine);
    }
}
