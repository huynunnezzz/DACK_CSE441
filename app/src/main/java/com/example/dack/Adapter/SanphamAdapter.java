package com.example.dack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dack.R;
import com.example.dack.models.Sanpham;

import java.util.ArrayList;

public class SanphamAdapter extends BaseAdapter {
    ArrayList<Sanpham> lstsp;
    Context context;
    public SanphamAdapter(ArrayList<Sanpham> lstsp,Context context){
        this.lstsp = lstsp;
        this.context = context;
    }
    @Override
    public int getCount() {
        return lstsp.size();
    }

    @Override
    public Object getItem(int position) {
        return lstsp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvTensp,tvGiasp,tvMotasp;
        ImageView imvsp;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.item_sanpham,null);

        tvTensp = convertView.findViewById(R.id.tvTensp);
        tvGiasp = convertView.findViewById(R.id.tvGiasp);
        tvMotasp = convertView.findViewById(R.id.tvMotasp);
        imvsp = convertView.findViewById(R.id.imvsp);

        Sanpham sanpham = (Sanpham) getItem(position);
        tvTensp.setText(sanpham.getTensp());
        tvGiasp.setText(sanpham.getGiasp()+" VNĐ");
        tvMotasp.setMaxLines(2);
        tvMotasp.setEllipsize(TextUtils.TruncateAt.END);
        tvMotasp.setText(sanpham.getMotasp());
        imvsp.setImageBitmap(convert64basetoImage(sanpham.getHinhanhsp()));
        return convertView;
    }
    public Bitmap convert64basetoImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
