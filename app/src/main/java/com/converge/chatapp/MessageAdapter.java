package com.converge.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<MessageItem> items;

    public MessageAdapter(List<MessageItem> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == MessageItem.TYPE_SENT) {
            return new SentViewHolder(inflater.inflate(R.layout.item_chat_bubble_sent, parent, false));
        } else if (viewType == MessageItem.TYPE_GROUP_RECEIVED) {
            return new GroupReceivedViewHolder(inflater.inflate(R.layout.item_group_chat_bubble_received, parent, false));
        }
        return new ReceivedViewHolder(inflater.inflate(R.layout.item_chat_bubble_received, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageItem item = items.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).bind(item);
        } else if (holder instanceof GroupReceivedViewHolder) {
            ((GroupReceivedViewHolder) holder).bind(item);
        } else if (holder instanceof ReceivedViewHolder) {
            ((ReceivedViewHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText;
        private final TextView messageTime;

        SentViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }

        void bind(MessageItem item) {
            messageText.setText(item.getMessage());
            messageTime.setText(item.getTime());
        }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageText;
        private final TextView messageTime;

        ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }

        void bind(MessageItem item) {
            messageText.setText(item.getMessage());
            messageTime.setText(item.getTime());
        }
    }

    static class GroupReceivedViewHolder extends RecyclerView.ViewHolder {
        private final TextView senderName;
        private final TextView messageText;
        private final TextView messageTime;

        GroupReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.senderName);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }

        void bind(MessageItem item) {
            senderName.setText(item.getSender());
            messageText.setText(item.getMessage());
            messageTime.setText(item.getTime());
        }
    }
}
