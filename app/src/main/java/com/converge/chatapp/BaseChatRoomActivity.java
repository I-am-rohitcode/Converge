package com.converge.chatapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class BaseChatRoomActivity extends AppCompatActivity {
    public static final String EXTRA_CHAT_TITLE = "title";
    private static final String FILE_AUTHORITY = "com.converge.chatapp.fileprovider";

    private ArrayList<MessageItem> messages;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private View composerContainer;
    private int composerBottomPadding;
    private int recyclerBottomPadding;
    private TextInputEditText messageInput;

    private Uri pendingCameraUri;
    private ActivityResultLauncher<String> pickImageLauncher;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;

    protected abstract int contentLayoutResId();

    protected abstract void populateSeedMessages(@NonNull ArrayList<MessageItem> out);

    protected abstract String fallbackChatTitle();

    protected void bindHeaderExtras() {
    }

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(contentLayoutResId());

        composerContainer = findViewById(R.id.composerContainer);
        recyclerView = findViewById(R.id.messagesRecyclerView);
        composerBottomPadding = composerContainer.getPaddingBottom();
        recyclerBottomPadding = recyclerView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());
            
            // Apply bottom padding to the root view so everything is pushed exactly above the keyboard and nav bar
            int bottomInset = Math.max(systemBars.bottom, imeInsets.bottom);
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, bottomInset);
            
            return insets;
        });

        TextView titleView = findViewById(R.id.chatTitle);
        String title = getIntent().getStringExtra(EXTRA_CHAT_TITLE);
        titleView.setText(title != null ? title : fallbackChatTitle());
        bindHeaderExtras();

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        messageInput = findViewById(R.id.messageInput);
        MaterialButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> sendTextMessage());

        ImageButton attachButton = findViewById(R.id.attachMediaButton);
        attachButton.setOnClickListener(v -> showAttachMediaDialog());

        findViewById(R.id.callButton).setOnClickListener(v -> startVoiceCall());
        findViewById(R.id.videoCallButton).setOnClickListener(v -> startVideoCall());

        ImageButton moreButton = findViewById(R.id.moreButton);
        moreButton.setOnClickListener(this::showCallOverflowMenu);

        messages = new ArrayList<>();
        populateSeedMessages(messages);
        adapter = new MessageAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        scrollToBottom();

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                this::onGalleryImagePicked);
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (success && pendingCameraUri != null) {
                        addOutgoingImage(pendingCameraUri);
                    }
                });
        cameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (granted) {
                        launchCameraCapture();
                    } else {
                        Toast.makeText(this, R.string.permission_camera_required, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showCallOverflowMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add(0, 1, 0, R.string.voice_call);
        popup.getMenu().add(0, 2, 0, R.string.video_call);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                startVoiceCall();
                return true;
            }
            if (item.getItemId() == 2) {
                startVideoCall();
                return true;
            }
            return false;
        });
        popup.show();
    }

    protected String peerDisplayName() {
        String t = getIntent().getStringExtra(EXTRA_CHAT_TITLE);
        return t != null ? t : fallbackChatTitle();
    }

    private void startVoiceCall() {
        startActivity(new Intent(this, VoiceCallActivity.class)
                .putExtra(VoiceCallActivity.EXTRA_DISPLAY_NAME, peerDisplayName()));
    }

    private void startVideoCall() {
        startActivity(new Intent(this, VideoCallActivity.class)
                .putExtra(VideoCallActivity.EXTRA_DISPLAY_NAME, peerDisplayName()));
    }

    private void sendTextMessage() {
        if (messageInput == null) {
            return;
        }
        String body = messageInput.getText() != null ? messageInput.getText().toString().trim() : "";
        if (body.isEmpty()) {
            return;
        }
        String time = android.text.format.DateFormat.getTimeFormat(this).format(new java.util.Date());
        messages.add(new MessageItem(MessageItem.TYPE_SENT, "", body, time));
        adapter.notifyItemInserted(messages.size() - 1);
        messageInput.setText("");
        scrollToBottom();
    }

    private void scrollToBottom() {
        int last = messages.size() - 1;
        if (last >= 0) {
            recyclerView.smoothScrollToPosition(last);
        }
    }

    private void showAttachMediaDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.attach_media_title)
                .setItems(new CharSequence[]{
                        getString(R.string.take_photo),
                        getString(R.string.choose_from_gallery)
                }, (d, which) -> {
                    if (which == 0) {
                        tryOpenCamera();
                    } else {
                        openGallery();
                    }
                })
                .show();
    }

    private void tryOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            launchCameraCapture();
        }
    }

    private void launchCameraCapture() {
        try {
            pendingCameraUri = createImageUri();
            takePictureLauncher.launch(pendingCameraUri);
        } catch (IOException e) {
            Toast.makeText(this, R.string.camera_error, Toast.LENGTH_SHORT).show();
        }
    }

    private Uri createImageUri() throws IOException {
        File dir = new File(getCacheDir(), "images");
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("cache");
        }
        File file = new File(dir, "capture_" + System.currentTimeMillis() + ".jpg");
        return FileProvider.getUriForFile(this, FILE_AUTHORITY, file);
    }

    private void openGallery() {
        pickImageLauncher.launch("image/*");
    }

    private void onGalleryImagePicked(Uri uri) {
        if (uri != null) {
            addOutgoingImage(uri);
        }
    }

    private void addOutgoingImage(Uri uri) {
        String caption = messageInput != null && messageInput.getText() != null
                ? messageInput.getText().toString().trim()
                : "";
        String time = android.text.format.DateFormat.getTimeFormat(this).format(new java.util.Date());
        messages.add(new MessageItem(MessageItem.TYPE_SENT_IMAGE, "", caption, time, uri.toString()));
        adapter.notifyItemInserted(messages.size() - 1);
        if (messageInput != null) {
            messageInput.setText("");
        }
        scrollToBottom();
    }
}
