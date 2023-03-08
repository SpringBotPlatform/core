package com.github.springbotplatform.core.BotUserPKG;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BotUserDataProcessing {

    private Map<Long, BotUser> cacheData = new HashMap();

    public BotUser getUser(Update update) {
        User user = null;

        if (update.hasMessage()) {
            user = update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            user = update.getCallbackQuery().getFrom();
        } else {
            //TODO Save error
            return null;
        }

        BotUser useUser = getCashUser(user);

        if (useUser == null) {
            useUser = callUserData(user);
        }
        return useUser;
    }

    private BotUser callUserData(User user) {
        // TODO connection to DB
        BotUser newUser = new BotUser(user);
        updateCashUser(newUser);
        return newUser;
    }

    private synchronized void updateCashUser(BotUser botUser) {
        if (!cacheData.containsKey(botUser.getMessengerUserID())) {
            this.cacheData.put(botUser.getMessengerUserID(), botUser);
        }
    }

    private BotUser getCashUser(User user) {
        BotUser returnInstance;
        Long userID = user.getId();
        if (cacheData.containsKey(userID)) {
            returnInstance = cacheData.get(userID);
            returnInstance.renewUser(user);
        } else {
            returnInstance = null;
        }
        return returnInstance;
    }

}
