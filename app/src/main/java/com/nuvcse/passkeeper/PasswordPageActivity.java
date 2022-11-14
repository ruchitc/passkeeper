package com.nuvcse.passkeeper;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;

public class PasswordPageActivity extends AppCompatActivity {

    String accName;
    String email;
    String password;

    TextView accNameView;
    TextView emailView;
    TextView passwordView;

    Button deleteDetails;
    Button editDetails;

    Bundle bundle;
    Bundle newBundle;

    account_details tempObj;

    Intent backIntent = new Intent();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users").document(user.getEmail()).collection("passwords");
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_page);

        bundle = getIntent().getExtras();

        accNameView = findViewById(R.id.text_accname_display);
        emailView = findViewById(R.id.text_email_display);
        passwordView = findViewById(R.id.text_password_display);

        Integer position = bundle.getInt("position");
        accName = bundle.getString("accName");
        email = bundle.getString("email");
        password = bundle.getString("password");

        updateView(accName, email, password);

        editDetails = findViewById(R.id.button_edit_details);
        editDetails.setOnClickListener(mEditBtnListener);

        deleteDetails = findViewById(R.id.button_delete_details);
        deleteDetails.setOnClickListener(mDeleteBtnListener);
    }

    private void updateToDB(String newAccName, String newEmail, String newPassword) {
        tempObj = new account_details(newAccName, newEmail, newPassword);

        Query query = mColRef.whereEqualTo("accName", accName).whereEqualTo("email", email);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        mColRef.document(document.getId())
                                .set(tempObj);
                    }
                }
            }
        });
    }

    private void deleteFromDB() {
        Query query = mColRef.whereEqualTo("accName", accName).whereEqualTo("email", email);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                mColRef.document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                finish();
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    private void updateView(String accName, String email, String password) {

        if (accNameView.getText() != null) {
            accNameView.setText(accName);
        }

        if (emailView.getText() != null) {
            emailView.setText(email);
        }

        if (passwordView.getText() != null) {
            passwordView.setText(password);
        }
    }

    private View.OnClickListener mEditBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), EditPasswordPageActivity.class);
            intent.putExtras(bundle);
            editLauncher.launch(intent);

            setResult(2);
        }
    };

    private View.OnClickListener mDeleteBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            deleteFromDB();
            setResult(1);
        }
    };

    ActivityResultLauncher<Intent> editLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Bundle backBundle;

                    if(result.getResultCode() == 1) {
                        Intent intent = result.getData();

                        if(intent != null) {
                            backBundle = intent.getExtras();

                            String accName = backBundle.getString("accName");
                            String email = backBundle.getString("email");
                            String password = backBundle.getString("password");

                            updateView(accName, email, password);
                            updateToDB(accName, email, password);

                            newBundle = new Bundle();
                            newBundle.putString("accName", accName);
                            newBundle.putString("email", email);
                            newBundle.putString("password", password);
                        }
                    }
                }
            }
    );
}