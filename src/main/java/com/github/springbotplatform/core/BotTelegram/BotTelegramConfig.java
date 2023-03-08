package com.github.springbotplatform.core.BotTelegram;

//import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("general.BotTelegram.properties")
public class BotTelegramConfig {

    @Value("${bot.telegram.name}")
    String botName = "NewsLineTestBot";

    @Value("${bot.telegram.token}")
    String token = "6189362924:AAH6fWZjAYSicjRNRAv6mGh76B6ku9xDvhA";

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }
}
