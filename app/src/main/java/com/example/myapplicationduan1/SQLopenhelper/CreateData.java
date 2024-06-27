package com.example.myapplicationduan1.SQLopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateData extends SQLiteOpenHelper {

    private static final String DB_NAME = "MiMi.db";
    private static final int VERSION = 2;

    public CreateData(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Bảng Nhân Viên
        String tb_nv =
                "create table NhanVien (" +
                        "maNV TEXT PRIMARY KEY UNIQUE, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL)";
        db.execSQL(tb_nv);
        //Bảng Khach hang
        String tb_tv =
                "create table KhachHang (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hoTen TEXT NOT NULL, " +
                        "namSinh TEXT NOT NULL)";
        db.execSQL(tb_tv);
        //Bảng Loại sp
        String tb_ls =
                "create table LoaiSanPham (" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoai TEXT UNIQUE NOT NULL,  " +
                        "nhacc TEXT NOT NULL)";
        db.execSQL(tb_ls);
        //Bảng San Pham
        String tb_S =
                "create table SanPham (" +
                        "maSp INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSp TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, " +
                        "nsx TEXT NOT NULL, " +
                        "soLuong INTEGER NOT NULL, " +
                        "maLoai INTEGER REFERENCES LoaiSanPham(maLoai))";
        db.execSQL(tb_S);
        //Bảng giỏ hàng
        String tb_cart =
                "create table GioHangSanPham (" +
                        "maSp INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSp TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, " +
                        "nsx TEXT NOT NULL, " +
                        "soLuong INTEGER NOT NULL, " +
                        "maLoai INTEGER REFERENCES LoaiSanPham(maLoai))";
        db.execSQL(tb_cart);

        //Bảng Phiếu Mua
        String tb_pm =
                "create table PhieuMua (" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maNV TEXT REFERENCES NhanVien(maNV), " +
                        "maTV INTEGER REFERENCES KhachHang(maTV), " +
                        "maSp INTEGER REFERENCES SanPham(maSp), " +
                        "tienThue INTEGER NOT NULL, " +
                        "ngay DATE NOT NULL, " +
                        "traSp INTEGER NOT NULL)";
        db.execSQL(tb_pm);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Xóa khi update phiên bản
        String dropTableThuThu = "drop table if exists NhanVien";
        db.execSQL(dropTableThuThu);
        String dropTableThanhVien = "drop table if exists KhachHang";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSp = "drop table if exists LoaiSanPham";
        db.execSQL(dropTableLoaiSp);
        String dropTableSp = "drop table if exists SanPham";
        db.execSQL(dropTableSp);
        String dropTableCart = "drop table if exists GioHangSanPhamGioHangSanPham";
        db.execSQL(dropTableCart);
        String dropTablePhieuMua = "drop table if exists PhieuMua";
        db.execSQL(dropTablePhieuMua);

        onCreate(db);
    }
}
