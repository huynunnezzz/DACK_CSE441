package com.example.dack;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.SharedPreferences.SharedPreferencesManager;
import com.example.dack.databinding.ActivityThongtinnguoimuaBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ThongtinnguoimuaActivity extends AppCompatActivity {
    ActivityThongtinnguoimuaBinding binding;
    SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongtinnguoimuaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(ThongtinnguoimuaActivity.this);
        binding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtTenKH.getText().toString().isEmpty()){
                    binding.edtTenKH.setError("Không để trống");
                }else if(binding.edtSDT.getText().toString().isEmpty()){
                    binding.edtSDT.setError("Không để trống");
                }else if(binding.edtDiachi.getText().toString().isEmpty()){
                    binding.edtDiachi.setError("Không để trống");
                }
                else{
                    xacnhanmua();
                }

            }
        });
        binding.btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThongtinnguoimuaActivity.this,GiohangActivity.class));
            }
        });
    }
    public void xacnhanmua() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> khachhang = new HashMap<>();

        String sanpham = "";
        float total = 0 ;
        for(int i=0;i<GiohangActivity.lstgiohang.size();i++){
            if(i==GiohangActivity.lstgiohang.size()-1){
                sanpham += GiohangActivity.lstgiohang.get(i).getTensp()+"(" + GiohangActivity.lstgiohang.get(i).getGiasp() +" VNĐ)" + " - Số lượng: " + GiohangActivity.lstgiohang.get(i).getSoluongsp();
            }else{
                sanpham += GiohangActivity.lstgiohang.get(i).getTensp()+"(" + GiohangActivity.lstgiohang.get(i).getGiasp() +" VNĐ)" + " - Số lượng: " + GiohangActivity.lstgiohang.get(i).getSoluongsp() + " , ";
            }
            total += GiohangActivity.lstgiohang.get(i).getTotalsp();
        }

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String getTime = dateFormat.format(currentDate);

        khachhang.put("tenkh",binding.edtTenKH.getText().toString().trim());
        khachhang.put("sodt",binding.edtSDT.getText().toString().trim());
        khachhang.put("diachi",binding.edtDiachi.getText().toString().trim());
        khachhang.put("sanpham",sanpham);
        khachhang.put("tongtien",String.valueOf(total));
        khachhang.put("thoigiandat",getTime);
        db.collection("Users").document(sharedPreferencesManager.getString("userid")).collection("Donhang")
                .add(khachhang)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ThongtinnguoimuaActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ThongtinnguoimuaActivity.this,MainActivity.class));
                        finishAffinity();
                    }
                });
        db.collection("Donhang").add(khachhang);
        GiohangActivity.lstgiohang.clear();
    }
}