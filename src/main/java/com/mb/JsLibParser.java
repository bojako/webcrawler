package com.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsLibParser {

    public JsLibParser() {
    }

    public List<String> parse(String content) {
        List<String> allLibraries = new ArrayList<>();

        Pattern scriptPattern = Pattern.compile("<[\\s]*script[\\s]*type[\\s]*=[\\s]*\"text/javascript\"[\\s]*src[\\s]*=[\\s]*\"([^\"]+)\"");
        Pattern filePattern = Pattern.compile("([^/]+)\\.js");

        Matcher scriptMatcher = scriptPattern.matcher(content);
        while (scriptMatcher.find()) {
            Matcher fileMatcher = filePattern.matcher(scriptMatcher.group(1));
            if (fileMatcher.find()) {
                allLibraries.add(fileMatcher.group(1));
            }
        }

        return allLibraries;
    }
}
