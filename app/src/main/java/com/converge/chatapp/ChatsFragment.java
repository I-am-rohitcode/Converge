package com.converge.chatapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    public ChatsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ChatsAdapter(buildChatPreviews()));
        return view;
    }

    private List<ChatPreview> buildChatPreviews() {
        List<ChatPreview> items = new ArrayList<>();
        items.add(new ChatPreview("Aarav Patel", "Uploading now. I also created a dedicated chat room screen.", "8:45 PM", 2, false, android.R.drawable.ic_menu_myplaces));
        items.add(new ChatPreview("Design Crew", "Riya: Perfect. Next step is wiring it to real data.", "9:13 PM", 5, true, android.R.drawable.ic_menu_share));
        items.add(new ChatPreview("Maya Singh", "The palette looks much stronger now.", "7:28 PM", 0, false, android.R.drawable.ic_menu_edit));
        return items;
    }
}
