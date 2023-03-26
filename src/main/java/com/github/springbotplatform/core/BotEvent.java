package com.github.springbotplatform.core;

import com.github.springbotplatform.core.BotUserPKG.BotUser;
import com.github.springbotplatform.core.BotUserPKG.BotUserInterface;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

public class BotEvent implements BotUserInterface {

    Update update;
    BotCore botCore;
    BotUser botUser;
    Integer messageID;
    Boolean clickEvent;

    public BotEvent(BotCore bot, Update update) {
        this.update = update;
        this.botCore = bot;
        this.botUser = botCore.getBotUserDataProcessing().getUser(this.update);
        // ------ ------ ------ ------
        if (update.hasCallbackQuery()) {
            clickEvent = true;
            messageID = update.getCallbackQuery().getMessage().getMessageId();
        } else if (update.hasMessage()) {
            clickEvent = false;
            messageID = null;
        }
        // ------ ------ ------ ------
    }
    public Boolean hasMessage() {
        return update.hasMessage();
    }
    public String getText() {
        return update.getMessage().getText();
//        return this.tgMessageText;
    }

    public void sendEditMessage(String messageToSend) {
        botCore.sendEditMessage(
                botUser.getMessengerUserID(),
                messageID,
                messageToSend);
    }

    public void sendEditMessage(String messageToSend, Map<String, String> buttonListMap) {
        botCore.sendEditMessage(
                botUser.getMessengerUserID(),
                messageID,
                messageToSend,
                buttonListMap);
    }

    public void sendEditMessage(String messageToSend, List<TreeMap<String, String>> buttonListMap) {
        botCore.sendEditMessage(
                botUser.getMessengerUserID(),
                messageID,
                messageToSend,
                buttonListMap);
    }

    public void sendMessage(String textToSend) {
        botCore.sendMessage(update.getMessage().getChatId(), textToSend);
    }

    public void sendMessage(String textToSend, List<TreeMap<String, String>> buttonListMap) {
        botCore.sendMessage(update.getMessage().getChatId(), textToSend, buttonListMap);
    }

    /**
     * Send text message with 1 dimension buttons
     * @param textToSend - text of message
     * @param buttonMap - 1 dimension button on message
     */
    public void sendMessage(String textToSend, Map<String, String> buttonMap) {
        botCore.sendMessage(update.getMessage().getChatId(), textToSend, buttonMap);
    }

    public String getMenuName() {

        if (!update.hasCallbackQuery()) {
            String testMessage = this.getText();
            if (testMessage.startsWith("/")) {
                return testMessage.substring(1).toLowerCase();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Boolean isClickEvent() {
        return this.clickEvent;
    }

    public BotEventClickStructure getClickEventStructure() {
        if (update.hasCallbackQuery()) {
            String callbackQueryData = update.getCallbackQuery().getData();
            String callMenuID;
            String callSubMenuID;
            String callProcessorID;
            String callStepID;
            String callKeyEnding;
            String callBody;

            String[] eventClickName = callbackQueryData.split("_");
            if (eventClickName.length >= 2) {
                String eventClickID = eventClickName[0];
                if (eventClickID.length() >= 6) {
                    callMenuID = eventClickID.substring(0,2);
                    callSubMenuID = eventClickID.substring(2,4);
                    callProcessorID = callMenuID + callSubMenuID;
                    callStepID = eventClickID.substring(4,6);
                    callKeyEnding = eventClickID.substring(6);
                } else {
                    // TODO save log error
                    return null;
                }
                callBody = eventClickName[1];
            } else {
                // TODO save log error
                return null;
            }

            return new BotEventClickStructure(callMenuID, callSubMenuID, callStepID, callProcessorID, callKeyEnding, callBody);
        } else {
            return null;
        }
    }

    public BotEventActionInterface getClickEvent () {

        BotEventClickStructure clickEventStructure = getClickEventStructure();

        if (clickEventStructure != null ) {
            return botCore.getClickEvent(clickEventStructure.callProcessorID);
        } else {
            BotException.reg(this, this.getClass(), "Unknown callProcessID: " + clickEventStructure.callProcessorID);
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

    /**
     * Add to each key of button key prefix.
     * '#' - in start is special symbol which say than key not need to change
     * @param ButtonTreeMap
     * @param MENU_ID
     * @param SUB_MENU_ID
     * @param level
     * @return
     */
    public static List<TreeMap<String, String>> getButtonStructure(List<TreeMap<String, String>> ButtonTreeMap, String MENU_ID, String SUB_MENU_ID, int level) {
        List<TreeMap<String, String>> newTreeMap = new ArrayList<>();

        for (TreeMap<String, String> stringStringTreeMap : ButtonTreeMap) {
            TreeMap<String, String> treeRow = new TreeMap();
            for (Map.Entry<String, String> entry : stringStringTreeMap.entrySet()) {
                String entryKey = entry.getKey();

                if (entryKey.length() > 0) {
                    if (entryKey.substring(0,1).equals("#")) {
                        treeRow.put(String.format("%s", entryKey.substring(1)), entry.getValue());
                    } else {
                        treeRow.put(String.format("%s%s%02d_%s",MENU_ID.toUpperCase(), SUB_MENU_ID.toUpperCase(), level, entryKey), entry.getValue());
                    }
                } else {
                    //TODO throw exception
                }
            }
            newTreeMap.add(treeRow);
        }
        return newTreeMap;
    }

}
