package com.example.myapplicationduan1.LopDao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplicationduan1.LopModel.SanPham;
import com.example.myapplicationduan1.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class SanphamDao {
    SQLiteDatabase sqLiteDatabase;
    CreateData createData;

    public SanphamDao(Context context){
        createData = new CreateData(context);
        sqLiteDatabase = createData.getWritableDatabase();
    }

    public long ADDS(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put(SanPham.COL_NAME_MALSP, sanPham.getMalsp());
        values.put(SanPham.COL_NAME_TENSP, sanPham.getTensp());
        values.put(SanPham.COL_NAME_GIASP, sanPham.getGiasp());
        values.put(SanPham.COL_NAME_NSX, sanPham.getNsx());
        return sqLiteDatabase.insert(SanPham.TB_NAME, null, values);
    }

    public int DELETES(SanPham sanPham) {
        return sqLiteDatabase.delete(SanPham.TB_NAME, "maSp=?", new String[]{String.valueOf(sanPham.getMasp())});
    }

    public int UPDATES(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put(SanPham.COL_NAME_MALSP, sanPham.getMalsp());
        values.put(SanPham.COL_NAME_TENSP, sanPham.getTensp());
        values.put(SanPham.COL_NAME_GIASP, sanPham.getGiasp());
        values.put(SanPham.COL_NAME_NSX, sanPham.getNsx());
        return sqLiteDatabase.update(SanPham.TB_NAME, values, "maSp=?", new String[]{String.valueOf(sanPham.getMasp())});
    }

    public List<SanPham> GETS() {
        String dl = "SELECT * FROM SanPham";
        List<SanPham> list = getdata(dl);
        return list;
    }

    public SanPham getId(String id) {
        String sql = "SELECT * FROM SanPham WHERE maSp=?";
        List<SanPham> list = getdata(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<SanPham> getdata(String dl, String... Arays /* có hoặc không nhiều phần tử*/) {
        List<SanPham> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(dl, Arays);
        while (cursor.moveToNext()) {
            SanPham sanPham = new SanPham();
            sanPham.setMasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanPham.COL_NAME_MASP))));
            sanPham.setMalsp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanPham.COL_NAME_MALSP))));
            sanPham.setTensp(cursor.getString(cursor.getColumnIndex(SanPham.COL_NAME_TENSP)));
            sanPham.setNsx(cursor.getString(cursor.getColumnIndex(SanPham.COL_NAME_NSX)));
            sanPham.setGiasp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanPham.COL_NAME_GIASP))));
            list.add(sanPham);
        }
        return list;
    }
}
