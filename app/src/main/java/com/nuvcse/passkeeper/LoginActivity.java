package com.nuvcse.passkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.edit_text_email);
        mPassword = findViewById(R.id.edit_text_password);
        mLoginBtn = findViewById(R.id.button_login);
        mRegisterBtn = findViewById(R.id.text_existing_user);
        progressBar = findViewById(R.id.progress_register);

        fAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(mLoginBtnListener);
        mRegisterBtn.setOnClickListener(mRegisterBtnListener);
    }

    private View.OnClickListener mLoginBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                mEmail.setError("Enter your email.");
                return;
            }

            if(TextUtils.isEmpty(password)) {
                mPassword.setError("Enter the password");
                return;
            }

            if(password.length() < 6) {
                mPassword.setError("Password must be at least 6 characters long.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logged in.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    };

    private View.OnClickListener mRegisterBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    };
}