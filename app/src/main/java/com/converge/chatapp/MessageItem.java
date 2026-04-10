package com.converge.chatapp;

public class MessageItem {
    public static final int TYPE_SENT = 0;
    public static final int TYPE_RECEIVED = 1;
    public static final int TYPE_GROUP_RECEIVED = 2;

    private final int type;
    private final String sender;
    private final String message;
    private final String time;

    public MessageItem(int type, String sender, String message, String time) {
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
