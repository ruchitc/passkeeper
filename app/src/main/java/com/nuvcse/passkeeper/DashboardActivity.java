package com.nuvcse.passkeeper;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements DashboardInterface{

    ArrayList<user_account_list> myAccountList = new ArrayList<>();
    RecyclerView recyclerView;

    Button addPassword;

    Intent intent;
    Bundle bundle;

    dashboard_RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addPassword = findViewById(R.id.button_add_password);
        recyclerView = findViewById(R.id.recycler_view);

        setMyAccountList();
        setAdapter();

        addPassword.setOnClickListener(mAddBtnListener);
    }

    private void setAdapter() {
        adapter = new dashboard_RecyclerAdapter(this, myAccountList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setMyAccountList() {
        int i;

        // temp to check recyclerview
        for(i = 1; i <=20; i++) {
            myAccountList.add(new user_account_list(Integer.toString(i)));
        }
    }

    @Override
    public void onItemClick(int position) {
        int requestCode = 0;
        intent = new Intent(getApplicationContext(), PasswordPageActivity.class);

        bundle = new Bundle();

        String counter = myAccountList.get(position).getCounter();
        bundle.putInt("position", position);
        Log.d("Dashboard", Integer.toString(position));
        bundle.putString("counter", counter);
        intent.putExtras(bundle);

        activityLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Integer position;
                    Bundle backBundle;

                    if(result.getResultCode() == 1) {
                        Intent intent = result.getData();


                        if(intent != null) {
                            backBundle = new Bundle();
                            backBundle = intent.getExtras();
                            position = backBundle.getInt("position");

                            Log.d("DeletePassword", Integer.toString(position));

                            myAccountList.remove(position);

                            Log.d("DeletePassword", adapter.toString());
                            adapter.notifyItemRemoved(position);
                        }

                    }
                }
            }
    );

    private View.OnClickListener mAddBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer newItem;
            newItem = Integer.parseInt(myAccountList.get(myAccountList.size() - 1).getCounter());

            myAccountList.add(new user_account_list(Integer.toString(newItem)));

            adapter.notifyDataSetChanged();
        }
    };
}