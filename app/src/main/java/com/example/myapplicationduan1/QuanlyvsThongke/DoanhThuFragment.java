package com.example.myapplicationduan1.QuanlyvsThongke;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapplicationduan1.LopDao.PhieuMuaDao;
import com.example.myapplicationduan1.R;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DoanhThuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thongkedoanhthu, container, false);
    }

    PhieuMuaDao phieuMuaDao;
    EditText ed_tungay, ed_dengay;
    ImageView img_tungay, img_dengay;
    AppCompatButton btn_xn;
    TextView tv_doanhthu;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_tungay = view.findViewById(R.id.ed_tungay);
        ed_dengay = view.findViewById(R.id.ed_denngay);
        img_tungay = view.findViewById(R.id.img_tungay);
        img_dengay = view.findViewById(R.id.img_denngay);
        btn_xn = view.findViewById(R.id.btn_check);
        tv_doanhthu = view.findViewById(R.id.tv_hienthikq);
        img_tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        // hàm để lấy ngày tháng
                        calendar.set(year, month, dayOfMonth);

                        ed_tungay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        img_dengay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        // hàm để lấy ngày tháng
                        calendar.set(year, month, dayOfMonth);

                        ed_dengay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }

        });
        btn_xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuaDao = new PhieuMuaDao(getActivity());
                int doanhthu;
                doanhthu = phieuMuaDao.laygiatritheongay(ed_tungay.getText().toString(), ed_dengay.getText().toString());
                Locale locale = new Locale("nv", "VN");
                NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
                String tienfomat = nf.format(doanhthu);
                tv_doanhthu.setText( tienfomat);
            }
        });
    }
}
