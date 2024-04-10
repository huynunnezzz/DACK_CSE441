package com.example.dack.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dack.R;
import com.example.dack.admin.SanphamManagerActivity;
import com.example.dack.models.Sanpham;

import java.util.ArrayList;

public class SanphamManagerAdapter extends RecyclerView.Adapter<SanphamManagerAdapter.ViewHolder> {
    ArrayList<Sanpham> lstsp;
    Context context;
    public SanphamManagerAdapter(ArrayList<Sanpham> lstsp,Context context){
        this.lstsp = lstsp;
        this.context = context;
    }
    @NonNull
    @Override
    public SanphamManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sp_manager,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanphamManagerAdapter.ViewHolder holder, int position) {
        int vitri = position;
        Sanpham sanpham = lstsp.get(position);
        holder.tvTensp.setText(sanpham.getTensp());
        holder.tvGiasp.setText(sanpham.getGiasp()+" VNĐ");
        holder.tvMotasp.setText(sanpham.getMotasp());
        holder.tvMotasp.setMaxLines(2);
        holder.tvMotasp.setEllipsize(TextUtils.TruncateAt.END);
        holder.imvsp.setImageBitmap(convert64basetoImage(sanpham.getHinhanhsp()));
        holder.imvsuasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SanphamManagerActivity)context).showdialogEdit(vitri);
            }
        });
        holder.imvxoasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa sản phẩm")
                        .setMessage("Bạn chắc chắn xóa")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((SanphamManagerActivity)context).deleteSP(sanpham.getIdsp());
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Không",null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstsp.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTensp,tvGiasp,tvMotasp;
        ImageView imvsp,imvsuasp,imvxoasp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTensp = itemView.findViewById(R.id.tvTensp);
            tvGiasp = itemView.findViewById(R.id.tvGiasp);
            tvMotasp = itemView.findViewById(R.id.tvMotasp);
            imvsp = itemView.findViewById(R.id.imvsp);
            imvsuasp = itemView.findViewById(R.id.imvSuasp);
            imvxoasp = itemView.findViewById(R.id.imvXoasp);
        }
    }
    public Bitmap convert64basetoImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
