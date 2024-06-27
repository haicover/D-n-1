package com.example.myapplicationduan1.LopModel;

public class SanPham {
    int masp;
    String tensp;
    int giasp;
    int malsp;
    int soLuong;
    String nsx;

    public SanPham() {
    }

    public SanPham(int masp, String tensp, int giasp, int malsp, String nsx, int soLuong) {
        this.masp = masp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.malsp = malsp;
        this.nsx = nsx;
        this.soLuong = soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getMalsp() {
        return malsp;
    }

    public void setMalsp(int malsp) {
        this.malsp = malsp;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }

    public static final String TB_NAME = "SanPham";
    public static final String TB_NAME_CART = "GioHangSanPham";
    public static final String COL_NAME_MASP = "maSp";
    public static final String COL_NAME_NSX = "nsx";
    public static final String COL_NAME_TENSP = "tenSp";
    public static final String COL_NAME_GIASP = "giaThue";
    public static final String COL_NAME_MALSP = "maLoai";
    public static final String COL_SL = "soLuong";
}
