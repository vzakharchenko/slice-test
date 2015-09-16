package slice.impl.resources.file;

import org.springframework.core.io.Resource;
import slice.impl.resources.parse.ParseLine;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public abstract class TextFileReader implements Callable<Map<String, AtomicInteger>> {

    private Resource resource;

    private ParseLine parseLine;

    protected TextFileReader(Resource resource, ParseLine parseLine) {
        this.resource = resource;
        Objects.requireNonNull(parseLine, "parse line is null");
        this.parseLine = parseLine;
    }

    @Override
    public Map<String, AtomicInteger> call() throws Exception {
        File file = resource.getFile();
        if (!file.exists()) {
            throw new IllegalStateException("File " + file + " is not exists");
        }
        return readFromFile(file);
    }

    public abstract Map<String, AtomicInteger> readFromFile(File file);

    protected List<String> parseLine(String line) {
        return parseLine.parse(line);
    }
}
