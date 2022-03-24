package com.example.myapplicationduan1.QuanlyvsThongke;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationduan1.LopDao.LoaispDao;
import com.example.myapplicationduan1.LopModel.LoaiSanpham;

import java.util.ArrayList;
import java.util.List;

public class LoaiSpViewModel extends AndroidViewModel {
    LoaispDao lspDao;
    MutableLiveData<List<LoaiSanpham>> liveData;

    public LoaiSpViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        lspDao = new LoaispDao(application);
    }

    public MutableLiveData<List<LoaiSanpham>> getLiveData() {
        loadls();
        return liveData;

    }

    public void loadls() {
        List<LoaiSanpham> list = new ArrayList<>();
        list = lspDao.GETLS();
        liveData.setValue(list);
    }
}
