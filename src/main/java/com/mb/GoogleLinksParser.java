package com.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleLinksParser {

    public List<String> parse(String googleResultsPage) {
        List<String> links = new ArrayList<>();

        Pattern pattern = Pattern.compile("h3 class=\"r\">[\\s]*(<span.*\\[PDF\\]</span>)?[\\s]*<a[\\s]*href=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(googleResultsPage);

        while (matcher.find()) {
            links.add(matcher.group(2));
        }

        return links;
    }

}
