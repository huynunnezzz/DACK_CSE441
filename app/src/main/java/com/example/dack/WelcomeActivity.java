package com.example.dack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dack.BroadcastReceiver.Internet;
import com.example.dack.SharedPreferences.SharedPreferencesManager;
import com.example.dack.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }
    public void nextActivity(){
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(WelcomeActivity.this);
        if(sharedPreferencesManager.getBoolean("isLogin",true)){
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        }else{
            startActivity(new Intent(WelcomeActivity.this,SigninActivity.class));
        }
    }
}