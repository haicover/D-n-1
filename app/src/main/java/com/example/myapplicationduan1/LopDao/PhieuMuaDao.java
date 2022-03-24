package com.example.myapplicationduan1.LopDao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplicationduan1.LopModel.PhieuMua;
import com.example.myapplicationduan1.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuaDao {
    CreateData createData;
    SQLiteDatabase liteDatabase;

    public PhieuMuaDao(Context context){
        createData = new CreateData(context);
        liteDatabase = createData.getWritableDatabase();
    }

    public long ADDPM(PhieuMua phieuMua) {
        ContentValues values = new ContentValues();
        values.put(PhieuMua.COL_NAME_MATVPM, phieuMua.getMaTVpm());
        values.put(PhieuMua.COL_NAME_MANVPM, phieuMua.getMaNVpm());
        values.put(PhieuMua.COL_NAME_MASPM, phieuMua.getMaSpm());
        values.put(PhieuMua.COL_NAME_NGAYMUA, phieuMua.getNgaymua());
        values.put(PhieuMua.COL_NAME_TIENTHUE, phieuMua.getTienthue());
        values.put(PhieuMua.COL_NAME_TRASP, phieuMua.getTrasp());
        return liteDatabase.insert("PhieuMua", null, values);
    }

    public int DELETEPM(PhieuMua phieuMua) {
        return liteDatabase.delete(PhieuMua.TB_NAME_PM, "maPM=?", new String[]{String.valueOf(phieuMua.getMaPM())});
    }

    public int UPDATEPM(PhieuMua phieuMua) {
        ContentValues values = new ContentValues();
        values.put(PhieuMua.COL_NAME_MAPM, phieuMua.getMaPM());
        values.put(PhieuMua.COL_NAME_MATVPM, phieuMua.getMaTVpm());
        values.put(PhieuMua.COL_NAME_MANVPM, phieuMua.getMaNVpm());
        values.put(PhieuMua.COL_NAME_MASPM, phieuMua.getMaSpm());
        values.put(PhieuMua.COL_NAME_NGAYMUA, phieuMua.getNgaymua());
        values.put(PhieuMua.COL_NAME_TIENTHUE, phieuMua.getTienthue());
        values.put(PhieuMua.COL_NAME_TRASP, phieuMua.getTrasp());
        return liteDatabase.update(PhieuMua.TB_NAME_PM, values, "maPM=?", new String[]{String.valueOf(phieuMua.getMaPM())});
    }

    //Lấy Phần tử Phiếu Mượn từ id cua PM trog Csdl
    public PhieuMua getID(String id) {
        String sql = "SELECT * FROM PhieuMua WHERE maPM=?";
        List<PhieuMua> list = getData(sql, id);
        return list.get(0);
    }

    public List<PhieuMua> GETPM() {
        String sql = "SELECT * FROM PhieuMua";
        List<PhieuMua> list = getData(sql);
        return list;
    }

    // getData viet kieu List để sd nhiều lần
    @SuppressLint("Range")
    private List<PhieuMua> getData(String sql, String... Arays) {
        List<PhieuMua> list = new ArrayList<>();
        Cursor cursor = liteDatabase.rawQuery(sql, Arays);
        while (cursor.moveToNext()) {
            PhieuMua phieuMua = new PhieuMua();
            phieuMua.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMua.COL_NAME_MAPM))));
            phieuMua.setMaNVpm(cursor.getString(cursor.getColumnIndex(PhieuMua.COL_NAME_MANVPM)));
            phieuMua.setMaTVpm(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMua.COL_NAME_MATVPM))));
            phieuMua.setMaSpm(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMua.COL_NAME_MASPM))));
            phieuMua.setNgaymua(cursor.getString(cursor.getColumnIndex(PhieuMua.COL_NAME_NGAYMUA)));
            phieuMua.setTienthue(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMua.COL_NAME_TIENTHUE))));
            phieuMua.setTrasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PhieuMua.COL_NAME_TRASP))));
            list.add(phieuMua);
        }
        return list;

    }

    public int laygiatritheongay(String tungay, String dengay) {
        Cursor cursor = liteDatabase.rawQuery("SELECT SUM(tienThue) FROM PhieuMua WHERE ngay >='" + tungay + "'AND ngay <='" + dengay + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }


}
