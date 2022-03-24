package com.example.myapplicationduan1.LopModel;

public class LoaiSanpham {
    private int maLSp;
    private String tenLSp;
    private String nhacc;

    public String getNhacc() {
        return nhacc;
    }

    public void setNhacc(String nhacc) {
        this.nhacc = nhacc;
    }

    public static final String TB_NAME = "LoaiSanPham";
    public static final String COL_NAME_MALS = "maLoai";
    public static final String COL_NAME_TENLS = "tenLoai";
    public static final String COL_NAME_NCC = "nhacc";


    public LoaiSanpham() {
    }

    public LoaiSanpham(int maLSp, String tenLSp, String nhacc) {
        this.maLSp = maLSp;
        this.tenLSp = tenLSp;
        this.nhacc = nhacc;
    }

    public int getMaLSp() {
        return maLSp;
    }

    public void setMaLSp(int maLSp) {
        this.maLSp = maLSp;
    }

    public String getTenLSp() {
        return tenLSp;
    }

    public void setTenLSp(String tenLSp) {
        this.tenLSp = tenLSp;
    }
}
