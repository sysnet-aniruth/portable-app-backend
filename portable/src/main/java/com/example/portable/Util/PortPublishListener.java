package com.example.portable.Util;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class PortPublishListener implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int port = event.getWebServer().getPort();
        String host = "127.0.0.1";
        String content = host + ":" + port;
        try {
            // write to working dir so Electron can find it
            Files.write(Path.of("backend-port.txt"), content.getBytes());
            System.out.println("Backend started on " + content + " (written to backend-port.txt)");
        } catch (IOException e) {
            System.err.println("Failed to write backend-port.txt: " + e.getMessage());
        }
    }
}