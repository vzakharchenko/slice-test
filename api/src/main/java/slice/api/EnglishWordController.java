package slice.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import slice.api.domain.WordStatus;

/**
 *
 */
@RequestMapping("/english")
public interface EnglishWordController {

    @RequestMapping(value = "/word/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    WordStatus wordStatus(@RequestParam("word") String word);
}
