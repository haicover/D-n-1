package com.example.myapplicationduan1.LopDao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplicationduan1.LopModel.LoaiSanpham;
import com.example.myapplicationduan1.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class LoaispDao {
    CreateData createData;
    SQLiteDatabase liteDatabase;

    public LoaispDao(Context context){
        createData = new CreateData(context);
        liteDatabase = createData.getWritableDatabase();
    }

    public long ADDLS(LoaiSanpham loaiSanpham) {
        ContentValues values = new ContentValues();
        values.put(LoaiSanpham.COL_NAME_TENLS, loaiSanpham.getTenLSp());
        values.put(LoaiSanpham.COL_NAME_NCC, loaiSanpham.getNhacc());
        return liteDatabase.insert(LoaiSanpham.TB_NAME, null, values);
    }

    public int UPDATELS(LoaiSanpham loaiSanpham) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiSanpham.COL_NAME_TENLS, loaiSanpham.getTenLSp());
        contentValues.put(LoaiSanpham.COL_NAME_NCC, loaiSanpham.getNhacc());
        return liteDatabase.update(LoaiSanpham.TB_NAME, contentValues, "maLoai=?", new String[]{String.valueOf(loaiSanpham.getMaLSp())});
    }

    public int DELETELS(LoaiSanpham loaiSanpham) {
        return liteDatabase.delete(LoaiSanpham.TB_NAME, "maLoai=?", new String[]{String.valueOf(loaiSanpham.getMaLSp())});
    }

    public List<LoaiSanpham> GETLS() {
        String dl = "SELECT * FROM LoaiSanPham";
        List<LoaiSanpham> list = getdata(dl);
        return list;
    }

    public LoaiSanpham getId(String id) {
        String sql = "SELECT * FROM LoaiSanPham WHERE maLoai=?";
        List<LoaiSanpham> list = getdata(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<LoaiSanpham> getdata(String dl, String... Arays) {
        List<LoaiSanpham> list = new ArrayList<>();
        Cursor cursor = liteDatabase.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            LoaiSanpham loaiSanpham = new LoaiSanpham();
            loaiSanpham.setMaLSp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(LoaiSanpham.COL_NAME_MALS))));
            loaiSanpham.setTenLSp(cursor.getString(cursor.getColumnIndex(LoaiSanpham.COL_NAME_TENLS)));
           loaiSanpham.setNhacc(cursor.getString(cursor.getColumnIndex(LoaiSanpham.COL_NAME_NCC)));
            list.add(loaiSanpham);
        }
        return list;

    }
}
