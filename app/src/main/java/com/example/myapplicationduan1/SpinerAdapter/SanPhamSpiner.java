package com.example.myapplicationduan1.SpinerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationduan1.LopModel.SanPham;
import com.example.myapplicationduan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SanPhamSpiner extends ArrayAdapter<SanPham> {
    Context context;
    ArrayList<SanPham> list;
    TextView tvTenSp;

    public SanPhamSpiner(@NonNull @NotNull Context context, ArrayList<SanPham> list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spiner_sp, null);
        }
        SanPham sanPham = list.get(position);
        if (sanPham != null) {
            tvTenSp = view.findViewById(R.id.tv_spiner_sach);
            tvTenSp.setText(sanPham.getTensp());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spiner_sp, null);
        }
        SanPham sanPham = list.get(position);
        if (sanPham != null) {
            tvTenSp = view.findViewById(R.id.tv_spiner_sach);
            tvTenSp.setText(sanPham.getTensp());
        }
        return view;
    }
}
