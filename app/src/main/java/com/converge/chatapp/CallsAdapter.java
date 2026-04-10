package com.converge.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.CallViewHolder> {
    private final List<CallPreview> items;

    public CallsAdapter(List<CallPreview> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_list, parent, false);
        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {
        CallPreview item = items.get(position);
        holder.userNameText.setText(item.getTitle());
        holder.timeText.setText(item.getSubtitle());
        holder.avatarImage.setImageResource(item.getAvatarIconRes());
        holder.avatarImage.setPadding(12, 12, 12, 12);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CallViewHolder extends RecyclerView.ViewHolder {
        final ImageView avatarImage;
        final TextView userNameText;
        final TextView timeText;

        CallViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.avatarImage);
            userNameText = itemView.findViewById(R.id.userNameText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}
