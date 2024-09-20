package com.example.ytubedown;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfiguration {

    @Bean
    @SneakyThrows
    public TelegramBot telegramBot(@Value("${bot_token}") String botToken, TelegramBotsApi telegramBotsApi, YouTubeDownService youTubeDownService) {

        TelegramBot telegramBot = new TelegramBot(new DefaultBotOptions(), botToken, youTubeDownService);
        telegramBotsApi.registerBot(telegramBot);
        return telegramBot;
    }

    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

}
