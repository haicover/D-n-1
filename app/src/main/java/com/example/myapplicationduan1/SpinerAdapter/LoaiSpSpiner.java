package com.example.myapplicationduan1.SpinerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationduan1.LopModel.LoaiSanpham;
import com.example.myapplicationduan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoaiSpSpiner extends ArrayAdapter<LoaiSanpham> {
    Context context;
    ArrayList<LoaiSanpham> list;

    public LoaiSpSpiner(@NonNull Context context, ArrayList<LoaiSanpham> list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_spiner_loaisp, null);
        }
        LoaiSanpham loaiSanpham = list.get(position);
        if (loaiSanpham != null) {
            TextView tv_ls = view.findViewById(R.id.tv_tenloaisach);
            tv_ls.setText(loaiSanpham.getTenLSp());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_spiner_loaisp, null);
        }
        LoaiSanpham loaiSanpham = list.get(position);
        if (loaiSanpham != null) {
            TextView tv_ls = view.findViewById(R.id.tv_tenloaisach);
            tv_ls.setText(loaiSanpham.getTenLSp());
        }
        return view;

    }
}
