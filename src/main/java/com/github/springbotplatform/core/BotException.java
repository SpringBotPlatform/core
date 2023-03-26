package com.github.springbotplatform.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotException {

    public static void reg(BotEvent botEvent, Class inClass, String ErrorText) {
        Logger log = LoggerFactory.getLogger(inClass);
        log.error(ErrorText);
        botEvent.sendMessage("Нажаль виникла непередбачена помилка.\nАле наші інжинери вже працюють над її виправленням.");
    }

    public static void reg(BotEvent botEvent, Class inClass) {
        reg(botEvent, inClass, "Undefined error");
    }


    public static void reg(BotEvent botEvent, Class inClass, Exception exception) {
        reg(botEvent, inClass, "Description: " + exception.toString());
    }

    public static void reg(BotEvent botEvent, Class inClass, String ErrorText, Exception exception) {
        reg(botEvent, inClass, ErrorText + " Description: " + exception.toString());
    }
}
