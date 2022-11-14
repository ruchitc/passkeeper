package com.nuvcse.passkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
    EditText mUsername, mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = findViewById(R.id.edit_text_username);
        mEmail = findViewById(R.id.edit_text_email);
        mPassword = findViewById(R.id.edit_text_password);
        mRegisterBtn = findViewById(R.id.button_register);
        mLoginBtn = findViewById(R.id.text_existing_user);
        progressBar = findViewById(R.id.progress_register);

        fAuth = FirebaseAuth.getInstance();

        //If user is already signed in
        if(fAuth.getCurrentUser() != null) {
            intent = new Intent(getApplicationContext(), DashboardActivity.class);

            startActivity(intent);
            finish();
        }

        mRegisterBtn.setOnClickListener(mRegisterBtnListener);
        mLoginBtn.setOnClickListener(mLoginBtnListener);
    }

    private View.OnClickListener mRegisterBtnListener = new View.OnClickListener() {
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

            // Registering to database
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {   //Successful registration
                        Toast.makeText(RegisterActivity.this,"Account created.", Toast.LENGTH_SHORT).show();

                        intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    } else {    //Registration failed
                        Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    };

    //Login page for existing user
    private View.OnClickListener mLoginBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    };
}