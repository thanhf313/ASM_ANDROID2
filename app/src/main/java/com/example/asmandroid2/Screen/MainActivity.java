package com.example.asmandroid2.Screen;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.asmandroid2.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // khai báo và ánh xạ
        NavigationView navigationView = findViewById(R.id.navigationView);
        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView ic = findViewById(R.id.imgIC);
        Toolbar toolbar = findViewById(R.id.Toolbar);

        // thiết lập thanh công cụ  là thanh ứng dụng  chính của activity
        setSupportActionBar(toolbar);
        // cho phep hiện thị nút back  trên thanh công cụ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set up Drawer Toggle (dùng để thay đổi hình ảnh của nút toggle trên thanh toolbar)
        // khi người dùng mở và đóng ( navigation drawer)
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close
        );
        // bật cn  hình ảnh của DrawerToggle trên toolbar
        // khi cn này được bật , hình ảnh mặc định của DrawerToggle sẽ hiển thị trên toolbar
        // điều này cho phép người dùng nhận biết được sự mở rộng và đóng vai trò navagation drawer thông qua hình ảnh
        drawerToggle.setDrawerIndicatorEnabled(true);

        // đồng bộ trạng thái của DrawerToggle với trạng thái DrawerLayout
        drawerToggle.syncState();
        // gắt kết DrawerToggle vs DrawerLayout
        drawerLayout.addDrawerListener(drawerToggle);
    }


}