package com.nuvcse.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditPasswordPageActivity extends AppCompatActivity {

    EditText editAccName;
    EditText editEmail;
    EditText editPassword;

    Button buttonSave;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_page);

        editAccName = findViewById(R.id.edit_text_accname);
        editEmail = findViewById(R.id.edit_text_email);
        editPassword = findViewById(R.id.edit_text_password);

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(mSaveBtnListener);
    }

    private View.OnClickListener mSaveBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String accName = editAccName.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            if(TextUtils.isEmpty(accName)) {
                editAccName.setError("Enter name for account");
                return;
            }

            if(TextUtils.isEmpty(email)) {
                editEmail.setError("Enter account email");
                return;
            }

            if(TextUtils.isEmpty(password)) {
                editPassword.setError("Enter account password");
                return;
            }

            Intent backIntent = new Intent();

            bundle = new Bundle();

            bundle.putString("accName", accName);
            bundle.putString("email", email);
            bundle.putString("password", password);

            backIntent.putExtras(bundle);
            setResult(1, backIntent);
            finish();
        }
    };
}