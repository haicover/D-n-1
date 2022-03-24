package com.example.myapplicationduan1.QuanlyvsThongke;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationduan1.LopAdapter.Lsp_Adapter;
import com.example.myapplicationduan1.LopDao.LoaispDao;
import com.example.myapplicationduan1.LopModel.LoaiSanpham;
import com.example.myapplicationduan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuanlytheloaiFragment extends Fragment {
    RecyclerView rcl_ls;
    EditText ed_tenls;
    LoaispDao lsdao;
    Lsp_Adapter adapter;
    FloatingActionButton flb_addls;
    LoaiSpViewModel model;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_loaisp, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_ls = view.findViewById(R.id.rcl_loaisach);
        flb_addls = view.findViewById(R.id.flb_ls);
        lsdao = new LoaispDao(getActivity());
        RecyclerView.LayoutManager lymanage = new LinearLayoutManager(getActivity());
        rcl_ls.setLayoutManager(lymanage);
        model = new ViewModelProvider(this).get(LoaiSpViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<LoaiSanpham>>() {
            @Override
            public void onChanged(List<LoaiSanpham> loaiSaches) {
                adapter = new Lsp_Adapter(getActivity(), loaiSaches, lsdao);
                rcl_ls.setAdapter(adapter);
            }
        });
        flb_addls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.custom_add_loaisp, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view1);
                EditText ed_ls = (EditText) view1.findViewById(R.id.ed_editls);
                EditText ed_nhcc = (EditText) view1.findViewById(R.id.ed_nhcc);
                builder.setTitle("                Thêm Loại Sản phẩm");
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSanpham loaiSanpham = new LoaiSanpham();
                        loaiSanpham.setTenLSp(ed_ls.getText().toString());
                        loaiSanpham.setNhacc(ed_nhcc.getText().toString());
                        long kq = lsdao.ADDLS(loaiSanpham);
                        if (kq > 0) {
                            ed_ls.setText("");
                            ed_nhcc.setText("");
                            Toast.makeText(getActivity(), "Thêm Loại Sản phẩm Thành Công", Toast.LENGTH_SHORT).show();
                            model.getLiveData();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Tên Loại Sản phẩm Trùng Lặp \n Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
