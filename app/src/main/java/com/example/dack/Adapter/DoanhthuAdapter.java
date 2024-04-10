package com.example.dack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dack.R;
import com.example.dack.models.Doanhthu;

import java.util.ArrayList;

public class DoanhthuAdapter extends RecyclerView.Adapter<DoanhthuAdapter.ViewHolder> {
    ArrayList<Doanhthu> lstDoanhthu;
    Context context;

    public DoanhthuAdapter(ArrayList<Doanhthu> lstDoanhthu,Context context){
        this.lstDoanhthu = lstDoanhthu;
        this.context = context;
    }
    @NonNull
    @Override
    public DoanhthuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_doanhthu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoanhthuAdapter.ViewHolder holder, int position) {
        Doanhthu doanhthu = lstDoanhthu.get(position);
        holder.tvID.setText(doanhthu.getId());
        holder.tvThoigian.setText(doanhthu.getThoigiandat());
        holder.tvTongtien.setText(doanhthu.getTongtien()+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return lstDoanhthu.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvID,tvThoigian,tvTongtien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvThoigian = itemView.findViewById(R.id.tvThoigian);
            tvTongtien = itemView.findViewById(R.id.tvTongtien);
            tvID = itemView.findViewById(R.id.tvID);
        }
    }
}
