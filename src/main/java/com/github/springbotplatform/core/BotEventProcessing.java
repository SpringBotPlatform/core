package com.github.springbotplatform.core;

import com.github.springbotplatform.core.BotUserPKG.BotUser;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotEventProcessing extends Thread {
    //    private static HashMap<String, ParameterAction> callMap;
//    private static HashMap<String, ParameterAction> callQueryMap;
    private BotEvent botEvent;
    private BotCore botCore;
    private BotUser botUser;
//    private TelegramLongPollingBot bot;
//    private BotProcessorUpdateEvent botEvent;

//    public BotEventProcessing(TelegramBot telegramBot, Update update) {
//        this.update = update;
//        this.bot = telegramBot;
//    }

//    public BotEventProcessing(BotProcessorUpdateEvent botEvent) {
//        this.botEvent = botEvent;
//        this.update = botEvent.getUpdate();
//        this.bot = botEvent.getBot();
//        generateHashMap();
//        generateQueryMap();
//    }

//    public void generateHashMap() {
//        this.callMap = new HashMap();
//        this.callMap.put("start", TBotBukovelOperations::startCommandReceived);
//        this.callMap.put("test", TBotBukovelOperations::startTestBlock);
//        this.callMap.put("tt", TBotBukovelOperations::startTestBlockMarkUp);
//        this.callMap.put("pause", TBotBukovelOperations::startTestMultiSession);
//        this.callMap.put("request", TBotBukovelOperations::startTestRequest);
//        this.callMap.put("eb", TBotBukovelOperations::testEquipmentBalance);
//        this.callMap.put("squire", TBotBukovelOperations::testSquireBlock);
//    }

    public BotEventProcessing(BotCore botCore, BotEvent botEvent) {
        this.botEvent = botEvent;
        this.botCore = botCore;
//        this.botUser = botCore.
    }

//    public void generateQueryMap() {
//        this.callQueryMap = new HashMap();
//        this.callQueryMap.put("equipmentbalance", EquipmentBalanceCoreProcessor::processor);
//    }

    @Override
    public void run() {

        var menuName = this.botEvent.getMenuName();
        if (menuName != null) {
            BotEventActionInterface actionMenu = this.botCore.getMenu((String) menuName);

            if (actionMenu != null) {
                try {
                    actionMenu.action(this.botEvent);
                } catch (TelegramApiException e) {
                    //TODO save error log
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    //TODO save error log
                    throw new RuntimeException(e);
                }
            } else {
                botEvent.sendMessage("Unknown menu name: '" + menuName + "'");
            }
        } else {
            botEvent.sendMessage("Hi-Hi-Hi");
        }

//        String messageText = this.botEvent.getText();
//        if (messageText.startsWith("/")) {
//            String command = messageText.substring(1).toLowerCase();
//            ParameterAction operation = this.callMap.get(command);
//            if (operation != null) {
//                try {
//                    operation.action(this.botEvent);
//                } catch (TelegramApiException e) {
//                    throw new RuntimeException(e);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                //TODO unknown command
//            }
//        } else {
//            switch (messageText) {
//                case "Hi" -> TelegramBotOperation.sendMessage(this.botEvent, "Hi!");
//                default -> TelegramBotOperation.sendMessage(this.botEvent, "??" + messageText);
//            }
//        }




        //        botEvent.hasMessage()
        int tt = 5;

//        if (!this.botEvent.hasException()) {
//            String buttonName = null;
//            if (this.botEvent.hasMessage()) {
//                messageProcessing();
//            } else if (update.hasCallbackQuery()) {
//                CallbackQuery callbackQuery = update.getCallbackQuery();
//                String callbackQueryData = callbackQuery.getData();
//
//                buttonName = callbackQueryData.toLowerCase();
//                if (buttonName.equals("test_edit_block")) {
//                    try {
//                        TBotBukovelOperations.testSquireBlockEdit(this.botEvent);
//                    } catch (TelegramApiException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else {
//                    String[] buttonParts = buttonName.split("_");
//
//                    ParameterAction callFunction = callQueryMap.get(buttonParts[0]);
//                    if (callFunction != null) {
//                        try {
//                            callFunction.action(this.botEvent);
//                        } catch (TelegramApiException e) {
//                            throw new RuntimeException(e);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    } else {
//                        //TODO write error on action
//                    }
//                }
////                TelegramBotOperation.sendMessage(this.botEvent, buttonName);
//            }
//        }
    }

//    private void messageProcessing() {
//        String messageText = this.botEvent.getText();
//        if (messageText.startsWith("/")) {
//            String command = messageText.substring(1).toLowerCase();
//            ParameterAction operation = this.callMap.get(command);
//            if (operation != null) {
//                try {
//                    operation.action(this.botEvent);
//                } catch (TelegramApiException e) {
//                    throw new RuntimeException(e);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                //TODO unknown command
//            }
//        } else {
//            switch (messageText) {
//                case "Hi" -> TelegramBotOperation.sendMessage(this.botEvent, "Hi!");
//                default -> TelegramBotOperation.sendMessage(this.botEvent, "??" + messageText);
//            }
//        }
//    }
}
