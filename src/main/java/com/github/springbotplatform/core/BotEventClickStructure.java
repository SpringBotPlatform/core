package com.github.springbotplatform.core;

public final class BotEventClickStructure {

    public final String callMenuID;
    public final String callSubMenuID;
    public final String callProcessorID;
    public final String callStepID;
    public final String callKeyEnding;

    public final Integer callStepIDint;
    public final String eventBody;

    public BotEventClickStructure(String callMenuID, String callSubMenuID, String callStepID, String callProcessorID, String callKeyEnding,String eventBody) {
        this.callMenuID = callMenuID;
        this.callSubMenuID = callSubMenuID;
        this.callStepID = callStepID;
        this.callStepIDint = Integer.parseInt(callStepID);
        this.callKeyEnding = callKeyEnding;
        this.eventBody = eventBody;
        this.callProcessorID = callProcessorID;
    }
}




