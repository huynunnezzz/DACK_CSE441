package com.example.dack.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.Adapter.UsersAdapter;
import com.example.dack.databinding.ActivityUsersManagerBinding;
import com.example.dack.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UsersManagerActivity extends AppCompatActivity {


    private ActivityUsersManagerBinding binding;
    ArrayList<Users> lstusers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lstusers = new ArrayList<>();
        ActionBar();
        getUsers();
        binding.lvuser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(UsersManagerActivity.this)
                        .setTitle("Xóa User?")
                        .setMessage("Bạn chắc chắn xóa")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Users users = lstusers.get(position);
                                deleteUser(users.getId());
                                getUsers();
                            }
                        })
                        .setNegativeButton("Không",null)
                        .show();

            }
        });
    }

    public void ActionBar() {
        setSupportActionBar(binding.toolbaruser);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbaruser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsersManagerActivity.this,ManagerActivity.class));
                finish();
            }
        });
    }
    public void getUsers(){
        loading(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                Users users = new Users();
                                users.setId(queryDocumentSnapshot.getId());
                                users.setFullName(queryDocumentSnapshot.getString("fullname"));
                                users.setUsername(queryDocumentSnapshot.getString("username"));
                                users.setPassword(queryDocumentSnapshot.getString("password"));
                                users.setEmail(queryDocumentSnapshot.getString("email"));
                                lstusers.add(users);
                            }
                            if(lstusers.size()>0){
                                loading(false);
                                UsersAdapter usersAdapter = new UsersAdapter(lstusers,UsersManagerActivity.this);
                                binding.lvuser.setAdapter(usersAdapter);
                            }else{
                                loading(false);
                                Toast.makeText(UsersManagerActivity.this,"Không có dữ liệu",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    private void loading(Boolean isLoading) {
        if(isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void deleteUser(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UsersManagerActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UsersManagerActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}