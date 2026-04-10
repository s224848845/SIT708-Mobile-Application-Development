package com.example.task_51c_media__content_apps.istream.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUtil {

    public static String extractVideoId(String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        Pattern pattern = Pattern.compile(
                "(?:youtu\\.be/|youtube\\.com/watch\\?v=|youtube\\.com/embed/)([a-zA-Z0-9_-]{11})"
        );

        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}