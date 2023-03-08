package com.github.springbotplatform.core;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@FunctionalInterface
public
interface BotEventActionInterface {
    void action(BotEvent t) throws TelegramApiException, InterruptedException;
}
