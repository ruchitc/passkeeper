package com.nuvcse.passkeeper;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements DashboardInterface{

    ArrayList<account_details> myAccountList = new ArrayList<>();
    RecyclerView passwordsRecycler;

    Button addPassword;
    Button buttonLogout;

    passwordsAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users").document(user.getEmail()).collection("passwords");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DashboardActivity", "start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addPassword = findViewById(R.id.button_add_password);
        addPassword.setOnClickListener(mAddBtnListener);

        buttonLogout = findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(mLogoutBtnListener);

        passwordsRecycler = findViewById(R.id.recycler_view);

        setMyAccountList();
        setAdapter();
    }

    private void setAdapter() {
        adapter = new passwordsAdapter(this, myAccountList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        passwordsRecycler.setLayoutManager(layoutManager);
        passwordsRecycler.setItemAnimator(new DefaultItemAnimator());
        passwordsRecycler.setAdapter(adapter);
    }

    private void setMyAccountList() {

        mColRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                account_details temp = document.toObject(account_details.class);
                                myAccountList.add(temp);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        int requestCode = 0;
        Intent intent = new Intent(getApplicationContext(), PasswordPageActivity.class);

        Bundle bundle = new Bundle();

        String accName = myAccountList.get(position).getAccName();
        String email = myAccountList.get(position).getEmail();
        String password = myAccountList.get(position).getPassword();

        bundle.putInt("position", position);
        bundle.putString("accName", accName);
        bundle.putString("email", email);
        bundle.putString("password", password);

        intent.putExtras(bundle);
        passwordPageLauncher.launch(intent);
    }

    private View.OnClickListener mAddBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), EditPasswordPageActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("accName", "");
            bundle.putString("email", "");
            bundle.putString("password", "");

            intent.putExtras(bundle);
            saveLauncher.launch(intent);
        }
    };

    private View.OnClickListener mLogoutBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    };

    private void addToList(String accName, String email, String password) {
        myAccountList.add(new account_details(accName, email, password));
        adapter.notifyDataSetChanged();

        Integer position = myAccountList.size() - 1;

        mColRef
                .add(myAccountList.get(position))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void removeFromList(int position) {
        myAccountList.remove(position);
        adapter.notifyItemRemoved(position);

        // remove from database
    }

    ActivityResultLauncher<Intent> passwordPageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Integer position;

                    String accName;
                    String email;
                    String password;

                    Bundle backBundle;

                    // request code 1 to delete
                    if(result.getResultCode() == 1) {
                        myAccountList.clear();
                        setMyAccountList();
                    }

                    // request code 2 to update
                    if(result.getResultCode() == 2) {
                        myAccountList.clear();
                        setMyAccountList();
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> saveLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    String accName;
                    String email;
                    String password;

                    Bundle backBundle;

                    if(result.getResultCode() == 1) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            backBundle = intent.getExtras();

                            accName = backBundle.getString("accName");
                            email = backBundle.getString("email");
                            password = backBundle.getString("password");

                            addToList(accName, email, password);
                        }
                    }
                }
            }
    );

}