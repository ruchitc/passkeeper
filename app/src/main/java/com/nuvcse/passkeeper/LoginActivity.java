package com.nuvcse.passkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mRegisterBtn;
    TextView mForgotBtn;
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
        mForgotBtn = findViewById(R.id.text_forgot_password);
        progressBar = findViewById(R.id.progress_register);

        fAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(mLoginBtnListener);
        mRegisterBtn.setOnClickListener(mRegisterBtnListener);
        mForgotBtn.setOnClickListener(mForgotBtnListener);
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
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
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

    private View.OnClickListener mForgotBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText resetMail = new EditText(view.getContext());
            AlertDialog.Builder resetDialog = new AlertDialog.Builder(view.getContext());

            resetDialog.setTitle("Reset password?");
            resetDialog.setMessage("Enter your email.");
            resetDialog.setView(resetMail);

            resetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(LoginActivity.this, "Reset link sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Error! Reset link not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            resetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            resetDialog.create().show();
        }
    };
}