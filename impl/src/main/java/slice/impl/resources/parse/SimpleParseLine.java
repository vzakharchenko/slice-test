package slice.impl.resources.parse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class SimpleParseLine implements ParseLine {

    public static final String REGEX = "([^a-zA-Z0-9']+)'*\\1*";

    @Override
    public List<String> parse(String line) {
        if (StringUtils.isNotEmpty(line)) {
            String[] words = line.split(REGEX);
            if (ArrayUtils.isNotEmpty(words)) {
                return Arrays.asList(words);
            }
        }
        return Collections.emptyList();
    }
}
