package com.github.springbotplatform.core.BotUserPKG;

import org.telegram.telegrambots.meta.api.objects.User;

public class BotUser {

    private String langCode;
    private Long messengerId;
    private User messengerUser;

    public BotUser(User user) {
        this.messengerId = user.getId();
        this.messengerUser = user;
    }

    public void renewUser(User user) {
        this.messengerUser = user;
    }

    public Long getMessengerUserID() {
        return this.messengerId;
    }

    public String getMessengerUserFirstName() {
        return messengerUser.getFirstName();
    }


}
