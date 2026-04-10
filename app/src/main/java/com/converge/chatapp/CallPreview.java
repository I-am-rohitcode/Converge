package com.converge.chatapp;

public class CallPreview {
    private final String title;
    private final String subtitle;
    private final int avatarIconRes;

    public CallPreview(String title, String subtitle, int avatarIconRes) {
        this.title = title;
        this.subtitle = subtitle;
        this.avatarIconRes = avatarIconRes;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getAvatarIconRes() {
        return avatarIconRes;
    }
}
