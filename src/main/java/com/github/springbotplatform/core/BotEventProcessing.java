package com.github.springbotplatform.core;

import com.github.springbotplatform.core.BotUserPKG.BotUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotEventProcessing extends Thread {
    private BotEvent botEvent;
    private BotCore botCore;
    private BotUser botUser;

    public BotEventProcessing(BotCore botCore, BotEvent botEvent) {
        this.botEvent = botEvent;
        this.botCore = botCore;
    }

    @Override
    public void run() {

        try {
            if (this.botEvent.isClickEvent()) {
                BotEventActionInterface getClickEvent = this.botEvent.getClickEvent();
                if (getClickEvent != null) {
                    getClickEvent.action(botEvent);
                }
            } else {
                var menuName = this.botEvent.getMenuName();
                if (menuName != null) {
                    BotEventActionInterface actionMenu = this.botCore.getMenu((String) menuName);

                    if (actionMenu != null) {
                        actionMenu.action(this.botEvent);
                    } else {
                        BotExclusion.reg(botEvent, "Нажаль меню '" + menuName + "' недоступне.", "Suspicious activities. Use unknown menu command.");
                    }
                } else {
                    //TODO create message processor
                    botEvent.sendMessage("Hi-Hi-Hi");
                }
            }
        } catch (TelegramApiException e) {
            BotException.reg(botEvent, this.getClass(), e);
        } catch (InterruptedException e) {
            BotException.reg(botEvent, this.getClass(), e);
        } catch (Exception e) {
            BotException.reg(botEvent, this.getClass(), e);
        }
    }
}
