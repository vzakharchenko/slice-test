package slice.impl.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import slice.api.domain.WordStatus;


/**
 *
 */
@Service
public class WordStatusServiceImpl implements WordStatusService {

    @Autowired
    private IndexWordService indexWordService;

    @Autowired
    private CountCalledService countCalledService;

    public WordStatus wordStatus(String word) {
        WordStatus wordStatus = new WordStatus();
        wordStatus.setOccursTimes(indexWordService.occursTimes(word));
        wordStatus.setCountCalled(countCalledService.countCalled(word));
        return wordStatus;
    }
}
