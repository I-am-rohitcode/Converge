package com.converge.chatapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class VoiceCallActivity extends AppCompatActivity {

    public static final String EXTRA_DISPLAY_NAME = "display_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voice_call);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbar), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), bars.top, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        String name = getIntent().getStringExtra(EXTRA_DISPLAY_NAME);
        if (name == null || name.isEmpty()) {
            name = getString(R.string.default_chat_title);
        }
        TextView peerName = findViewById(R.id.peerName);
        peerName.setText(name);

        TextView callStatus = findViewById(R.id.callStatus);
        Chronometer callTimer = findViewById(R.id.callTimer);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callStatus.setText(R.string.call_status_connected);
            callTimer.setVisibility(android.view.View.VISIBLE);
            callTimer.setBase(SystemClock.elapsedRealtime());
            callTimer.start();
        }, 1200L);

        findViewById(R.id.btnEndCall).setOnClickListener(v -> finish());

        MaterialButton mute = findViewById(R.id.btnMute);
        final boolean[] muted = {false};
        mute.setOnClickListener(v -> {
            muted[0] = !muted[0];
            mute.setAlpha(muted[0] ? 0.45f : 1f);
            Toast.makeText(this, muted[0] ? R.string.call_mute : R.string.call_unmute, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnSpeaker).setOnClickListener(v ->
                Toast.makeText(this, R.string.call_speaker, Toast.LENGTH_SHORT).show());
    }
}
