package com.example.dack.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.databinding.ActivityThemSpactivityBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class ThemSPActivity extends AppCompatActivity {
    ActivityThemSpactivityBinding binding;
    private String ImageSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar();

        binding.layoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK,imageURI);
                chooseImage.launch(intent);
            }
        });
        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading(true);
                if(binding.edtTensp.getText().toString().isEmpty()){
                    loading(false);
                    binding.edtTensp.setError("Không để trống");
                }else if(binding.edtGiasp.getText().toString().isEmpty()){
                    loading(false);
                    binding.edtGiasp.setError("Không để trống");
                }else if(binding.edtMotasp.getText().toString().isEmpty()){
                    loading(false);
                    binding.edtMotasp.setError("Không để trống");
                }else if(ImageSP == null){
                    loading(false);
                    showToast("Yêu cầu chọn ảnh");
                }
                else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    HashMap<String, Object> product = new HashMap<>();
                    product.put("tensp",binding.edtTensp.getText().toString().trim());
                    product.put("giasp",binding.edtGiasp.getText().toString());
                    product.put("motasp",binding.edtMotasp.getText().toString().trim());
                    product.put("imagesp",ImageSP);
                    db.collection("Sanpham")
                            .add(product)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    loading(false);
                                    showToast("Thêm sản phẩm thành công");
                                }
                            });
                }

            }
        });
    }

    public void ActionBar() {
        setSupportActionBar(binding.toolbarThemSP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarThemSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSPActivity.this,SanphamManagerActivity.class));
                finish();
            }
        });
    }

    public void loading(Boolean isLoading) {
        if(isLoading) {
            binding.btnInsert.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.btnInsert.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void showToast(String message){
        Toast.makeText(ThemSPActivity.this,message,Toast.LENGTH_LONG).show();
    }
    private final ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.imvsp.setImageBitmap(bitmap);
                        ImageSP = convertImageto64Base(bitmap);
                    } catch ( Exception e){
                        showToast("Không có ảnh");
                    }
                }
            }
    );
    private String convertImageto64Base(Bitmap bitmap) {
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}