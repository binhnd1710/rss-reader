package com.example.btl.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    private static final String PATTERN = "(<img.*?/>)|(<a.*?/a>)(</br>)";
    private static final String PATTERN1 = "src\\s*=\\s*\"(.+?)\"";
    private static final String REPLACEMENT = "";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN, Pattern.
            CASE_INSENSITIVE);
    private static final Pattern COMPILED_PATTERN1 = Pattern.compile(PATTERN1, Pattern.
            CASE_INSENSITIVE);

    public static String replaceMatches(String html) {
        Matcher matcher = COMPILED_PATTERN.matcher(html);
        return matcher.replaceAll(REPLACEMENT);
    }

    public static String findImage(String html) {
        Matcher matcher1 = COMPILED_PATTERN1.matcher(html);
        while (matcher1.find()) {
            String url = matcher1.group(1);
            return url;
        }
        return "not found";
    }
}
