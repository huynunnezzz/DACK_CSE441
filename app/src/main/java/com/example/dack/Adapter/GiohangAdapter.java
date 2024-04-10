package com.example.dack.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dack.GiohangActivity;
import com.example.dack.R;
import com.example.dack.models.Giohang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.ViewHolder> {
    ArrayList<Giohang> lstgiohang;
    DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
    Context context;
    public GiohangAdapter(ArrayList<Giohang> lstgiohang,Context context){
        this.lstgiohang = lstgiohang;
        this.context = context;
    }

    @NonNull
    @Override
    public GiohangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_giohang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiohangAdapter.ViewHolder holder, int position) {
        int vitri = position;
        Giohang giohang = lstgiohang.get(position);
        holder.tenspgiohang.setText(giohang.getTensp());
        holder.giaspgiohang.setText(giohang.getGiasp()+" VNĐ");
        holder.ivspgiohang.setImageBitmap(convert64basetoImage(giohang.getHinhanhsp()));
        holder.soluong.setText(String.valueOf(giohang.getSoluongsp()));
        holder.tvTotal.setText("Tổng: "+giohang.getGiasp()+ " VNĐ");
        holder.giamsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(holder.soluong.getText().toString()) - 1;
                float giahientai = giohang.getGiasp();
                float giamoinhat = giahientai*slmoinhat;
                giohang.setSoluongsp(slmoinhat);
                giohang.setTotalsp(giamoinhat);
                holder.soluong.setText(String.valueOf(slmoinhat));
                holder.tvTotal.setText("Tổng: "+decimalFormat.format(giamoinhat)+" VNĐ");

                if(slmoinhat < 2){
                    holder.giamsl.setVisibility(View.INVISIBLE);
                    holder.tangsl.setVisibility(View.VISIBLE);
                }else{
                    holder.giamsl.setVisibility(View.VISIBLE);
                    holder.tangsl.setVisibility(View.VISIBLE);
                }
                ((GiohangActivity)context).totalMoney();
            }
        });

        holder.tangsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(holder.soluong.getText().toString()) + 1;
                float giahientai = giohang.getGiasp();
                giohang.setSoluongsp(slmoinhat);
                float giamoinhat = giahientai*slmoinhat;
                giohang.setTotalsp(giamoinhat);
                holder.soluong.setText(String.valueOf(slmoinhat));
                holder.tvTotal.setText("Tổng: "+decimalFormat.format(giamoinhat)+" VNĐ");

                if(slmoinhat > 9){
                    holder.tangsl.setVisibility(View.INVISIBLE);
                    holder.giamsl.setVisibility(View.VISIBLE);
                }else{
                    holder.tangsl.setVisibility(View.VISIBLE);
                    holder.giamsl.setVisibility(View.VISIBLE);
                }
                ((GiohangActivity)context).totalMoney();
            }
        });
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa sản phẩm")
                        .setMessage("Bạn chắc chắn xóa")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GiohangActivity.lstgiohang.remove(vitri);
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                ((GiohangActivity)context).getData();
                                ((GiohangActivity)context).totalMoney();
                            }
                        })
                        .setNegativeButton("Không",null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstgiohang.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tenspgiohang,giaspgiohang,giamsl,tangsl,tvTotal,soluong;
        ImageView ivspgiohang;
        Button btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenspgiohang = itemView.findViewById(R.id.tvTenspgiohang);
            giaspgiohang = itemView.findViewById(R.id.tvGiaspgiohang);
            ivspgiohang = itemView.findViewById(R.id.ivgiohang);
            giamsl = itemView.findViewById(R.id.giamsl);
            tangsl = itemView.findViewById(R.id.tangsl);
            soluong = itemView.findViewById(R.id.soluong);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
    public Bitmap convert64basetoImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
