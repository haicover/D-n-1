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

    public SanphamDao(Context context) {
        createData = new CreateData(context);
        sqLiteDatabase = createData.getWritableDatabase();
    }

    public long ADDS(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put(SanPham.COL_NAME_MALSP, sanPham.getMalsp());
        values.put(SanPham.COL_NAME_TENSP, sanPham.getTensp());
        values.put(SanPham.COL_NAME_GIASP, sanPham.getGiasp());
        values.put(SanPham.COL_NAME_NSX, sanPham.getNsx());
        values.put(SanPham.COL_SL, 1);
        return sqLiteDatabase.insert(SanPham.TB_NAME, null, values);
    }

    public long AddCarts(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put(SanPham.COL_NAME_MALSP, sanPham.getMalsp());
        values.put(SanPham.COL_NAME_TENSP, sanPham.getTensp());
        values.put(SanPham.COL_NAME_GIASP, sanPham.getGiasp());
        values.put(SanPham.COL_NAME_NSX, sanPham.getNsx());
        values.put(SanPham.COL_SL, sanPham.getSoLuong());
        return sqLiteDatabase.insert(SanPham.TB_NAME_CART, null, values);
    }

    public List<SanPham> GetDataCart() {
        String dl = "SELECT * FROM " + SanPham.TB_NAME_CART;
        List<SanPham> list = getdata(dl);
        return list;
    }

    public int DeleteCart(SanPham sanPham) {
        return sqLiteDatabase.delete(SanPham.TB_NAME_CART, "maSp=?", new String[]{String.valueOf(sanPham.getMasp())});
    }

    public boolean isTableExist(SanPham sanPham) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GioHangSanPham WHERE tenSp=? AND giaThue=? AND nsx=? AND maLoai=?", new String[]{String.valueOf(sanPham.getTensp()), String.valueOf(sanPham.getGiasp()), String.valueOf(sanPham.getNsx()), String.valueOf(sanPham.getMalsp())});
        boolean tableExist = (cursor.getCount() != 0);
        cursor.close();
        return tableExist;
    }

    public int updateCart(SanPham sanPham) {
        String sql = "SELECT * FROM GioHangSanPham WHERE tenSp=? AND giaThue=? AND nsx=? AND maLoai=?";
        List<SanPham> list = getdata(sql, sanPham.getTensp(), String.valueOf(sanPham.getGiasp()), sanPham.getNsx(), String.valueOf(sanPham.getMalsp()));

        SanPham sanPhamUpdate = list.get(0);

        ContentValues values = new ContentValues();
        values.put(SanPham.COL_NAME_MALSP, sanPhamUpdate.getMalsp());
        values.put(SanPham.COL_NAME_TENSP, sanPhamUpdate.getTensp());
        values.put(SanPham.COL_NAME_GIASP, sanPhamUpdate.getGiasp());
        values.put(SanPham.COL_NAME_NSX, sanPhamUpdate.getNsx());
        values.put(SanPham.COL_SL, sanPhamUpdate.getSoLuong() + 1);
        return sqLiteDatabase.update(SanPham.TB_NAME_CART, values, "maSp=?", new String[]{String.valueOf(sanPhamUpdate.getMasp())});
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
            sanPham.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SanPham.COL_SL))));
            list.add(sanPham);
        }
        return list;
    }

    public List<SanPham> getSearch(String textSearch) {
        String sql = "SELECT * FROM SanPham WHERE tenSp LIKE '%" + textSearch + "%'";
        return getdata(sql);
    }
}
