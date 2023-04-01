package com.github.springbotplatform.core;

import com.github.springbotplatform.core.BotUserPKG.BotUserDataProcessing;
import com.vdurmont.emoji.EmojiParser;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.*;


@Component
public class BotCore {


    private BotConfig botConfig;
    private BotUserDataProcessing botUserDataProcessing;
    TelegramLongPollingBot botTelegram;

    public BotCore() {
    }

    public BotCore( BotConfig botConfig, BotUserDataProcessing botUserDataProcessing, TelegramLongPollingBot botTelegram) {
        this.botTelegram = botTelegram;
        this.botConfig = botConfig;
        this.botUserDataProcessing = botUserDataProcessing;
    }

    public BotUserDataProcessing getBotUserDataProcessing() {
        return botUserDataProcessing;
    }

    public BotEventProcessing generateProcessEvent(Update update) {
        return new BotEventProcessing(this, new BotEvent(this, update));

    }

    public BotEventActionInterface getClickEvent(String eventID) {
        return botConfig.getClickEvent(eventID);
    }

    public void sendEditMessage(long chatID, int messageID, String textToSend) {
        EditMessageText message = new EditMessageText();

        message.setChatId(String.valueOf(chatID));
        message.setText(textToSend);
        message.setMessageId(messageID);

        try {
            //TODO make dispatch queue
            this.botTelegram.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendEditMessage(long chatID, int messageID, String textToSend,  Map<String, String> buttonListMap) {
        EditMessageText editMessage = new EditMessageText();

        editMessage.setChatId(String.valueOf(chatID));
        editMessage.setText(textToSend);
        editMessage.setMessageId(messageID);
        attachButtons(editMessage, buttonListMap);

        try {
            //TODO make dispatch queue
            this.botTelegram.execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendEditMessage(long chatID, int messageID, String textToSend,  List<TreeMap<String, String>> buttonListMap) {
        EditMessageText editMessage = new EditMessageText();

        editMessage.setChatId(String.valueOf(chatID));
        editMessage.setText(textToSend);
        editMessage.setMessageId(messageID);
        attachButtons(editMessage, buttonListMap);

        try {
            //TODO make dispatch queue
            this.botTelegram.execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(long chatID, String textToSend) {
        int aa = 4;

        SendMessage message = new SendMessage();

        message.setChatId(String.valueOf(chatID));
        message.setText(EmojiParser.parseToUnicode(textToSend));

        try {
            //TODO make dispatch queue
            this.botTelegram.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessage(long chatID, String messageToSend, List<TreeMap<String, String>> buttonListMap) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(messageToSend);
        attachButtons(message, buttonListMap);

        try {
            //TODO make dispatch queue
            this.botTelegram.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessage(long chatID, String testToSend, Map<String, String> buttonMap) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(testToSend);

        attachButtons(message, buttonMap);

        try {
            //TODO make dispatch queue
            this.botTelegram.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void attachButtons(SendMessage message, List<TreeMap<String, String>> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> keyboardButtonRow;

        for (Map<String, String> curLine : buttons) {
            keyboardButtonRow = new ArrayList();

            for (Map.Entry<String, String> entry: curLine.entrySet()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setCallbackData(entry.getKey());
                button.setText(entry.getValue());

                keyboardButtonRow.add(button);
            }

            keyboard.add(keyboardButtonRow);
        }


        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    /**
     * Appending 1 dimension button list to message.
     * @param message
     * @param buttons
     */
    protected void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonValue);
            button.setCallbackData(buttonName);

            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    protected void attachButtons(EditMessageText message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);

            keyboard.add(Arrays.asList(button));
        }

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    protected static void attachButtons(EditMessageText message, List<TreeMap<String, String>> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> keyboardButtonRow;

        for (Map<String, String> curLine : buttons) {
            keyboardButtonRow = new ArrayList();

            for (Map.Entry<String, String> entry: curLine.entrySet()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(entry.getValue());
                button.setCallbackData(entry.getKey());

                keyboardButtonRow.add(button);
            }

//            for (String buttonName : curLine.keySet()) {
//                String buttonValue = curLine.get(buttonName);
//
//                InlineKeyboardButton button = new InlineKeyboardButton();
//                button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
//                button.setCallbackData(buttonValue);
//
//                keyboardButtonRow.add(button);
//            }

            keyboard.add(keyboardButtonRow);
        }

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    public BotEventActionInterface getMenu(String menuName) {
        BotEventActionInterface action = (BotEventActionInterface) this.botConfig.callMap.get(menuName);
        if (action == null) {
            //TODO save error
            return null;
        } else {
            return action;
        }
    }

//    public BotCore(BotConfig botConfig) {
//        this.botConfig = botConfig;
//        this.botConfig.testString = "000";
//        int t = 4;
//        this.botConfig.putCallElement("test", this);
//    }

}
