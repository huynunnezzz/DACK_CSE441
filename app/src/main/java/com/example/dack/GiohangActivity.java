package com.example.dack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dack.Adapter.GiohangAdapter;
import com.example.dack.databinding.ActivityGiohangBinding;
import com.example.dack.models.Giohang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangActivity extends AppCompatActivity {
    ActivityGiohangBinding binding;
    public static ArrayList<Giohang> lstgiohang = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGiohangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar();
        getData();
        totalMoney();
        binding.btnTieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiohangActivity.this,MainActivity.class));
            }
        });
        binding.btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lstgiohang.size()>0){
                    startActivity(new Intent(GiohangActivity.this,ThongtinnguoimuaActivity.class));
                }else{
                    Toast.makeText(GiohangActivity.this, "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbargiohang);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiohangActivity.this,MainActivity.class));
                finish();
            }
        });
    }
    public void getData(){
        if(lstgiohang.size()>0){
            binding.rvGiohang.setLayoutManager(new GridLayoutManager(GiohangActivity.this,1));
            binding.rvGiohang.setAdapter(new GiohangAdapter(lstgiohang,GiohangActivity.this));
            binding.rvGiohang.setVisibility(View.VISIBLE);
            binding.tvGiohangtrong.setVisibility(View.INVISIBLE);
        }else{
            binding.rvGiohang.setVisibility(View.INVISIBLE);
            binding.tvGiohangtrong.setVisibility(View.VISIBLE);
        }
    }
    public void totalMoney(){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        float total = 0;
        for(int i=0;i<lstgiohang.size();i++){
            total += lstgiohang.get(i).getTotalsp();
        }
        binding.textviewtongtien.setText(decimalFormat.format(total)+" VNĐ");
    }
}