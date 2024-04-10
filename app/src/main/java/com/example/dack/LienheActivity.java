package com.example.dack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dack.databinding.ActivityLienheBinding;

public class LienheActivity extends AppCompatActivity {
    ActivityLienheBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLienheBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar();
        binding.cardcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:0971570582"));
                startActivity(intent);
            }
        });
        binding.cardzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:0971570582"));
                startActivity(intent);
            }
        });
        binding.cardfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkfb = "https://www.facebook.com/huynunne";
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(linkfb));
                startActivity(intent);
            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(binding.toolbarLienhe);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarLienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}