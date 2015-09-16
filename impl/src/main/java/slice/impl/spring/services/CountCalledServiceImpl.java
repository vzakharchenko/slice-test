package slice.impl.spring.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@Service
public class CountCalledServiceImpl implements CountCalledService {

    private Map<String, AtomicInteger> callTimeMap = new ConcurrentHashMap<String, AtomicInteger>();

    public Integer countCalled(String word) {
        String key = StringUtils.lowerCase(word);
        AtomicInteger integer = callTimeMap.putIfAbsent(key, new AtomicInteger(0));
        if (integer == null) {
            integer = callTimeMap.get(key);
        }
        int count = integer.incrementAndGet();
        return count;
    }
}
