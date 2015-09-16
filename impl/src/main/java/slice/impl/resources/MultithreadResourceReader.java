package slice.impl.resources;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import slice.impl.resources.file.FileReaderFactory;
import slice.impl.resources.file.FileReaderFactoryImpl;
import slice.impl.resources.parse.ParseLine;
import slice.impl.resources.parse.SimpleParseLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class MultithreadResourceReader implements TextResourceReader, InitializingBean,DisposableBean {

    private Integer threadCount = 15;

    private ExecutorService executorService;

    private ParseLine parseLine = new SimpleParseLine();

    private FileReaderFactory fileReaderFactory = new FileReaderFactoryImpl();

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public void setParseLine(ParseLine parseLine) {
        this.parseLine = parseLine;
    }

    public void setFileReaderFactory(FileReaderFactory fileReaderFactory) {
        this.fileReaderFactory = fileReaderFactory;
    }

    @Override
    public Map<String, AtomicInteger> readResources(List<Resource> resources) throws Exception {
        Map<String, AtomicInteger> atomicIntegerMap = new HashMap<>();

        List<Future<Map<String, AtomicInteger>>> futures = new ArrayList<>();

        for (Resource resource : resources) {
            Future<Map<String, AtomicInteger>> future = executorService.submit(fileReaderFactory.build(resource, parseLine));
            futures.add(future);
        }

        for (Future<Map<String, AtomicInteger>> future : futures) {
            Map<String, AtomicInteger> integerMap = future.get();
            if (integerMap != null && integerMap.size() > 0) {
                for (Map.Entry<String, AtomicInteger> entry : integerMap.entrySet()) {
                    String word = StringUtils.lowerCase(entry.getKey());
                    AtomicInteger atomicInteger = atomicIntegerMap.putIfAbsent(word, new AtomicInteger(0));
                    if (atomicInteger == null) {
                        atomicInteger = atomicIntegerMap.get(word);
                    }
                    atomicInteger.getAndAdd(entry.getValue().get());
                }
            }
        }

        return atomicIntegerMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.MINUTES);
    }
}
