package com.converge.chatapp;

import androidx.annotation.Nullable;

public class MessageItem {
    public static final int TYPE_SENT = 0;
    public static final int TYPE_RECEIVED = 1;
    public static final int TYPE_GROUP_RECEIVED = 2;
    public static final int TYPE_SENT_IMAGE = 3;
    public static final int TYPE_RECEIVED_IMAGE = 4;
    public static final int TYPE_GROUP_RECEIVED_IMAGE = 5;

    private final int type;
    private final String sender;
    private final String message;
    private final String time;
    @Nullable
    private final String imageUriString;

    /** Text message (1:1 or group sent as plain text). */
    public MessageItem(int type, String sender, String message, String time) {
        this(type, sender, message, time, null);
    }

    /** Message with optional image (uri string from content:// or file://). */
    public MessageItem(int type, String sender, String message, String time, @Nullable String imageUriString) {
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.imageUriString = imageUriString;
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

    @Nullable
    public String getImageUriString() {
        return imageUriString;
    }

    public boolean hasImage() {
        return imageUriString != null && !imageUriString.isEmpty();
    }
}
