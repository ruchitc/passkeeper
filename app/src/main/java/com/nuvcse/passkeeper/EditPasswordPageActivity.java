package com.nuvcse.passkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditPasswordPageActivity extends AppCompatActivity {

    EditText editAccName;
    EditText editEmail;
    EditText editPassword;

    FloatingActionButton buttonSave;

    Toolbar toolbar;

    Intent intent;
    Bundle bundle;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_item:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();

        String accName = bundle.getString("accName");
        String email = bundle.getString("email");
        String password = bundle.getString("password");

        editAccName = findViewById(R.id.edit_text_accname);
        editAccName.setText(accName);
        editEmail = findViewById(R.id.edit_text_email);
        editEmail.setText(email);
        editPassword = findViewById(R.id.edit_text_password);
        editPassword.setText(password);

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