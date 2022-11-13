package com.nuvcse.passkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class passwordsAdapter extends RecyclerView.Adapter<passwordsAdapter.MyViewHolder> {

    private final DashboardInterface dashboardInterface;

    Context context;
    ArrayList<account_details> myAccountList;

    // Constructor
    public passwordsAdapter(Context context, ArrayList<account_details> myAccountList, DashboardInterface dashboardInterface) {
        this.context = context;
        this.myAccountList = myAccountList;
        this.dashboardInterface = dashboardInterface;
    }
    @NonNull
    @Override
    public passwordsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.password_item, parent, false);

        return new passwordsAdapter.MyViewHolder(view, dashboardInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull passwordsAdapter.MyViewHolder holder, int position) {
        String accName = myAccountList.get(position).getAccName();
        holder.accNameView.setText(accName);
    }

    @Override
    public int getItemCount() {
        return myAccountList.size();
    }

    // Inner class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView accNameView;

        // Inner class constructor
        // itemView is an individual recyclerview item
        public MyViewHolder(@NonNull View itemView, DashboardInterface dashboardInterface) {
            super(itemView);

            accNameView = itemView.findViewById(R.id.text_accname_display);

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
