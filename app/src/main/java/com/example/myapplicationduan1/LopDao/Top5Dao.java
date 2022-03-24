package com.example.myapplicationduan1.LopDao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplicationduan1.LopModel.SanPham;
import com.example.myapplicationduan1.LopModel.Top;
import com.example.myapplicationduan1.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class Top5Dao {
    CreateData createData;
    SQLiteDatabase liteDatabase;
    Context context;

    public Top5Dao(Context context){
        this.context = context;
        createData = new CreateData(context);
        liteDatabase = createData.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Top> GetTop() {
        // gới hạn 5 kết quả từ trên xuống
        String sql = "SELECT maSp , COUNT(maSp) AS soLuong FROM PhieuMua GROUP BY maSp ORDER BY soLuong DESC LIMIT 5";
        List<Top> list = new ArrayList<>();
        SanphamDao sanphamDao = new SanphamDao(context);
        Cursor cursor = liteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Top top = new Top();
            @SuppressLint("Range") SanPham sanPham = sanphamDao.getId(cursor.getString(cursor.getColumnIndex("maSp")));
            top.tensp= sanPham.getTensp();
            top.soluong=(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }
}
