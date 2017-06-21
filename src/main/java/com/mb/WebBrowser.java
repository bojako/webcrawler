package com.mb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebBrowser {
    public String openUrl(String urlAsString) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAsString);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
}
