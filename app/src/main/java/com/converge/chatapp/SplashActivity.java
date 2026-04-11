package com.converge.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // CHECK AND DISPLAY PREVIOUS CRASH LOG
        SharedPreferences prefs = getSharedPreferences("CrashLogs", Context.MODE_PRIVATE);
        String crash = prefs.getString("last_crash", null);
        if (crash != null) {
            prefs.edit().remove("last_crash").apply();
            new AlertDialog.Builder(this)
                .setTitle("🚨 App Crashed 🚨")
                .setMessage("Please copy this and send it back to me:\n\n" + crash)
                .setPositiveButton("OK", (d, w) -> startPrototypeFlow())
                .setCancelable(false)
                .show();
            return;
        }

        // SET GLOBAL EXCEPTION HANDLER
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            String stackTrace = android.util.Log.getStackTraceString(throwable);
            getSharedPreferences("CrashLogs", Context.MODE_PRIVATE)
                .edit().putString("last_crash", stackTrace).commit();
            System.exit(2);
        });

        startPrototypeFlow();
    }

    private void startPrototypeFlow() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // Add a short delay before launching the main screen
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, ChatListActivity.class));
            finish();
        }, 2000);
    }
}