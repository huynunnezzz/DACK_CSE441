package com.example.dack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dack.R;
import com.example.dack.models.Donhang;

import java.util.ArrayList;

public class DonhangAdapter extends RecyclerView.Adapter<DonhangAdapter.ViewHolder> {
    ArrayList<Donhang> lstdonhang;
    Context context;

    public DonhangAdapter(ArrayList<Donhang> lstdonhang,Context context){
        this.lstdonhang = lstdonhang;
        this.context = context;
    }

    @NonNull
    @Override
    public DonhangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_donhang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonhangAdapter.ViewHolder holder, int position) {
        Donhang donhang = lstdonhang.get(position);
        holder.tvID.setText(donhang.getId());
        holder.tvHoten.setText(donhang.getTenkh());
        holder.tvSdt.setText(donhang.getSdt());
        holder.tvDiachi.setText(donhang.getDiachi());
        holder.tvSanpham.setText(donhang.getSanpham());
        holder.tvThoigian.setText(donhang.getThoigiandat());
        holder.tvTongtien.setText(donhang.getTongtien()+" VNĐ");
    }
    @Override
    public int getItemCount() {
        return lstdonhang.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvHoten,tvSdt,tvDiachi,tvSanpham,tvThoigian,tvTongtien,tvID;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoten = itemView.findViewById(R.id.tvHoten);
            tvSdt = itemView.findViewById(R.id.tvSdt);
            tvDiachi = itemView.findViewById(R.id.tvDiachi);
            tvSanpham = itemView.findViewById(R.id.tvSanpham);
            tvThoigian = itemView.findViewById(R.id.tvThoigian);
            tvTongtien = itemView.findViewById(R.id.tvTongtien);
            tvID = itemView.findViewById(R.id.tvID);
        }
    }
}
