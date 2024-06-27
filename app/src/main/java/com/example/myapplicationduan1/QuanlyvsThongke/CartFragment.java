package com.example.myapplicationduan1.QuanlyvsThongke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationduan1.LopAdapter.Sp_Adapter;
import com.example.myapplicationduan1.LopDao.SanphamDao;
import com.example.myapplicationduan1.R;

import org.jetbrains.annotations.NotNull;

public class CartFragment extends Fragment {
    RecyclerView rcv_cart;
    Button btn_thanh_toan;
    SanphamDao dao;
    SpViewModel model;
    Sp_Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_cart, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcv_cart = view.findViewById(R.id.rcv_cart);
        btn_thanh_toan = view.findViewById(R.id.btn_thanh_toan);

        model = new ViewModelProvider(this).get(SpViewModel.class);
        model.getListCartLiveData().observe(getViewLifecycleOwner(), data -> {
            adapter = new Sp_Adapter(getActivity(), data, dao, 1);
            rcv_cart.setAdapter(adapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
