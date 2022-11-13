package com.nuvcse.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PasswordPageActivity extends AppCompatActivity {

    TextView accNameView;
    TextView emailView;
    TextView passwordView;
    Button deleteDetails;
    Button editDetails;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_page);

        bundle = getIntent().getExtras();

        Integer position = bundle.getInt("position");
        String accName = bundle.getString("accName");
        String email = bundle.getString("email");
        String password = bundle.getString("password");

        accNameView = findViewById(R.id.text_accname_display);
        if (accNameView.getText() != null) {
            accNameView.setText(accName);
        }
        emailView = findViewById(R.id.text_email_display);
        if (emailView.getText() != null) {
            emailView.setText(email);
        }
        passwordView = findViewById(R.id.text_password_display);
        if (passwordView.getText() != null) {
            passwordView.setText(password);
        }

        editDetails = findViewById(R.id.button_edit_details);
        editDetails.setOnClickListener(mEditBtnListener);

        deleteDetails = findViewById(R.id.button_delete_details);
        deleteDetails.setOnClickListener(mDeleteBtnListener);
    }

    private View.OnClickListener mEditBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

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