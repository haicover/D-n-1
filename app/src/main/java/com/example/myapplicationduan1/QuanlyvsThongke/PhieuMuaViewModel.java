package com.example.myapplicationduan1.QuanlyvsThongke;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationduan1.LopDao.PhieuMuaDao;
import com.example.myapplicationduan1.LopModel.PhieuMua;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuaViewModel extends AndroidViewModel {
    MutableLiveData<List<PhieuMua>> liveData;
    PhieuMuaDao dao;

    public PhieuMuaViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        dao = new PhieuMuaDao(application);
    }

    public MutableLiveData<List<PhieuMua>> getLiveData() {
        loadData();
        return liveData;
    }

    public void loadData() {
        List<PhieuMua> list = new ArrayList<>();
        list = dao.GETPM();
        liveData.setValue(list);
    }
}
