package com.converge.chatapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class VideoCallActivity extends AppCompatActivity {

    public static final String EXTRA_DISPLAY_NAME = "display_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_call);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.controlsRow), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), bars.bottom + v.getPaddingBottom());
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

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            callStatus.setText(R.string.call_status_connected);
            callTimer.setVisibility(View.VISIBLE);
            callTimer.setBase(SystemClock.elapsedRealtime());
            callTimer.start();
        }, 1200L);

        MaterialCardView pip = findViewById(R.id.pipLocal);
        MaterialButton videoToggle = findViewById(R.id.btnVideoToggle);
        final boolean[] videoOff = {false};
        videoToggle.setOnClickListener(v -> {
            videoOff[0] = !videoOff[0];
            pip.setAlpha(videoOff[0] ? 0.35f : 1f);
            Toast.makeText(this, videoOff[0] ? R.string.call_video_off : R.string.call_video_on, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnFlip).setOnClickListener(v ->
                Toast.makeText(this, R.string.call_flip_camera, Toast.LENGTH_SHORT).show());

        MaterialButton mute = findViewById(R.id.btnMute);
        final boolean[] muted = {false};
        mute.setOnClickListener(v -> {
            muted[0] = !muted[0];
            mute.setAlpha(muted[0] ? 0.45f : 1f);
            Toast.makeText(this, muted[0] ? R.string.call_mute : R.string.call_unmute, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnEndCall).setOnClickListener(v -> finish());
    }
}
