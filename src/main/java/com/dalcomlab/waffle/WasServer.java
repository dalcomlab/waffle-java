package com.dalcomlab.waffle;

import com.dalcomlab.waffle.application.Application;
import com.dalcomlab.waffle.dispatch.ChannelledRequest;
import com.dalcomlab.waffle.dispatch.Http;
import com.dalcomlab.waffle.dispatch.HttpRequest;
import com.dalcomlab.waffle.dispatch.HttpResponse;
import com.dalcomlab.waffle.connector.Connector;
import com.dalcomlab.waffle.server.Server;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class WasServer implements Server {

    public static final String WEBAPPS_ROOT = Paths.get("").toAbsolutePath().toString() + "/webapps/";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    Map<String, Application> applications = new HashMap<>();
    List<Connector> connectors = new LinkedList<>();

    private int totalRequestCount = 0;

    public WasServer() {
        System.out.println("starting..............");
        String root = WEBAPPS_ROOT;
        System.out.println("* root : " + root);

        File rootFile = new File(root);
        if (rootFile != null) {
            File[] webapps = rootFile.listFiles();
            if (webapps != null) {
                for (File webapp : webapps) {
                    if (webapp.isDirectory() || webapp.getName().endsWith(".war")) {
                        try {
                            String path = webapp.getCanonicalPath();
                            System.out.println("context : " + path);
                            String context = webapp.getName();
                            if (context.endsWith(".war")) {
                                context = context.substring(0, context.length() - 4);
                            }
                            applications.put(context, new Application(context, path));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void init() {
        applications.forEach((key, value) -> value.init());
        connectors.forEach(connector -> connector.init());
    }

    @Override
    public void start() {
        applications.forEach((key, value) -> value.start());
        connectors.forEach(connector -> connector.start());
    }

    @Override
    public void stop() {
        applications.forEach((key, value) -> value.stop());
        connectors.forEach(connector -> connector.stop());
    }

    @Override
    public void addConnector(Connector connector) {
        if (connector != null) {
            connector.setServer(this);
            connectors.add(connector);
        }
    }

    @Override
    public Connector[] getConnectors() {
        return connectors.stream().toArray(Connector[]::new);
    }

    public Application getApplication(String name) {
        return applications.get(name);
    }

    @Override
    public void dispatch(Connector connector, URI uri, HttpRequest request, HttpResponse response) {

        String path = uri.getPath();
        String queryString = uri.getQuery();

        if (queryString != null && queryString.length() != 0) {
            path += "?" + queryString;
        }

        System.out.println(ANSI_YELLOW);
        System.out.println("* client request [" + (++totalRequestCount) + "] : " + path);
        System.out.println(ANSI_RESET);


        final String finalPath = path;
        String[] components = path.split("/");
        if (components.length > 1) {
            String context = components[1];
            Application application = getApplication(context);

            if (application != null) {
                Runnable service = () -> {
                    Http http = new Http(application.getContext(), request, response);
                    http.initialize();
                    try {
                        application.service(finalPath, new ChannelledRequest(application.getContext(), request), response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    http.destroy();
                };

                new Thread(service).start();

            } else {
                // not found...
            }
        }
    }


}
