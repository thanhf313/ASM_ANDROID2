package com.example.asmandroid2.Screen;

import static android.opengl.ETC1.isValid;

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

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText tvUserName;
    TextInputEditText tvFullName;
    TextInputEditText tvPass;
    TextInputEditText tvPass2;
    Button btnRegister;
    TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUserName = findViewById(R.id.tvUserName);
        tvFullName = findViewById(R.id.tvFullName);
        tvPass = findViewById(R.id.tvPass);
        tvPass2 = findViewById(R.id.tvPass2);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        // bắt sự kiện btn
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = tvUserName.getText().toString();
                String hoTen = tvFullName.getText().toString();
                String matKhau = tvPass.getText().toString();
                String matKhauLan2 = tvPass2.getText().toString();

                // gọi dữ liệu data
                DbHelper dbHelper = new DbHelper(RegisterActivity.this);
                // kiểm tra thông tin
                if (tenDN.isEmpty() || hoTen.isEmpty() || matKhau.isEmpty() || matKhauLan2.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill all details",Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = dbHelper.checkUserName(tenDN);
                 if (check){
                     tvUserName.setError("user already exists..");
                 }else {
                     if (matKhauLan2.compareTo(matKhau) == 0){
                         if (isValid(matKhau)){
                             dbHelper.register(tenDN,matKhau,hoTen);
                             Toast.makeText(RegisterActivity.this,"Đã lưu vào bản ghi",Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                         }else {
                             Toast.makeText(RegisterActivity.this,"Mật khẩu phải có ít nhất 8 kí từ,có chữ in hoa,ít nhất 1 số",
                                     Toast.LENGTH_SHORT).show();
                         }
                     }else {
                         Toast.makeText(RegisterActivity.this,"Mật khẩu nhập lại không đúng?",
                                 Toast.LENGTH_SHORT).show();
                     }
                 }
                }
            }
        });

    }

    // hàm kiểm tra tính hợp lệ của pass
    private static boolean isValid(String matKhau) {
        int flag1 =0, flag2 = 0,flag3=0;
        if (matKhau.length()<0){
            return false;
        }else {
            for (int p =0; p<matKhau.length();p++){
                // kiểm tra ký tự hiện tại có phải chữ cái kh
                // character lấy kí tự hiện tại
                if(Character.isLetter(matKhau.charAt(p))){
                    flag1 = 1;
                }
            }
            for (int r = 0; r <matKhau.length();r++){
                // kiểm tra kí tự hiện tại có phải là 1 chữ số kh
                if( Character.isDigit(matKhau.charAt(r))){
                    flag2 = 1;
                }
            }
            for (int s = 0; s <matKhau.length();s++){
                // lấy kí từ hiện tại
                char c  = matKhau.charAt(s);
                // kiểm tra kí tự hiện tại có phải là kí từ đặc biệt  kh
                // mã củ kí từ
                if( c >= 33 && c<= 46 || c == 64){
                    flag3 = 1;
                }
            }
            if (flag1 == 1 && flag2 == 1 && flag3 ==1){
                return  true;

            }else {
                return false;
            }
        }
    }
}