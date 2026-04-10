package com.converge.chatapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroupChatRoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_chat_room);

        View composerContainer = findViewById(R.id.composerContainer);
        RecyclerView recyclerView = findViewById(R.id.messagesRecyclerView);
        final int composerBottomPadding = composerContainer.getPaddingBottom();
        final int recyclerBottomPadding = recyclerView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            composerContainer.setPadding(
                    composerContainer.getPaddingLeft(),
                    composerContainer.getPaddingTop(),
                    composerContainer.getPaddingRight(),
                    Math.max(systemBars.bottom, imeInsets.bottom) + composerBottomPadding
            );
            recyclerView.setPadding(
                    recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getPaddingRight(),
                    recyclerBottomPadding
            );
            return insets;
        });

        String title = getIntent().getStringExtra("title");
        TextView titleView = findViewById(R.id.chatTitle);
        titleView.setText(title == null ? "Group chat room" : title);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<MessageItem> messages = buildGroupMessages();
        recyclerView.setAdapter(new MessageAdapter(messages));
        recyclerView.scrollToPosition(messages.size() - 1);
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
