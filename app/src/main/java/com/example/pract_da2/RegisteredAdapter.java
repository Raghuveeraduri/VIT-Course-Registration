package com.example.pract_da2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RegisteredAdapter extends RecyclerView.Adapter<RegisteredAdapter.ViewHolder> {
    ArrayList<RegisteredDataModel> data;
    Context context;
    public RegisteredAdapter(Context context,ArrayList<RegisteredDataModel> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RegisteredAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.registered_item_design,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredAdapter.ViewHolder holder, int position) {
        RegisteredDataModel obj=data.get(position);
        holder.registered_code.setText(obj.code);
        holder.registered_title.setText(obj.title);
        holder.registered_slot.setText(obj.slot);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView registered_code;TextView registered_title;TextView registered_slot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            registered_code=itemView.findViewById(R.id.registered_code);
            registered_title=itemView.findViewById(R.id.registered_title);
            registered_slot=itemView.findViewById(R.id.registered_slot);
        }
    }
}
