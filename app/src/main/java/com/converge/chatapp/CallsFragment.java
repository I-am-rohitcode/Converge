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

public class CallsFragment extends Fragment {

    public CallsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calls, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.callsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new CallsAdapter(buildCallPreviews()));
        return view;
    }

    private List<CallPreview> buildCallPreviews() {
        List<CallPreview> items = new ArrayList<>();
        items.add(new CallPreview("Maya Singh", "Outgoing • Today, 10:45 AM", android.R.drawable.ic_menu_call));
        items.add(new CallPreview("Design Crew", "Missed group call • Today, 9:10 AM", android.R.drawable.ic_menu_recent_history));
        items.add(new CallPreview("Aarav Patel", "Incoming • Yesterday, 8:15 PM", android.R.drawable.ic_menu_call));
        return items;
    }
}
