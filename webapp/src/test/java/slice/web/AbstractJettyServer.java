package slice.web;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 *
 */
public abstract class AbstractJettyServer {
    protected JettyServer jettyServer;

    public String getHost() {
        return "127.0.0.1";
    }

    public int getPort() {
        return 4533;
    }


    public String contextPath() {
        return "";
    }

    public JettyServer.ServerParams getServerParams() {
        return new JettyServer.ServerParams(pathToWebXml(), pathToResourceBase());
    }

    public String pathToWebXml() {
        return "src/main/webapp/WEB-INF/web.xml";
    }

    public String pathToResourceBase() {
        return "src/main/webapp/";
    }

    @BeforeClass
    public void startServer() throws Throwable {
        jettyServer = JettyServer.getInstance(getHost(), getPort(), contextPath(), getServerParams());
        assertTrue(jettyServer.isStarted());
        assertFalse(jettyServer.isFailed());
    }

}
