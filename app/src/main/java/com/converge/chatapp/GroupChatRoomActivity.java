package com.converge.chatapp;

import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class GroupChatRoomActivity extends BaseChatRoomActivity {

    @Override
    protected int contentLayoutResId() {
        return R.layout.activity_group_chat_room;
    }

    @Override
    protected String fallbackChatTitle() {
        return getString(R.string.default_group_title);
    }

    @Override
    protected void bindHeaderExtras() {
        TextView statusSubtitle = findViewById(R.id.statusSubtitle);
        statusSubtitle.setText(getString(R.string.group_participant_count, 12));
    }

    @Override
    protected void populateSeedMessages(@NonNull ArrayList<MessageItem> out) {
        out.addAll(buildGroupMessages());
    }

    private List<MessageItem> buildGroupMessages() {
        List<MessageItem> items = new ArrayList<>();
        items.add(new MessageItem(MessageItem.TYPE_GROUP_RECEIVED, "Maya", "Let's use the new purple palette across all chat surfaces.", "9:08 PM"));
        items.add(new MessageItem(MessageItem.TYPE_GROUP_RECEIVED, "Aarav", "Agreed. The card style already feels much cleaner.", "9:09 PM"));
        items.add(new MessageItem(MessageItem.TYPE_SENT, "You", "I added a dedicated group chat room so we can preview the full flow.", "9:12 PM"));
        items.add(new MessageItem(MessageItem.TYPE_GROUP_RECEIVED, "Riya", "Perfect. Next step is wiring it to real data.", "9:13 PM"));
        return items;
    }
}
