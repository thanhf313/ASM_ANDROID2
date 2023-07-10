package com.example.asmandroid2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asmandroid2.adapter.DbHelper;
import com.example.asmandroid2.model.SanPham;

import java.util.ArrayList;

public class SanPhamDao {
    private final DbHelper dbHelper;

    public SanPhamDao(Context context) {
        this.dbHelper = new DbHelper(context);

    }

    // hàm hiện thị sản phẩm
    public ArrayList<SanPham> getListSP() {
        // TẠO 1 DANH SÁCH ĐỂ ADD VÀO DỮ LIỆU VÀO SẢN PHẨM
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("select * from ProDucts", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst(); // đưa con trỏ về cị trí đầu tiên
                do {
                    list.add(new SanPham(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful(); // database đã thành công
            }
        } catch (Exception e) {
            Log.e("Error", "getListSP:" + e);
        }finally {
            database.endTransaction();
        }
        return list;
    }

    // theem sản phảm
    public boolean addSP(SanPham sanPham) {
        SQLiteDatabase database = dbHelper.getWritableDatabase(); // ghi vào csdl
        ContentValues contentValues = new ContentValues(); // đẩy dl
        contentValues.put("tenSP", sanPham.getTenSP());
        contentValues.put("giaSP", String.valueOf(sanPham.getGiaSP()));
        contentValues.put("slSP", String.valueOf(sanPham.getSlSP()));

        long check = database.insert("ProDucts", null, contentValues);
        return check != -1;
    }

    // hàm xóa sản phẩm
    public boolean deleteSP(int maSP) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("ProDucts", "maSP=?", new String[]{String.valueOf(maSP)});
        return check != -1;
    }

    // hàm update
    public boolean updateSP(SanPham sanPham) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSP", sanPham.getTenSP());
        contentValues.put("giaSP", String.valueOf(sanPham.getGiaSP()));
        contentValues.put("SLSP", String.valueOf(sanPham.getSlSP()));

        long check = database.update("ProDucts", contentValues, "maSP=?",
                new String[]{String.valueOf(sanPham.getMaSP())});
        return check != -1;
    }
}
