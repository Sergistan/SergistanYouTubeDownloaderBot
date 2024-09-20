package com.example.ytubedown;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/download/")
public class YouTubeDownController {

    private final YouTubeDownService youTubeDownService;

    @GetMapping("video-with-audio")
    public ResponseEntity<?> downloadVideoWithAudioFormat(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "quality", defaultValue = "HD") Quality quality){
        File response = youTubeDownService.download(url, quality);

        if (Objects.isNull(response)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return new ResponseEntity<>(String.format("Файл %s скачен", response.getName()), HttpStatus.OK);
    }

}
