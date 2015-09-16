package slice.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;
import slice.api.domain.WordStatus;

import static org.testng.Assert.*;

/**
 *
 */
public class IntegrationTest extends AbstractJettyServer {


    @Test(enabled = true)
    public void testStartApplication() throws JettyServer.JettyServerException {
        assertTrue(jettyServer.isStarted());
    }


    @Test(enabled = true)
    public void testCheckWord() throws JettyServer.JettyServerException {
        WordStatus wordStatus = wordStatusClient("test");
        Integer countCalled = wordStatus.getCountCalled();
        Integer occursTimes = wordStatus.getOccursTimes();
        assertNotNull(wordStatus);
        wordStatus = wordStatusClient("test");
        assertEquals(wordStatus.getCountCalled(), ++countCalled);
        assertEquals(wordStatus.getOccursTimes(), occursTimes);
    }

    private WordStatus wordStatusClient(String word) {
        String uri = "http://" + getHost() + ":" + getPort() + "/" + contextPath() + "english/word/status?word=" + word;
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WordStatus> response = restTemplate
                .getForEntity(
                        uri,
                        WordStatus.class);
        return response.getBody();
    }


}
