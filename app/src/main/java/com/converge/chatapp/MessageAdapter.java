package com.converge.chatapp;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        switch (viewType) {
            case MessageItem.TYPE_SENT:
                return new SentViewHolder(inflater.inflate(R.layout.item_chat_bubble_sent, parent, false));
            case MessageItem.TYPE_SENT_IMAGE:
                return new SentImageViewHolder(inflater.inflate(R.layout.item_chat_bubble_sent_image, parent, false));
            case MessageItem.TYPE_RECEIVED_IMAGE:
                return new ReceivedImageViewHolder(inflater.inflate(R.layout.item_chat_bubble_received_image, parent, false));
            case MessageItem.TYPE_GROUP_RECEIVED:
                return new GroupReceivedViewHolder(inflater.inflate(R.layout.item_group_chat_bubble_received, parent, false));
            case MessageItem.TYPE_GROUP_RECEIVED_IMAGE:
                return new GroupReceivedImageViewHolder(
                        inflater.inflate(R.layout.item_group_chat_bubble_received_image, parent, false));
            case MessageItem.TYPE_RECEIVED:
            default:
                return new ReceivedViewHolder(inflater.inflate(R.layout.item_chat_bubble_received, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageItem item = items.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).bind(item);
        } else if (holder instanceof SentImageViewHolder) {
            ((SentImageViewHolder) holder).bind(item);
        } else if (holder instanceof ReceivedImageViewHolder) {
            ((ReceivedImageViewHolder) holder).bind(item);
        } else if (holder instanceof GroupReceivedViewHolder) {
            ((GroupReceivedViewHolder) holder).bind(item);
        } else if (holder instanceof GroupReceivedImageViewHolder) {
            ((GroupReceivedImageViewHolder) holder).bind(item);
        } else if (holder instanceof ReceivedViewHolder) {
            ((ReceivedViewHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static void bindImage(ImageView imageView, MessageItem item) {
        String uriString = item.getImageUriString();
        if (uriString != null) {
            imageView.setImageURI(Uri.parse(uriString));
        }
    }

    private static void bindCaption(TextView caption, MessageItem item) {
        String text = item.getMessage();
        if (TextUtils.isEmpty(text)) {
            caption.setVisibility(View.GONE);
        } else {
            caption.setVisibility(View.VISIBLE);
            caption.setText(text);
        }
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

    static class SentImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView messageImage;
        private final TextView messageText;
        private final TextView messageTime;

        SentImageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageImage = itemView.findViewById(R.id.messageImage);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }

        void bind(MessageItem item) {
            bindImage(messageImage, item);
            bindCaption(messageText, item);
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

    static class ReceivedImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView messageImage;
        private final TextView messageText;
        private final TextView messageTime;

        ReceivedImageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageImage = itemView.findViewById(R.id.messageImage);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }

        void bind(MessageItem item) {
            bindImage(messageImage, item);
            bindCaption(messageText, item);
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

    static class GroupReceivedImageViewHolder extends RecyclerView.ViewHolder {
        private final TextView senderName;
        private final ImageView messageImage;
        private final TextView messageText;
        private final TextView messageTime;

        GroupReceivedImageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.senderName);
            messageImage = itemView.findViewById(R.id.messageImage);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }

        void bind(MessageItem item) {
            senderName.setText(item.getSender());
            bindImage(messageImage, item);
            bindCaption(messageText, item);
            messageTime.setText(item.getTime());
        }
    }
}
