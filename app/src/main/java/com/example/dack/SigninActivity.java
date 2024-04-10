package com.example.dack;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.BroadcastReceiver.Battery;
import com.example.dack.SharedPreferences.SharedPreferencesManager;
import com.example.dack.admin.ManagerActivity;
import com.example.dack.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class SigninActivity extends AppCompatActivity {
    private ActivitySigninBinding binding;
    private SharedPreferencesManager sharedPreferencesManager;
    private Battery batterylow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(this);
        checkBattery();


        binding.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkisEmpty()){
                    if(binding.rdbtnUser.isChecked()){
                        signinUser();
                    }else if(binding.rdbtnAdmin.isChecked()){
                        signinAdmin();
                    }else{
                        showToast("Yêu cầu chọn quyền đăng nhập");
                    }
                }
            }
        });
        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        binding.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    public void checkBattery(){
        batterylow = new Battery();
        registerReceiver(batterylow, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
    public void signinUser(){
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .whereEqualTo("username",binding.edtUsername.getText().toString().trim())
                .whereEqualTo("password",binding.edtPassword.getText().toString().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
                            loading(false);
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            sharedPreferencesManager.putBoolean("isLogin",true);
                            sharedPreferencesManager.putString("userid",documentSnapshot.getId());
                            sharedPreferencesManager.putString("fullname",documentSnapshot.getString("fullname"));
                            sharedPreferencesManager.putString("username",documentSnapshot.getString("username"));
                            sharedPreferencesManager.putString("password",documentSnapshot.getString("password"));
                            sharedPreferencesManager.putString("email",documentSnapshot.getString("email"));
                            showToast("Đăng nhập thành công");
                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else{
                            loading(false);
                            showToast("Tài khoản hoặc mật khẩu không chính xác");
                        }
                    }
                });
    }
    public void signinAdmin(){
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Admin")
                .whereEqualTo("account",binding.edtUsername.getText().toString().trim())
                .whereEqualTo("password",binding.edtPassword.getText().toString().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
                            loading(false);
                            showToast("Đăng nhập thành công");
                            Intent intent = new Intent(SigninActivity.this,ManagerActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else{
                            loading(false);
                            showToast("Tài khoản và mật khẩu không chính xác");
                        }
                    }
                });
    }
    public void signup(){
        startActivity(new Intent(SigninActivity.this,SignupActivity.class));
    }
    public void showToast(String message){
        Toast.makeText(SigninActivity.this,message,Toast.LENGTH_LONG).show();
    }
    public Boolean checkisEmpty(){
        if(binding.edtUsername.getText().toString().trim().isEmpty() ){
            binding.edtUsername.setError("Username không để trống");
            return false;
        }
        else if(binding.edtPassword.getText().toString().trim().isEmpty()){
            binding.edtPassword.setError("Mật khẩu không để trống");
            return false;
        }
        return true;
    }
    public void loading(Boolean isLoading){
        if(isLoading) {
            binding.btnSignin.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.btnSignin.setVisibility(View.VISIBLE);
        }
    }
}