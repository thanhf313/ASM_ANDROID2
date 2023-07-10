package com.example.asmandroid2.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "QLSP", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qND = "create table NguoiDung(tenDN text primary key, matKhau text , hoTen text )";
        db.execSQL(qND);

        String qSP = "create table ProDucts(maSP integer primary key autoincrement,tenSP text,giaSP integer, slSP integer)";
        db.execSQL(qSP);
        // nạp dự liệu table products
        String data = "insert into ProDucts values (1,'Bánh quy bơ Lu Pháp',45000,10)," +
                "(2,'Snack mực lăn muối ớt',8000,52)," +
                "(3,'Snack khoai tây',12000,38)," +
                "(4,'Bánh gạo One One',30000,11)," +
                "(5,'Kẹo sữa sô cô la',25000,30)";
        db.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // xóa những bảng cũ
        db.execSQL("drop table if exists NguoiDung");
        db.execSQL("drop table if exists ProDucts");
    }

    // hàm register
    public void register(String tenDN, String matKhau, String hoTen) {
        ContentValues contentValues = new ContentValues(); //ĐƯA dữ liệu vào database
        contentValues.put("tenDN", tenDN);
        contentValues.put("matKhau", matKhau);
        contentValues.put("hoTen", hoTen);
        SQLiteDatabase db = getWritableDatabase(); // ghi  data
        db.insert("NguoiDung", null, contentValues); // khai báo bảng
        db.close(); // đóng dữ liệu
    }

    // hàm login
    public int login(String tenDN, String matkhau) {
        int result = 0;
        String str[] = new String[2]; // có 2 biến
        str[0] = tenDN;
        str[1] = matkhau;
        SQLiteDatabase db = getReadableDatabase(); // chỉ kiểm tra
        Cursor c = db.rawQuery("select * from NguoiDung where tenDN = ? and matKhau =?", str); // để so sánh thông tin mình chuyền vào
        if (c.moveToNext()) {
            result = 1;
        }
        return result;
    }

    // hàm check user name
    public boolean checkUserName(String tenDN) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from NguoiDung where tenDN=?", new String[]{tenDN}); // dùng biến con trỏ để xem nó có tồn tại không
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    // hàm update password
    public  boolean updatePassword(String tenDN, String MK){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matkhau",MK);
        // phưowng thức put để gán giá trị mới cho mỗi cột cần cập nhật
        long result = sqLiteDatabase.update("NguoiDung",contentValues,"tenDN=?",new String[]{tenDN});
        // để kiểm tra
        return result != -1;
    }
}
