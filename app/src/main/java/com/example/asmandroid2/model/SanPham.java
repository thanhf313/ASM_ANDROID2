package com.example.asmandroid2.model;

public class SanPham {
    private int maSP;
    private String tenSP;
    private int giaSP, slSP;

    public SanPham(int maSP, String tenSP, int giaSP, int slSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.slSP = slSP;
    }

    public SanPham() {
    } // HÀM TRỐNG

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getSlSP() {
        return slSP;
    }

    public void setSlSP(int slSP) {
        this.slSP = slSP;
    }
}
