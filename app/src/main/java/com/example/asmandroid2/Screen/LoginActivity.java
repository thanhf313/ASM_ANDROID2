package com.example.asmandroid2.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmandroid2.R;
import com.example.asmandroid2.adapter.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText tvUserNameLogin;
    TextInputEditText tvPassLogin;
    Button btnLogin;
    TextView tvForPass, tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvUserNameLogin = findViewById(R.id.tvUserNameLogin);
        tvPassLogin = findViewById(R.id.tvPassLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvForPass = findViewById(R.id.tvForPass);
        tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        // bắt sự kện btn
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = tvUserNameLogin.getText().toString();
                String matKhau = tvPassLogin.getText().toString();
                DbHelper  dbHelper = new DbHelper(LoginActivity.this);
                // kiểm tra nhập liệu
                if (tenDN.isEmpty() || matKhau.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập dữ liệu",Toast.LENGTH_SHORT).show();
                }else {
                    if(dbHelper.login(tenDN,matKhau) == 1){
                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        // lưu lại các tài khoản
                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tenDN",tenDN);
                        editor.apply();// lưu dữ liệu vào data với key và value
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // bắt sự kiên forgot pass
        tvForPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }
}