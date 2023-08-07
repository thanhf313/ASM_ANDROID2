package com.example.asmandroid2.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asmandroid2.R;
import com.example.asmandroid2.dao.SanPhamDao;
import com.example.asmandroid2.database.SanPhamAdapter;
import com.example.asmandroid2.model.SanPham;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    ImageView imgAddHinh;

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

        // hiện thị dữ liệu
        SanPhamDao sanPhamDao = new SanPhamDao(MainActivity.this);
        ArrayList<SanPham> list = sanPhamDao.getListSP();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // adapter đỗ dl
        SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(MainActivity.this, list, sanPhamDao);
        recyclerView.setAdapter(sanPhamAdapter);

        // set up sk vào item tương ứng trg navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.itLogout) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Thoát thành công", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        // bắt sk cho ic add
        ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi trên chính nó nên kh cần gọi biến môi trường
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = ((MainActivity.this).getLayoutInflater());
                View view = inflater.inflate(R.layout.dialog_add, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();

                TextInputEditText edtNameSP = view.findViewById(R.id.edtNameSP);
                TextInputEditText edtPriceSP = view.findViewById(R.id.edtPriceSP);
                TextInputEditText edtQuantitySP = view.findViewById(R.id.edtQuantitySP);

                EditText edtURL = view.findViewById(R.id.edtURL);
                Button btnInsertImage = view.findViewById(R.id.btnInsertImage);
                imgAddHinh = view.findViewById(R.id.imgAddHinh);

                // bắt sk cho btnInsertImage
                btnInsertImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = edtURL.getText().toString();
                        new FetchImage(url).start();
                    }
                });

                Button btnAdd = view.findViewById(R.id.btnAddSP);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtNameSP.getText().toString();
                        String price = edtPriceSP.getText().toString();
                        String quantity = edtQuantitySP.getText().toString();
                        String url = edtURL.getText().toString();

                        // kiểm tra việc nhập
                        if (name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                        } else {
                            SanPham sanPham = new SanPham();
                            sanPham.setTenSP(name);
                            sanPham.setGiaSP(Integer.parseInt(price));
                            sanPham.setSlSP(Integer.parseInt(quantity));
                            sanPham.setAvatar(url);

                            boolean check = sanPhamDao.addSP(sanPham);
                            if (check) {
                                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(sanPhamDao.getListSP());
                                sanPhamAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(MainActivity.this, "Thêm thất bại ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    public class FetchImage extends Thread {
        // thread ( luôn = dữ liệu): đc cung cấp bỏi androi - thực thi công việc (  vào trước xử lý trước )..
        // threadPool ngược lại thread:
        String URL; // sử lý link hình ảnh
        Bitmap bitmap;

        FetchImage(String URL) {
            this.URL = URL;
        }

        @Override
        public void run() {
            super.run();
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    // hiện thị dailog
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Đang tải hình xuống...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });
            InputStream inputStream = null; //đọc dữ liệu từ file hoặc nhận dữ liệu qua mạng.
            try {
                inputStream = new URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream); // xử lý độ họa,hình ảnh
            } catch (Exception e) {
                e.printStackTrace();
            }
            // xử lý hình ảnh trên dailog
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        imgAddHinh.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }
}