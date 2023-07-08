package com.example.asmandroid2.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asmandroid2.R;
import com.example.asmandroid2.adapter.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputEditText tvUserNameForgot;
    Button btnReset;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        tvUserNameForgot = findViewById(R.id.tvUserNameForgot);
        btnReset = findViewById(R.id.btnReset);
        db = new DbHelper(ForgotPasswordActivity.this);

        // sự kiện
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = tvUserNameForgot.getText().toString();
                boolean check = db.checkUserName(tenDN);
                if (check){
                    Intent intent = new Intent(ForgotPasswordActivity.this, ResetActivity.class);
                    //chuyển dữ liệu
                    intent.putExtra("username",tenDN);
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "Người dùng không tồn tại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}