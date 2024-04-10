package com.example.dack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class ResetPasswordActivity extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    private String generatedOTP;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar();
        binding.btnGuimaOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtEmail.getText().toString().isEmpty()){
                    binding.edtEmail.setError("Email không để trống");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText().toString().trim()).matches()) {
                    binding.edtEmail.setError("Email không đúng dạng");
                }
                else{
                    checkEmail();
                }

            }
        });
        binding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtOTP.getText().toString().isEmpty()){
                    binding.edtOTP.setError("Nhập OTP");
                }else{
                    checkOTP(binding.edtOTP.getText().toString());
                }

            }
        });
        binding.btnXacnhandoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doimatkhau();
            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbarresetpassword);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarresetpassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void checkEmail(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("email",binding.edtEmail.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task ) {
                        if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
                            sendOTP(binding.edtEmail.getText().toString());
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            id = documentSnapshot.getId();
                            binding.LLmail.setVisibility(View.INVISIBLE);
                            binding.LLOTP.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(ResetPasswordActivity.this, "Email chưa đăng ký", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void checkOTP(String maOTP){
        if (maOTP.equals(generatedOTP)) {
            binding.LLOTP.setVisibility(View.INVISIBLE);
            binding.LLresetMk.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Mã OTP không đúng", Toast.LENGTH_SHORT).show();
        }
    }
    private void sendOTP(String email) {
        generatedOTP = RandomOTP();
        EmailSender emailSender = new EmailSender(email, "OTP Verification", "Mã xác thực OTP của bạn là: " + generatedOTP);
        emailSender.execute();
        Log.d("otp", "sendOTP: " + generatedOTP);
        Toast.makeText(this, "OTP đã gửi vào email của bạn", Toast.LENGTH_SHORT).show();
    }
    private String RandomOTP() {
        Random random = new Random();
        int otp = random.nextInt(9000) + 1000;
        return String.valueOf(otp);
    }
    public void doimatkhau(){
        if(binding.edtPassword.getText().toString().isEmpty()){
            binding.edtPassword.setError("Mật khẩu không để trống");
        }
        else if( binding.edtRepassword.getText().toString().isEmpty()){
            binding.edtRepassword.setError("Mật khẩu không để trống");
        }
        else if(!binding.edtPassword.getText().toString().equals(binding.edtRepassword.getText().toString())){
            Toast.makeText(ResetPasswordActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Users")
                    .document(id)
                    .update("password",binding.edtPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPasswordActivity.this,SigninActivity.class));
                        }
                    });
        }
    }
}