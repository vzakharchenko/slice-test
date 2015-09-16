package slice.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;
import slice.api.domain.WordStatus;

import static org.testng.Assert.assertNotNull;

/**
 *
 */
public class CallExternalService {


    @Test(enabled = false)
    public void test() {
        WordStatus wordStatus = wordStatusClient("test");
        assertNotNull(wordStatus);
    }

    private WordStatus wordStatusClient(String word) {
        String uri = "http://127.0.0.1:9099//slice//english/word/status?word=" + word;
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WordStatus> response = restTemplate
                .getForEntity(
                        uri,
                        WordStatus.class);
        return response.getBody();
    }
}
