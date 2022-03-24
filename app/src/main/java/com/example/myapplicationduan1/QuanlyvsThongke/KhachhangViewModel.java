package com.example.myapplicationduan1.QuanlyvsThongke;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationduan1.LopDao.KhachhangDao;
import com.example.myapplicationduan1.LopModel.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachhangViewModel extends AndroidViewModel {
    KhachhangDao vienDao;
    MutableLiveData<List<KhachHang>> liveData;


    public KhachhangViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        vienDao = new KhachhangDao(application);
    }

    public MutableLiveData<List<KhachHang>> getLiveData() {
        loaddl();
        return liveData;
    }

    public void loaddl() {
        List<KhachHang> list = new ArrayList<>();
        list = vienDao.GETTV();
        liveData.setValue(list);
    }
}
