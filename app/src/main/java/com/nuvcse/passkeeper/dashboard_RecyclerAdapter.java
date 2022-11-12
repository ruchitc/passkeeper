package com.nuvcse.passkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class dashboard_RecyclerAdapter extends RecyclerView.Adapter<dashboard_RecyclerAdapter.MyViewHolder> {

    private final DashboardInterface dashboardInterface;

    Context context;
    ArrayList<user_account_list> myAccountList;

    // Constructor
    public dashboard_RecyclerAdapter(Context context, ArrayList<user_account_list> myAccountList, DashboardInterface dashboardInterface) {
        this.context = context;
        this.myAccountList = myAccountList;
        this.dashboardInterface = dashboardInterface;
    }
    @NonNull
    @Override
    public dashboard_RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.password_item, parent, false);

        return new dashboard_RecyclerAdapter.MyViewHolder(view, dashboardInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull dashboard_RecyclerAdapter.MyViewHolder holder, int position) {
        String counter = myAccountList.get(position).getCounter();
        holder.password_counter.setText(counter);
    }

    @Override
    public int getItemCount() {
        return myAccountList.size();
    }

    // Inner class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView password_counter;

        // Inner class constructor
        // itemView is an individual recyclerview item
        public MyViewHolder(@NonNull View itemView, DashboardInterface dashboardInterface) {
            super(itemView);

            password_counter = itemView.findViewById(R.id.recycler_counter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dashboardInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            dashboardInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
