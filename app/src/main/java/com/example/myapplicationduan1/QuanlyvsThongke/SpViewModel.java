package com.example.myapplicationduan1.QuanlyvsThongke;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationduan1.LopDao.SanphamDao;
import com.example.myapplicationduan1.LopModel.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SpViewModel extends AndroidViewModel {
    SanphamDao dao;
    MutableLiveData<List<SanPham>> liveData;
    MutableLiveData<List<SanPham>> listCartLiveData;

    public SpViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        listCartLiveData = new MutableLiveData<>();
        dao = new SanphamDao(application);
    }

    public MutableLiveData<List<SanPham>> getListCartLiveData() {
        List<SanPham> list ;
        list = dao.GetDataCart();
        listCartLiveData.setValue(list);
        return listCartLiveData;
    }

    public MutableLiveData<List<SanPham>> getLiveData() {
        loads();
        return liveData;
    }

    private void loads() {
        List<SanPham> list = new ArrayList<>();
        list = dao.GETS();
        liveData.setValue(list);
    }
}
