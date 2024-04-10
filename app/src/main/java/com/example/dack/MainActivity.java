package com.example.dack;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.dack.Adapter.SanphamAdapter;
import com.example.dack.BroadcastReceiver.Battery;
import com.example.dack.BroadcastReceiver.Internet;
import com.example.dack.SharedPreferences.SharedPreferencesManager;
import com.example.dack.databinding.ActivityMainBinding;
import com.example.dack.models.Sanpham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ActionBarDrawerToggle toggle;
    private ArrayList<Sanpham> lstsanpham;
    SharedPreferencesManager sharedPreferencesManager;
    Internet internetbv;
    Battery batterylow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferencesManager = new SharedPreferencesManager(MainActivity.this);
        ActionBar();
        ImageSlider();
        getData();
        checkInternet();
        checkBattery();

        binding.listsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ChitietspActivity.class);
                intent.putExtra("thongtinchitietsanpham",lstsanpham.get(position));
                startActivity(intent);
            }
        });
        binding.searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findData(newText);
                return false;
            }
        });
        binding.searchItem.clearFocus();
    }

    public void checkInternet(){
        internetbv = new Internet();
        registerReceiver(internetbv, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    public void checkBattery(){
        batterylow = new Battery();
        registerReceiver(batterylow, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
    private void ActionBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationView.bringToFront();
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_giohang){
                    startActivity(new Intent(MainActivity.this,GiohangActivity.class));
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(id == R.id.nav_donhang){
                    startActivity(new Intent(MainActivity.this,DonhangActivity.class));
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(id == R.id.nav_thongtinnguoidung){
                    startActivity(new Intent(MainActivity.this,ProfileUserActivity.class));
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(id == R.id.nav_lienhe){
                    startActivity(new Intent(MainActivity.this,LienheActivity.class));
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(id == R.id.nav_logout){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Đăng xuất")
                            .setMessage("Bạn chắc chắn đăng xuất")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sharedPreferencesManager.clear();
                                    sharedPreferencesManager.putBoolean("isLogin",false);
                                    startActivity(new Intent(MainActivity.this,SigninActivity.class));
                                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                                }
                            })
                            .setNegativeButton("Không",null)
                            .show();
                }
                return true;
            }
        });
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        loading(true);
        lstsanpham = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Sanpham")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                Sanpham sanpham = new Sanpham();
                                sanpham.setIdsp(queryDocumentSnapshot.getId());
                                sanpham.setTensp(queryDocumentSnapshot.getString("tensp"));
                                sanpham.setGiasp(Float.parseFloat(queryDocumentSnapshot.getString("giasp")));
                                sanpham.setMotasp(queryDocumentSnapshot.getString("motasp"));
                                sanpham.setHinhanhsp(queryDocumentSnapshot.getString("imagesp"));
                                lstsanpham.add(sanpham);
                            }
                            if(lstsanpham.size()>0){
                                loading(false);
                                binding.listsp.setAdapter(new SanphamAdapter(lstsanpham, MainActivity.this));
                            }else{
                                loading(false);
                                showToast("Không có dữ liệu");
                            }
                        }
                    }
                });
    }
    public void findData(String value){
        ArrayList<Sanpham> lstfindsanpham = new ArrayList<>();
        for(Sanpham sanpham : lstsanpham){
            if(sanpham.getTensp().toLowerCase().contains(value.toLowerCase())){
                lstfindsanpham.add(sanpham);
            }
        }
        if(lstfindsanpham.size()>0) {
            binding.listsp.setAdapter(new SanphamAdapter(lstfindsanpham, MainActivity.this));
        }
    }
    public void loading(Boolean isLoading) {
        if(isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void showToast(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }
    public void ImageSlider(){
        ArrayList<SlideModel> lstquangcao = new ArrayList<>();
        lstquangcao.add(new SlideModel(R.drawable.bannerqc1,null));
        lstquangcao.add(new SlideModel(R.drawable.bannerqc2,null));
        lstquangcao.add(new SlideModel(R.drawable.bannerqc3,null));
        lstquangcao.add(new SlideModel(R.drawable.bannerqc4,null));
        lstquangcao.add(new SlideModel(R.drawable.bannerqc5,null));
        binding.imageSlider.setImageList(lstquangcao, ScaleTypes.CENTER_CROP);

    }

}