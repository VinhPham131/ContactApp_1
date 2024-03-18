package com.example.contactappv1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.AccessController;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<Contact> contactList;
    private Context context;
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        holder.tv_name.setText(contactList.get(position).getName());
        holder.linear_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewContactActivity.class);
                intent.putExtra("contact", position);
                context.startActivity(intent);
            }
        });
    }

    public ContactAdapter(ArrayList<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_name;
    public LinearLayout linear_item;

        public ViewHolder(View view) {
            super(view);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        linear_item = (LinearLayout) view.findViewById(R.id.rv_contact);
        }

    }
}


