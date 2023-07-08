package com.example.asmandroid2.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmandroid2.R;
import com.example.asmandroid2.adapter.DbHelper;
import com.google.android.material.textfield.TextInputEditText;

public class ResetActivity extends AppCompatActivity {
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        TextInputEditText tvNewPass = findViewById(R.id.tvNewPass);
        TextInputEditText tvNewPass2 = findViewById(R.id.tvNewPass2);
        TextView tvName = findViewById(R.id.tvName);
        Button btnConfrim = findViewById(R.id.btnConfrim);

        // nhận dữ liệu đã được check từ ForgotActivity
        db = new DbHelper(this);
        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("username"));
        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String tenDNlayMK = tvName.getText().toString();
            String newPass = tvNewPass.getText().toString();
            String newPass2 = tvNewPass2.getText().toString();
            // kiểm tra nhập dữ liệu
                if (tenDNlayMK.isEmpty() || newPass.isEmpty() || newPass2.isEmpty() ){
                    Toast.makeText(ResetActivity.this, "Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else {
                    if (newPass2.compareTo(newPass)==0){
                                boolean check = db.updatePassword(tenDNlayMK,newPass);
                        if (check){
                            startActivity(new Intent(ResetActivity.this, LoginActivity.class));
                            Toast.makeText(ResetActivity.this,"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ResetActivity.this,"Đổi mật khẩu không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ResetActivity.this,"Mật khẩu và mật khẩu nhập lại không khớp",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}