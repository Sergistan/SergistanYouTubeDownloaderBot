package com.example.ytubedown;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class YouTubeLinkExtractor {
    public String extractVideoId(String url) {
        String videoId = null;

        // Regular expression pattern to match YouTube video IDs
        Pattern pattern = Pattern.compile(
//                "(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/|youtube\\.com/watch\\?.*?&v=)?([a-zA-Z0-9_-]{11})"
//                "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
                "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F|shorts\\/|live\\/)[^#\\&\\?\\n]*"
        );
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            videoId = matcher.group();
        }

        return videoId;
    }
}
