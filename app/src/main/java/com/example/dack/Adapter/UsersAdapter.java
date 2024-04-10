package com.example.dack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dack.R;
import com.example.dack.models.Users;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter{
    private ArrayList<Users> lstusers;
    private Context context;
    public UsersAdapter(ArrayList<Users> lstusers,Context context){
        this.lstusers = lstusers;
        this.context = context;
    }
    @Override
    public int getCount() {
        return lstusers.size();
    }

    @Override
    public Object getItem(int position) {
        return lstusers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvFullname,tvUsername,tvPassword,tvEmail;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.item_get_user,null);

        tvFullname = convertView.findViewById(R.id.tvFullname);
        tvUsername = convertView.findViewById(R.id.tvUsername);
        tvPassword = convertView.findViewById(R.id.tvPassword);
        tvEmail = convertView.findViewById(R.id.tvEmail);
        Users users =(Users) getItem(position);
        tvFullname.setText("Tên: "+users.getFullName());
        tvUsername.setText("Username: "+users.getUsername());
        tvPassword.setText("Password: "+users.getPassword());
        tvEmail.setText("Email: "+users.getEmail());
        return convertView;
    }
}

