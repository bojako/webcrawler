package com.mb;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static com.mb.WebBrowserTest.WebServer.Builder.aWebServer;
import static org.assertj.core.api.Assertions.assertThat;

public class WebBrowserTest {

    @Test
    public void should_read_html_page_for_a_valid_url() throws IOException {
        // Given
        WebServer webServer = aWebServer()
                .listeningOnPort(8001)
                .returningContent("<html><head></head><body><h1>Hi, there!</h1></body></html>")
                .build();
        webServer.start();

        // When
        WebBrowser webBrowser = new WebBrowser();
        String htmlContent = webBrowser.openUrl("http://localhost:8001");

        // Then
        assertThat(htmlContent).isEqualTo("<html><head></head><body><h1>Hi, there!</h1></body></html>");

        webServer.stop();
    }

    @Test
    // TODO Initial version is pretty dumb; it does not return any kind of error message
    public void should_ignore_page_that_cant_be_opened() throws IOException {
        // When
        WebBrowser webBrowser = new WebBrowser();
        String htmlContent = webBrowser.openUrl("http://this.is.not.valid");

        // Then
        assertThat(htmlContent).isEmpty();
    }


    static class WebServer {

        private final int portNumber;
        private final String content;

        private HttpServer httpServer;

        public WebServer(int portNumber, String content) {
            this.portNumber = portNumber;
            this.content = content;
        }

        public void start() throws IOException {
            httpServer = HttpServer.create(new InetSocketAddress(portNumber), 0);
            httpServer.createContext("/", httpExchange -> {
                OutputStream os = httpExchange.getResponseBody();
                httpExchange.sendResponseHeaders(200, content.length());
                os.write(content.getBytes());
                os.close();
            });

            httpServer.start();
        }

        public void stop() {
            httpServer.stop(0);
        }

        static class Builder {
            private int portNumber = 6451;
            private String content = "default content";

            private Builder() {
            }

            public static Builder aWebServer() {
                return new Builder();
            }

            public Builder listeningOnPort(int portNumber) {
                this.portNumber = portNumber;
                return this;
            }

            public Builder returningContent(String content) {
                this.content = content;
                return this;
            }

            public WebServer build() {
                return new WebServer(portNumber, content);
            }
        }
    }

}
