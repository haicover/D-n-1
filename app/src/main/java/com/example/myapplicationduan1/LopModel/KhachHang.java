package com.example.myapplicationduan1.LopModel;

public class KhachHang {
    int IDTV;
    String HoTenTV;
    String NamsinhTV;

    public KhachHang() {
    }

    public KhachHang(int IDTV, String hoTenTV, String namsinhTV) {
        this.IDTV = IDTV;
        HoTenTV = hoTenTV;
        NamsinhTV = namsinhTV;
    }

    public int getIDTV() {
        return IDTV;
    }

    public void setIDTV(int IDTV) {
        this.IDTV = IDTV;
    }

    public String getHoTenTV() {
        return HoTenTV;
    }

    public void setHoTenTV(String hoTenTV) {
        HoTenTV = hoTenTV;
    }

    public String getNamsinhTV() {
        return NamsinhTV;
    }

    public void setNamsinhTV(String namsinhTV) {
        NamsinhTV = namsinhTV;
    }

    public static final String TB_NAME = "KhachHang";
    public static final String COL_NAME_ID = "maTV";
    public static final String COL_NAME_HOTEN = "hoTen";
    public static final String COL_NAME_NAMSINH = "namSinh";
}
