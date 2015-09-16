package slice.impl.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import slice.api.EnglishWordController;
import slice.api.domain.WordStatus;
import slice.impl.spring.services.WordStatusService;

/**
 *
 */
@RestController
public class EnglishWordControllerImpl implements EnglishWordController {


    @Autowired
    private WordStatusService wordStatusService;

    public
    @ResponseBody
    WordStatus wordStatus(@RequestParam("word") String word) {
        return wordStatusService.wordStatus(word);
    }
}
