package com.example.dack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.SharedPreferences.SharedPreferencesManager;
import com.example.dack.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(SignupActivity.this);
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkisEmpty()){
                    signup();
                }
            }
        });
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }
    public void signup(){

        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put("fullname",binding.edtFullName.getText().toString().trim());
        user.put("username",binding.edtUsername.getText().toString().trim());
        user.put("password",binding.edtPassword.getText().toString().trim());
        user.put("email",binding.edtEmail.getText().toString().trim());

        db.collection("Users")
                .whereEqualTo("username",binding.edtUsername.getText().toString().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
                            loading(false);
                            showToast("Tài khoản đã được đăng ký");
                        }else{
                            db.collection("Users")
                                    .whereEqualTo("email",binding.edtEmail.getText().toString().trim())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0){
                                                loading(false);
                                                showToast("Email đã được đăng ký");
                                            }else{
                                                db.collection("Users")
                                                        .add(user)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                loading(false);
                                                                sharedPreferencesManager.putString("userid",documentReference.getId());
                                                                sharedPreferencesManager.putString("fullname",binding.edtFullName.getText().toString());
                                                                sharedPreferencesManager.putString("username",binding.edtUsername.getText().toString());
                                                                sharedPreferencesManager.putString("password",binding.edtPassword.getText().toString());
                                                                sharedPreferencesManager.putString("email",binding.edtEmail.getText().toString());
                                                                showToast("Đăng ký thành công");
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void signin(){
        startActivity(new Intent(SignupActivity.this,SigninActivity.class));
    }

    public void loading(Boolean isLoading) {
        if(isLoading) {
            binding.btnSignup.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.btnSignup.setVisibility(View.VISIBLE);
        }
    }
    public Boolean checkisEmpty(){
        if(binding.edtFullName.getText().toString().trim().isEmpty()){
            binding.edtFullName.setError("Họ tên không để trống");
            return false;
        }
        else if(binding.edtUsername.getText().toString().trim().isEmpty()){
            binding.edtUsername.setError("Username không để trống");
            return false;
        }
        else if(binding.edtPassword.getText().toString().trim().isEmpty()){
            binding.edtPassword.setError("Mật khẩu không để trống");
            return false;
        }
        else if (binding.edtRepassword.getText().toString().trim().isEmpty()) {
            binding.edtRepassword.setError("Mật khẩu không để trống");
            return false;
        }
        else if(!binding.edtPassword.getText().toString().equals(binding.edtRepassword.getText().toString())){
            showToast("Mật khẩu không trùng khớp");
            return false;
        }
        else if(binding.edtEmail.getText().toString().trim().isEmpty()){
            binding.edtEmail.setError("Email không để trống");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText().toString().trim()).matches()){
            binding.edtEmail.setError("Email không đúng dạng");
            return false;
        }
        else{
            return true;
        }
    }
    public void showToast(String message){
        Toast.makeText(SignupActivity.this,message,Toast.LENGTH_SHORT).show();
    }

}