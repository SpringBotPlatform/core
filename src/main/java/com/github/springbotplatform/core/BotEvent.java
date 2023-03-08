package com.github.springbotplatform.core;

import com.github.springbotplatform.core.BotUserPKG.BotUser;
import com.github.springbotplatform.core.BotUserPKG.BotUserInterface;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

public class BotEvent implements BotUserInterface {

    Update update;
    BotCore botCore;
    BotUser botUser;

    public BotEvent(BotCore bot, Update update) {
        this.update = update;
        this.botCore = bot;
        this.botUser = botCore.getBotUserDataProcessing().getUser(this.update);
    }

    //    public Update getUpdate() {
//        return tgUpdate;
//    }
//    public Boolean hasException() {
//        return this.tgException;
//    }
//    public TelegramLongPollingBot getBot() {
//        return botProcessor.getBot();
//    }
    public Boolean hasMessage() {
        return update.hasMessage();
    }
    public String getText() {
        return update.getMessage().getText();
//        return this.tgMessageText;
    }

    public void sendMessage(String textToSend) {
        botCore.sendMessage(update.getMessage().getChatId(), textToSend);
    }

    public void sendMessage(String textToSend, List<Map<String, String>> buttonListMap) {
        botCore.sendMessage(update.getMessage().getChatId(), textToSend, buttonListMap);
    }

    public String getMenuName() {

        String testMessage = this.getText();
        if (testMessage.startsWith("/")) {
            return testMessage.substring(1).toLowerCase();
        } else {
            return null;
        }
    }

    @Override
    public BotUser getUser() {
        return botUser;
    }

    @Override
    public String getMessengerUserFirstName() {
        return botUser.getMessengerUserFirstName();
    }

    @Override
    public Long getMessengerUserID() {
        return botUser.getMessengerUserID();
    }
}
