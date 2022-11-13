package com.nuvcse.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PasswordPageActivity extends AppCompatActivity {

    Integer position;

    TextView textView;
    Button deletePassword;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_page);

        bundle = new Bundle();
        bundle = getIntent().getExtras();

        String counter;

        counter = bundle.getString("counter");
        position = bundle.getInt("position");

        Log.d("Password page", Integer.toString(position));

        textView = findViewById(R.id.textView3);
        textView.setText(counter);

        deletePassword = findViewById(R.id.button_delete_password);
        deletePassword.setOnClickListener(mDeleteBtnListener);
    }

    private View.OnClickListener mDeleteBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent backIntent;
            backIntent = new Intent();
            backIntent.putExtras(bundle);

            setResult(1, backIntent);
            finish();
        }
    };
}