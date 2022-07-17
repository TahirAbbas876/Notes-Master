package com.example.notesmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SaplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saplash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(SaplashActivity.this,CreateAccountActivity.class));
            finish();
            }
        },3000);
    }
}