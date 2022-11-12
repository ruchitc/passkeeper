package com.nuvcse.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PasswordPageActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_page);

        Intent intent = getIntent();
        String counter;

        counter = intent.getStringExtra("counter");

        textView = findViewById(R.id.textView3);
        textView.setText(counter);
    }
}