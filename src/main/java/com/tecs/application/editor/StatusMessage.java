package com.tecs.application.editor;

public final class StatusMessage {

    private String message = "";
    private long timestamp;
    
    public void update(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String currentMessage() {
        if(message.isBlank()){
            return "";
        }

        long age = System.currentTimeMillis() - timestamp;

        if(age > EditorConstants.STATUS_MESSAGE_TIMEOUT_MS) {
            return "";
        }
        return message;
    }
}
