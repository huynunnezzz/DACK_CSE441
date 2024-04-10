package com.example.dack.admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dack.Adapter.SanphamAdapter;
import com.example.dack.Adapter.SanphamManagerAdapter;
import com.example.dack.R;
import com.example.dack.databinding.ActivitySanphamManagerBinding;
import com.example.dack.models.Sanpham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SanphamManagerActivity extends AppCompatActivity {
    ActivitySanphamManagerBinding binding;

    ArrayList<Sanpham> lstsp;
    SanphamAdapter sanphamAdapter;
    SanphamManagerAdapter sanphamManagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySanphamManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar();
        getData();

        binding.btnThemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SanphamManagerActivity.this,ThemSPActivity.class);
                startActivity(intent);
            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbarSP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanphamManagerActivity.this,ManagerActivity.class));
                finish();
            }
        });
    }
    public void getData(){
        loading(true);
        lstsp = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Sanpham")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                Sanpham sanpham = new Sanpham();
                                sanpham.setIdsp(queryDocumentSnapshot.getId());
                                sanpham.setTensp(queryDocumentSnapshot.getString("tensp"));
                                sanpham.setGiasp(Float.parseFloat(queryDocumentSnapshot.getString("giasp")));
                                sanpham.setMotasp(queryDocumentSnapshot.getString("motasp"));
                                sanpham.setHinhanhsp(queryDocumentSnapshot.getString("imagesp"));
                                lstsp.add(sanpham);
                            }
                            if(lstsp.size()>0){
                                loading(false);
                                sanphamManagerAdapter = new SanphamManagerAdapter(lstsp,SanphamManagerActivity.this);
                                binding.rvSP.setLayoutManager(new GridLayoutManager(SanphamManagerActivity.this,1));
                                binding.rvSP.setAdapter(sanphamManagerAdapter);
                            }else{
                                loading(false);
                                showToast("Không có dữ liệu");
                            }
                        }
                    }
                });
    }

    public void loading(Boolean isLoading) {
        if(isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void showToast(String message){
        Toast.makeText(SanphamManagerActivity.this,message,Toast.LENGTH_LONG).show();
    }
    public void showdialogEdit(int positon){
        AlertDialog.Builder builder = new AlertDialog.Builder(SanphamManagerActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_dialog_suasp,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText tensp,giasp,motasp;
        ImageView imvsp;
        Button btnEdit;
        tensp = view.findViewById(R.id.edtTensp);
        giasp = view.findViewById(R.id.edtGiasp);
        motasp = view.findViewById(R.id.edtMotasp);
        imvsp = view.findViewById(R.id.imvsp);
        btnEdit = view.findViewById(R.id.btnSua);

        Sanpham sanpham = lstsp.get(positon);
        tensp.setText(sanpham.getTensp());
        giasp.setText(String.valueOf(sanpham.getGiasp()));
        motasp.setText(sanpham.getMotasp());
        imvsp.setImageBitmap(convert64basetoImage(sanpham.getHinhanhsp()));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSP(sanpham.getIdsp(),tensp.getText().toString().trim(),Float.parseFloat(giasp.getText().toString().trim()),motasp.getText().toString().trim());
                dialog.dismiss();
                getData();
            }
        });
    }
    public void updateSP(String id,String tensp,float giasp,String motasp){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> product = new HashMap<>();
        product.put("tensp",tensp);
        product.put("giasp",String.valueOf(giasp));
        product.put("motasp",motasp);
        db.collection("Sanpham")
                .document(id)
                .update(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            showToast("Sửa thành công");
                        }else{
                            showToast("Sửa thất bại");
                        }
                    }
                });
    }
    public void deleteSP(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Sanpham")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            showToast("Xóa thành công");
                            getData();
                        }
                    }
                });
    }
    public Bitmap convert64basetoImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}