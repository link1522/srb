package com.example.srb.core;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AppUrlLogger {
    private final Environment env;
    private final ApplicationContext ctx;

    public AppUrlLogger(Environment env, ApplicationContext ctx) {
        this.env = env;
        this.ctx = ctx;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logUrl() {
        boolean ssl = env.getProperty("server.ssl.enabled", Boolean.class, false);
        String scheme = ssl ? "https" : "http";

        String host = env.getProperty("server.address");
        if (!StringUtils.hasText(host) || "0.0.0.0".equals(host) || "::".equals(host)) {
            host = "localhost";
        }

        int port = -1;
        if (ctx instanceof WebServerApplicationContext) {
            WebServerApplicationContext wac = (WebServerApplicationContext) ctx;
            if (wac.getApplicationName() != null) {
                port = wac.getWebServer().getPort();
            }
        }
        if (port <= 0) {
            port = env.getProperty("local.server.port", Integer.class,
                    env.getProperty("server.port", Integer.class, 8080));
        }

        String contextPath = env.getProperty("server.servlet.context-path", "");
        if (!StringUtils.hasText(contextPath) || "/".equals(contextPath)) {
            contextPath = "";
        }

        String url = String.format("%s://%s:%d%s", scheme, host, port, contextPath);

        System.out.println();
        System.out.println(">>> Application is ready!");
        System.out.println(">>> URL: " + url);
        System.out.println(">>> Swagger: " + url + "/swagger-ui.html");
        System.out.println();
    }
}
