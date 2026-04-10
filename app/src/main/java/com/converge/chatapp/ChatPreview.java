package com.converge.chatapp;

public class ChatPreview {
    private final String title;
    private final String lastMessage;
    private final String time;
    private final int unreadCount;
    private final boolean isGroup;
    private final int avatarIconRes;

    public ChatPreview(String title, String lastMessage, String time, int unreadCount, boolean isGroup, int avatarIconRes) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.time = time;
        this.unreadCount = unreadCount;
        this.isGroup = isGroup;
        this.avatarIconRes = avatarIconRes;
    }

    public String getTitle() {
        return title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTime() {
        return time;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public int getAvatarIconRes() {
        return avatarIconRes;
    }
}
