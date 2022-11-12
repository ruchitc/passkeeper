package com.nuvcse.passkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    ArrayList<user_account_list> myAccountList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recycler_view);

        setMyAccountList();
        setAdapter();
    }

    private void setAdapter() {
        dashboard_RecyclerAdapter adapter = new dashboard_RecyclerAdapter(this, myAccountList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setMyAccountList() {
        int i;

        for(i = 1; i <=20; i++) {
            myAccountList.add(new user_account_list(Integer.toString(i)));
        }
    }
}