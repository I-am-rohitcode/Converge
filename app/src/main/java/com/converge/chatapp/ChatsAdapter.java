package com.converge.chatapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {
    private final List<ChatPreview> items;

    public ChatsAdapter(List<ChatPreview> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatPreview item = items.get(position);
        holder.userNameText.setText(item.getTitle());
        holder.lastMessageText.setText(item.getLastMessage());
        holder.timeText.setText(item.getTime());
        holder.avatarImage.setImageResource(item.getAvatarIconRes());
        holder.avatarImage.setPadding(12, 12, 12, 12);
        holder.unreadBadge.setVisibility(item.getUnreadCount() > 0 ? View.VISIBLE : View.GONE);
        holder.unreadBadge.setText(String.valueOf(item.getUnreadCount()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), item.isGroup() ? GroupChatRoomActivity.class : ChatRoomActivity.class);
            intent.putExtra("title", item.getTitle());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        final ImageView avatarImage;
        final TextView userNameText;
        final TextView lastMessageText;
        final TextView timeText;
        final TextView unreadBadge;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.avatarImage);
            userNameText = itemView.findViewById(R.id.userNameText);
            lastMessageText = itemView.findViewById(R.id.lastMessageText);
            timeText = itemView.findViewById(R.id.timeText);
            unreadBadge = itemView.findViewById(R.id.unreadBadge);
        }
    }
}
