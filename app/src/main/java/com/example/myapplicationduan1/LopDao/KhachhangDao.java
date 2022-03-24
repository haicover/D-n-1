package com.example.myapplicationduan1.LopDao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplicationduan1.LopModel.KhachHang;
import com.example.myapplicationduan1.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class KhachhangDao {
    SQLiteDatabase sqLitetv;
    CreateData createData;

    public KhachhangDao(Context context){
        createData = new CreateData(context);
        sqLitetv = createData.getWritableDatabase();
    }

    public long ADDTV(KhachHang khachHang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhachHang.COL_NAME_HOTEN, khachHang.getHoTenTV());
        contentValues.put(KhachHang.COL_NAME_NAMSINH, khachHang.getNamsinhTV());
        return sqLitetv.insert(KhachHang.TB_NAME, null, contentValues);
    }

    public int UPDATETV(KhachHang khachHang) {
        ContentValues values = new ContentValues();
        values.put(KhachHang.COL_NAME_HOTEN, khachHang.getHoTenTV());
        values.put(KhachHang.COL_NAME_NAMSINH, khachHang.getNamsinhTV());
        return sqLitetv.update(KhachHang.TB_NAME, values, "maTV=?", new String[]{String.valueOf(khachHang.getIDTV())});
    }

    public int DELETETV(KhachHang khachHang) {
        return sqLitetv.delete(KhachHang.TB_NAME, "maTV=?", new String[]{String.valueOf(khachHang.getIDTV())});
    }

    // get tất cả dữ liệu
    public List<KhachHang> GETTV() {
        String dl = "SELECT * FROM KhachHang";
        List<KhachHang> list = getdata(dl);
        return list;
    }

    // get dữ liệu theo id
    public KhachHang getId(String id) {
        String dl = "SELECT * FROM KhachHang WHERE maTV==? ";
        List<KhachHang> list = getdata(dl, id);
        return list.get(0);
    }
    // get dữ liệu nhiều tham số
    @SuppressLint("Range")
    private List<KhachHang> getdata(String dl, String... Arays) {
        List<KhachHang> list = new ArrayList<>();
        Cursor cursor = sqLitetv.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            KhachHang khachHang = new KhachHang();
            khachHang.setIDTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KhachHang.COL_NAME_ID))));
            khachHang.setHoTenTV(cursor.getString(cursor.getColumnIndex(KhachHang.COL_NAME_HOTEN)));
            khachHang.setNamsinhTV(cursor.getString(cursor.getColumnIndex(KhachHang.COL_NAME_NAMSINH)));
            list.add(khachHang);
        }
        return list;
    }
}
