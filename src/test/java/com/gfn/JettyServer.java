package com.gfn;

import no.gfn.CustomWebSocketServlet;
import no.gfn.Twitter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(CustomWebSocketServlet.class, "/ws");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/webapp");
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{handler, resourceHandler});
        server.setHandler(handlers);

        server.start();

        new Twitter().fetchTweets();
    }
}