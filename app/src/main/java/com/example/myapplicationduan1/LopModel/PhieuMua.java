package com.example.myapplicationduan1.LopModel;

public class PhieuMua {

    int maPM;
    String maNVpm;
    int maTVpm;
    int maSpm;
    int tienthue;
    String ngaymua;
    int trasp;
    public static final String TB_NAME_PM = "PhieuMua";
    public static final String COL_NAME_MAPM = "maPM";
    public static final String COL_NAME_MANVPM = "maNV";
    public static final String COL_NAME_MATVPM = "maTV";
    public static final String COL_NAME_MASPM = "maSp";
    public static final String COL_NAME_TIENTHUE = "tienThue";
    public static final String COL_NAME_NGAYMUA = "ngay";
    public static final String COL_NAME_TRASP = "traSp";

    public PhieuMua() {
    }

    public PhieuMua(int maPM, String maNVpm, int maTVpm, int maSpm, int tienthue, String ngaymua, int trasp) {
        this.maPM = maPM;
        this.maNVpm = maNVpm;
        this.maTVpm = maTVpm;
        this.maSpm = maSpm;
        this.tienthue = tienthue;
        this.ngaymua = ngaymua;
        this.trasp = trasp;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaNVpm() {
        return maNVpm;
    }

    public void setMaNVpm(String maNVpm) {
        this.maNVpm = maNVpm;
    }

    public int getMaTVpm() {
        return maTVpm;
    }

    public void setMaTVpm(int maTVpm) {
        this.maTVpm = maTVpm;
    }

    public int getMaSpm() {
        return maSpm;
    }

    public void setMaSpm(int maSpm) {
        this.maSpm = maSpm;
    }

    public int getTienthue() {
        return tienthue;
    }

    public void setTienthue(int tienthue) {
        this.tienthue = tienthue;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public int getTrasp() {
        return trasp;
    }

    public void setTrasp(int trasp) {
        this.trasp = trasp;
    }
}
