package com.example.myapplicationduan1.QuanlyvsThongke;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationduan1.LopAdapter.Sp_Adapter;
import com.example.myapplicationduan1.LopDao.LoaispDao;
import com.example.myapplicationduan1.LopDao.SanphamDao;
import com.example.myapplicationduan1.LopModel.LoaiSanpham;
import com.example.myapplicationduan1.LopModel.SanPham;
import com.example.myapplicationduan1.R;
import com.example.myapplicationduan1.SpinerAdapter.LoaiSpSpiner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuanlyspFragment extends Fragment {
    RecyclerView rcl_sp;
    FloatingActionButton flb_sp;
    ArrayList<LoaiSanpham> loaiSpches;
    int mals;
    LoaispDao loaispDao;
    LoaiSpSpiner spSpiner;
    SanphamDao dao;
    SpViewModel model;
    Sp_Adapter adapter;
    SearchView searchView;
    ArrayList<SanPham> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sp, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_sp = (RecyclerView) view.findViewById(R.id.rcl_sach);
        flb_sp = (FloatingActionButton) view.findViewById(R.id.fbl_ads);
        dao = new SanphamDao(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl_sp.setLayoutManager(layoutManager);
        model = new ViewModelProvider(this).get(SpViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<SanPham>>() {
            @Override
            public void onChanged(List<SanPham> saches) {
                adapter = new Sp_Adapter(getActivity(), saches, dao, 0);
                rcl_sp.setAdapter(adapter);
//                rcl_sach.setItemAnimator(new ScaleInAnimator());
//        //        adapter = new StudentAdapter(students, this);
//              rcl_sach.setAdapter(new ScaleInAnimationAdapter(adapter));
            }
        });

        searchView = view.findViewById(R.id.id_serch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        flb_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.custom_add_sp, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                EditText ed_tens = (EditText) view.findViewById(R.id.tensach);
                Spinner spns = (Spinner) view.findViewById(R.id.spin_lsach);
                EditText ed_gias = (EditText) view.findViewById(R.id.giasach);
                EditText ed_tacgia = (EditText) view.findViewById(R.id.ed_tacgia);
                builder.setTitle("                Thêm Sản phẩm");
                loaiSpches = new ArrayList<>();
                loaispDao = new LoaispDao(getContext());
                loaiSpches = (ArrayList<LoaiSanpham>) loaispDao.GETLS();
                spSpiner = new LoaiSpSpiner(getActivity(), loaiSpches);
                spns.setAdapter(spSpiner);
                spns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mals = loaiSpches.get(position).getMaLSp();
                        Toast.makeText(getContext(), "Chọn" + loaiSpches.get(position).getTenLSp(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ed_tens.getText().length() == 0 || ed_gias.getText().length() == 0 || ed_gias.getText().length() == 0) {
                            Toast.makeText(getContext(), "Bạn cần phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                int giaThue = Integer.parseInt(ed_gias.getText().toString());
                                SanPham sanPham = new SanPham();
                                sanPham.setTensp(ed_tens.getText().toString());
                                sanPham.setNsx(ed_tacgia.getText().toString());
                                sanPham.setGiasp(giaThue);
                                sanPham.setMalsp(mals);
                                long kq = dao.ADDS(sanPham);
                                if (kq > 0) {
                                    Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    ed_tens.setText("");
                                    ed_gias.setText("");
                                    ed_tacgia.setText("");
                                    spns.setSelection(0);
                                    model.getLiveData();
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Giá thuế phải là số", Toast.LENGTH_SHORT).show();
                            }
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
