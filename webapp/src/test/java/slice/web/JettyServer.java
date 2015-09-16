package slice.web;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 */
public class JettyServer {
    private static JettyServer instance;
    private static ServerParams serverParams;
    private String contextPath;
    private String host;
    private int port;
    private Server server = new Server();
    private WebAppContext webapp;
    private WebApplicationContext context;


    private JettyServer(String host, int port, String contextPath) throws JettyServerException {
        this.host = host;
        this.port = port;
        this.contextPath = contextPath;
        start();
    }

    public static synchronized JettyServer getInstance(String host, int port, String contextPath, ServerParams newServerParams) throws JettyServerException {
        if (instance == null || instance.isStopped()) {
            serverParams = newServerParams;
            instance = new JettyServer(host, port, contextPath);
        }
        return instance;
    }

    public static synchronized void stopJettyServer() throws JettyServerException {
        if (instance != null) {
            instance.stop();
            instance = null;
        }
    }

    private void setIPPort(String ip, int port) {
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(ip);
        connector.setPort(port);
        server.setConnectors(new Connector[]
                {connector});
    }

    public boolean isFailed() throws JettyServerException {
        return server.isFailed();
    }

    public boolean isStarted() throws JettyServerException {
        return server.isStarted();
    }

    public boolean isStopped() throws JettyServerException {
        return server.isStopped() || server.isStopping();
    }

    public void start() throws JettyServerException {
        if (isStarted()) return;
        try {
            webapp = new WebAppContext();
            setIPPort(host, port);
            webapp.setDescriptor(serverParams.getDescriptor());
            webapp.setResourceBase(serverParams.getResourcebase());
            webapp.setContextPath(contextPath);
            webapp.setClassLoader(Thread.currentThread().getContextClassLoader());

            server.setHandler(webapp);
            webapp.setParentLoaderPriority(true);
            server.start();
            context = WebApplicationContextUtils.getWebApplicationContext(webapp.getServletContext());
        } catch (Exception ex) {
            throw new JettyServerException(ex);
        }
    }

    public WebApplicationContext getContext() {
        return context;
    }

    public void stop() throws JettyServerException {
        try {
            server.stop();
        } catch (Exception e) {
            {
                throw new JettyServerException(e);
            }
        }

    }


    public static class JettyServerException extends Exception {

        public JettyServerException(String message) {
            super(message);
        }

        public JettyServerException(Throwable cause) {
            super(cause);
        }
    }

    public static class ServerParams {
        private static final String DESCRIPTOR = "src/main/webapp/WEB-INF/web.xml";
        private static final String RESOURCEBASE = "src/main/webapp/";
        private String descriptor = DESCRIPTOR;
        private String resourcebase = RESOURCEBASE;


        public ServerParams(String descriptor, String resourcebase) {
            this.descriptor = descriptor;
            this.resourcebase = resourcebase;
        }

        public String getDescriptor() {
            return descriptor;
        }

        public String getResourcebase() {
            return resourcebase;
        }
    }
}
