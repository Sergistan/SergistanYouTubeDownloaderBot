package com.example.ytubedown;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private final YouTubeDownService youTubeDownService;

    public TelegramBot(DefaultBotOptions options, String botToken, YouTubeDownService youTubeDownService) {
        super(options, botToken);
        this.youTubeDownService = youTubeDownService;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (text.equals("/start")) {
                String userName = update.getMessage().getChat().getUserName();
                String startText = """
                        Добро пожаловать в бот, %s!
                                        
                        Здесь Вы сможете скачать видео с YouTube.
                                        
                        Для этого просто отправьте ссылку на видео:
                                        
                        Дополнительные команды:
                        /help - получение справки
                        """;
                String formattedText = String.format(startText, userName);
                SendMessage sendMessage = new SendMessage(chatId.toString(), formattedText);
                execute(sendMessage);
            } else {
                File download = youTubeDownService.download(text, Quality.DEFAULT);
                InputFile inputFile = new InputFile(download);

                SendVideo sendVideo = new SendVideo(chatId.toString(), inputFile);
                execute(sendVideo);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "SergistanYouTubeDownloaderBot";
    }
}
