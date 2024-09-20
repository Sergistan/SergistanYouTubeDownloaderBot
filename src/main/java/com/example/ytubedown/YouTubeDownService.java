package com.example.ytubedown;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YouTubeDownService {

    private final YouTubeLinkExtractor youTubeLinkExtractor;

    public File download(String url, Quality quality) {

        String videoId = youTubeLinkExtractor.extractVideoId(url);
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo request = new RequestVideoInfo(videoId);

        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();
        VideoDetails details = video.details();

        List<VideoWithAudioFormat> videoWithAudioFormats = video.videoWithAudioFormats();
        videoWithAudioFormats.forEach(it -> {
            System.out.println(it.videoQuality() + " : " + it.url());
        });

        Format format = videoWithAudioFormats.get(quality.getQuality());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        RequestVideoFileDownload requestVideoFileDownload = new RequestVideoFileDownload(format)
                .saveTo(new File("media"))
                .renameTo(details.title() + " Автор: " + details.author() + " " + LocalDateTime.now().format(formatter))
                .overwriteIfExists(false)
                .callback(new YoutubeProgressCallback<>() {
                    @Override
                    public void onDownloading(int progress) {
                        System.out.printf("Downloaded %d%%\n", progress);
                    }

                    @Override
                    public void onFinished(File videoInfo) {
                        System.out.println("Finished file download: " + videoInfo);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error: " + throwable.getLocalizedMessage());
                    }
                })
                .async();

        Response<File> fileResponse = downloader.downloadVideoFile(requestVideoFileDownload);
        return fileResponse.data();
    }
}
