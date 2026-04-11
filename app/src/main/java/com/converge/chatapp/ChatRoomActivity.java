package com.converge.chatapp;

import android.view.Window;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends BaseChatRoomActivity {

    @Override
    protected int contentLayoutResId() {
        return R.layout.activity_chat_room;
    }

    @Override
    protected String fallbackChatTitle() {
        return getString(R.string.default_chat_title);
    }

    @Override
    protected void populateSeedMessages(@NonNull ArrayList<MessageItem> out) {
        out.addAll(buildDirectMessages());
    }



    private List<MessageItem> buildDirectMessages() {
        List<MessageItem> items = new ArrayList<>();
        items.add(new MessageItem(MessageItem.TYPE_RECEIVED, "hello", "Hey, are we still on for the UI review tonight?", "8:42 PM"));
        items.add(new MessageItem(MessageItem.TYPE_SENT, "", "Yes, I just wrapped the new chat screens.", "8:43 PM"));
        items.add(new MessageItem(MessageItem.TYPE_RECEIVED, "", "Nice. Send the latest build when you're ready.", "8:44 PM"));
        items.add(new MessageItem(MessageItem.TYPE_SENT, "", "Uploading now. I also created a dedicated chat room screen.", "8:45 PM"));
        return items;
    }
}
