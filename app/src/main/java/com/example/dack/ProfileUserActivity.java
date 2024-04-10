package com.example.dack;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.SharedPreferences.SharedPreferencesManager;
import com.example.dack.databinding.ActivityProfileUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileUserActivity extends AppCompatActivity {
    ActivityProfileUserBinding binding;
    SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(ProfileUserActivity.this);
        ActionBar();
        getProfileUser();
        binding.btnDoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogdoimk();
            }
        });
    }
    public void getProfileUser(){
        binding.hotenuser.setText(sharedPreferencesManager.getString("fullname"));
        binding.taikhoanuser.setText(sharedPreferencesManager.getString("username"));
        binding.matkhauuser.setText(sharedPreferencesManager.getString("password"));
        binding.emailuser.setText(sharedPreferencesManager.getString("email"));

    }
    public void showdialogdoimk(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileUserActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_dialog_doimatkhau_user,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtMKhientai,edtMKmoi,edtXacnhanMK;
        Button btnDoimk;

        edtMKhientai = view.findViewById(R.id.edtMatkhauhientai);
        edtMKmoi = view.findViewById(R.id.edtMatkhaumoi);
        edtXacnhanMK = view.findViewById(R.id.edtXacnhanmatkhau);
        btnDoimk = view.findViewById(R.id.btnDoimk);
        
        btnDoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMKhientai.getText().toString().isEmpty()){
                    edtMKhientai.setError("Không để trống");
                }else if(edtMKmoi.getText().toString().isEmpty()){
                    edtMKmoi.setError("Không để trống");
                }else if(edtXacnhanMK.getText().toString().isEmpty()){
                    edtXacnhanMK.setError("Không để trống");
                }
                else if(!edtMKmoi.getText().toString().equals(edtXacnhanMK.getText().toString())){
                    Toast.makeText(ProfileUserActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Users")
                            .whereEqualTo("password",edtMKhientai.getText().toString().trim())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && task.getResult() != null){
                                        db.collection("Users")
                                                .document(sharedPreferencesManager.getString("userid"))
                                                .update("password",edtMKmoi.getText().toString().trim())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(ProfileUserActivity.this, "Đổi mật khẩu thành công! ", Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                            sharedPreferencesManager.putString("password",edtMKmoi.getText().toString().trim());
                                                            getProfileUser();
                                                        }
                                                    }
                                                });
                                    }else{
                                        Toast.makeText(ProfileUserActivity.this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbarProfileuser);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarProfileuser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}