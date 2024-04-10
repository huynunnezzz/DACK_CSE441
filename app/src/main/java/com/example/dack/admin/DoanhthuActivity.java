package com.example.dack.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dack.Adapter.DoanhthuAdapter;
import com.example.dack.databinding.ActivityDoanhthuBinding;
import com.example.dack.models.Doanhthu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoanhthuActivity extends AppCompatActivity {
    ActivityDoanhthuBinding binding;
    ArrayList<Doanhthu> lstDoanhthu = new ArrayList<>();
    float tongtien = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoanhthuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar();
        getData();


    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbarqldoanhthu);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarqldoanhthu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getData(){
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Donhang")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                Doanhthu doanhthu = new Doanhthu();
                                doanhthu.setId(queryDocumentSnapshot.getId());
                                doanhthu.setThoigiandat(queryDocumentSnapshot.getString("thoigiandat"));
                                doanhthu.setTongtien(Float.parseFloat(queryDocumentSnapshot.getString("tongtien")));
                                lstDoanhthu.add(doanhthu);
                            }
                            if(lstDoanhthu.size()>0){
                                loading(false);
                                binding.rvDoanhthu.setLayoutManager(new GridLayoutManager(DoanhthuActivity.this,1));
                                binding.rvDoanhthu.setAdapter(new DoanhthuAdapter(lstDoanhthu,DoanhthuActivity.this));
                                for(int i=0;i<lstDoanhthu.size();i++){
                                    tongtien += lstDoanhthu.get(i).getTongtien();
                                }
                                binding.tvTongdoanhthu.setText(tongtien+" VNĐ");
                            }else{
                                loading(false);
                                Toast.makeText(DoanhthuActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

    }

    public void loading(Boolean isloading){
        if(isloading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}