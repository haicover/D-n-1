package com.example.myapplicationduan1.QuanlyvsThongke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplicationduan1.LopAdapter.Top5_Adapter;
import com.example.myapplicationduan1.LopDao.Top5Dao;
import com.example.myapplicationduan1.LopModel.Top;
import com.example.myapplicationduan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Thongketo5Fragment extends Fragment {

    public Thongketo5Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thongketo5, container, false);
    }


    ListView lv_top;
    ArrayList<Top> list;
    Top5_Adapter top5_adapter;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_top = view.findViewById(R.id._lvthongke);
        Top5Dao top10Dao =new Top5Dao(getActivity());
        list = (ArrayList<Top>) top10Dao.GetTop();
        top5_adapter = new Top5_Adapter(getActivity(),this,list);
        lv_top.setAdapter(top5_adapter);
    }
}
