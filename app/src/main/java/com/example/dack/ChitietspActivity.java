package com.example.dack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.databinding.ActivityChitietspBinding;
import com.example.dack.models.Giohang;
import com.example.dack.models.Sanpham;

public class ChitietspActivity extends AppCompatActivity {
    ActivityChitietspBinding binding;
    Sanpham sanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChitietspBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar();
        getInforSP();
        binding.btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkdathang = false;
                for(int i=0;i<GiohangActivity.lstgiohang.size();i++){
                    if(GiohangActivity.lstgiohang.get(i).getIdspgh().equals(sanpham.getIdsp())){
                        checkdathang = true;
                    }else{
                        checkdathang = false;
                    }
                }
                if(checkdathang){
                    Toast.makeText(ChitietspActivity.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }else{
                    GiohangActivity.lstgiohang.add(new Giohang(sanpham.getIdsp(),sanpham.getTensp(),sanpham.getGiasp(),1,sanpham.getHinhanhsp(),sanpham.getGiasp()));
                    Toast.makeText(ChitietspActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getInforSP(){
        sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinchitietsanpham");
        binding.tvtenchitietsp.setText(sanpham.getTensp());
        binding.tvgiasanpham.setText("Giá: "+sanpham.getGiasp()+" VNĐ");
        binding.tvmotachitiet.setText(sanpham.getMotasp());
        binding.ivchitietsp.setImageBitmap(convert64BasetoImage(sanpham.getHinhanhsp()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menugiohang){
            Intent intent = new Intent(this, GiohangActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbarchitietsanpham);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarchitietsanpham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public Bitmap convert64BasetoImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}