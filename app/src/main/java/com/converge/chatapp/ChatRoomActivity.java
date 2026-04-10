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

public class ChatRoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_room);

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
        titleView.setText(title == null ? "Chat room" : title);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<MessageItem> messages = buildDirectMessages();
        recyclerView.setAdapter(new MessageAdapter(messages));
        recyclerView.scrollToPosition(messages.size() - 1);
    }

    private List<MessageItem> buildDirectMessages() {
        List<MessageItem> items = new ArrayList<>();
        items.add(new MessageItem(MessageItem.TYPE_RECEIVED, "", "Hey, are we still on for the UI review tonight?", "8:42 PM"));
        items.add(new MessageItem(MessageItem.TYPE_SENT, "", "Yes, I just wrapped the new chat screens.", "8:43 PM"));
        items.add(new MessageItem(MessageItem.TYPE_RECEIVED, "", "Nice. Send the latest build when you're ready.", "8:44 PM"));
        items.add(new MessageItem(MessageItem.TYPE_SENT, "", "Uploading now. I also created a dedicated chat room screen.", "8:45 PM"));
        return items;
    }
}
