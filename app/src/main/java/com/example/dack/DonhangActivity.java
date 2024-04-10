package com.example.dack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dack.Adapter.DonhangAdapter;
import com.example.dack.SharedPreferences.SharedPreferencesManager;
import com.example.dack.databinding.ActivityDonhangBinding;
import com.example.dack.models.Donhang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DonhangActivity extends AppCompatActivity {
    ActivityDonhangBinding binding;
    ArrayList<Donhang> lstdonhang;
    SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonhangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(DonhangActivity.this);
        ActionBar();
        getData();

    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbardonhang);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbardonhang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getData(){
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        lstdonhang = new ArrayList<>();
        db.collection("Users").document(sharedPreferencesManager.getString("userid")).collection("Donhang")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                Donhang donhang = new Donhang();
                                donhang.setId(queryDocumentSnapshot.getId());
                                donhang.setTenkh(queryDocumentSnapshot.getString("tenkh"));
                                donhang.setSdt(queryDocumentSnapshot.getString("sodt"));
                                donhang.setDiachi(queryDocumentSnapshot.getString("diachi"));
                                donhang.setSanpham(queryDocumentSnapshot.getString("sanpham"));
                                donhang.setThoigiandat(queryDocumentSnapshot.getString("thoigiandat"));
                                donhang.setTongtien(Float.parseFloat(queryDocumentSnapshot.getString("tongtien")));
                                lstdonhang.add(donhang);
                            }
                            if(lstdonhang.size()>0){
                                loading(false);
                                binding.rvDonhang.setLayoutManager(new GridLayoutManager(DonhangActivity.this,1));
                                binding.rvDonhang.setAdapter(new DonhangAdapter(lstdonhang,DonhangActivity.this));
                                binding.rvDonhang.setVisibility(View.VISIBLE);
                                binding.tvDonhangtrong.setVisibility(View.INVISIBLE);
                            }else{
                                loading(false);
                                binding.rvDonhang.setVisibility(View.INVISIBLE);
                                binding.tvDonhangtrong.setVisibility(View.VISIBLE);
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