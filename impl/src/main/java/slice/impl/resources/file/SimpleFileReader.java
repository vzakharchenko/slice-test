package slice.impl.resources.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import slice.impl.resources.parse.ParseLine;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class SimpleFileReader extends TextFileReader {


    protected SimpleFileReader(Resource resource, ParseLine parseLine) {
        super(resource, parseLine);
    }

    @Override
    public Map<String, AtomicInteger> readFromFile(File file) {
        Map<String, AtomicInteger> wordCountMap = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {

                List<String> words = parseLine(line);

                if (!CollectionUtils.isEmpty(words)) {
                    for (String word : words) {
                        if (StringUtils.isNotEmpty(word)) {
                            word = StringUtils.lowerCase(word);
                            AtomicInteger readWord = wordCountMap.putIfAbsent(word, new AtomicInteger(0));
                            if (readWord == null) {
                                readWord = wordCountMap.get(word);
                            }
                            readWord.incrementAndGet();
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            IOUtils.closeQuietly(br);
        }
        return wordCountMap;
    }

}
