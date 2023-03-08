package com.github.springbotplatform.core.BotTelegram;

import com.github.springbotplatform.core.BotConfig;
import com.github.springbotplatform.core.BotCore;
import com.github.springbotplatform.core.BotEventProcessing;
import com.github.springbotplatform.core.BotUserPKG.BotUserDataProcessing;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotTelegramLongPolling extends TelegramLongPollingBot {

    private BotTelegramConfig botTelegramConfig;
    private BotConfig botConfig;
    private BotCore botCore;

    public BotTelegramLongPolling(BotTelegramConfig botTelegramConfig, BotConfig botConfig, BotUserDataProcessing botUserDataProcessing) {
        this.botConfig = botConfig;
        this.botTelegramConfig = botTelegramConfig;

        this.botCore = new BotCore(botConfig, botUserDataProcessing, this);
    }

    public TelegramLongPollingBot getRegisterBot() {
        List<BotCommand> listOfCommands = new ArrayList();
        HashMap<String, String> menuMap = this.botConfig.getMenuMap();

        for (Map.Entry curEntity : menuMap.entrySet()) {
            listOfCommands.add(new BotCommand((String) curEntity.getKey(), (String) curEntity.getValue()));
        }

        if (listOfCommands.size() != 0) {
            try {
                this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
            } catch (TelegramApiException e) {
                //TODO write to log
                throw new RuntimeException(e);
            }
        }

        return this;
    }

    @Override
    public String getBotUsername() {
        return botTelegramConfig.getBotName();
    }
    @Override
    public String getBotToken() {
        return botTelegramConfig.getToken();
    }
    @Override
    public void onUpdateReceived(Update update) {

        BotEventProcessing botEventProcessing = botCore.generateProcessEvent(update);
        botEventProcessing.start();

    }
}
