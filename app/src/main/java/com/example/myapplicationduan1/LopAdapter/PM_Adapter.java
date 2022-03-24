package com.example.myapplicationduan1.LopAdapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationduan1.LopDao.KhachhangDao;
import com.example.myapplicationduan1.LopDao.PhieuMuaDao;
import com.example.myapplicationduan1.LopDao.SanphamDao;
import com.example.myapplicationduan1.LopModel.KhachHang;
import com.example.myapplicationduan1.LopModel.PhieuMua;
import com.example.myapplicationduan1.LopModel.SanPham;
import com.example.myapplicationduan1.R;
import com.example.myapplicationduan1.SpinerAdapter.KhachHangSpiner;
import com.example.myapplicationduan1.SpinerAdapter.SanPhamSpiner;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PM_Adapter extends RecyclerView.Adapter<PM_Adapter.PhieuMuahoder>{
    Context context;
    List<PhieuMua> list;
    PhieuMuaDao phieuMuaDao;
    SanPhamSpiner sanPhamSpiner;
    KhachHangSpiner khachHangSpiner;
    int maTV, maSp, Trasp, mst, mtvt;
    String maNV;
    KhachhangDao vienDao;
    ArrayList<KhachHang> vienArrayList;
    SanphamDao sanphamDaoe;
    ArrayList<SanPham> spArrayList;


    public PM_Adapter(Context context, List<PhieuMua> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhieuMuahoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_phieumua, parent, false);
        return new PhieuMuahoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuahoder holder, int position) {
        PhieuMua phieuMua = list.get(position);
        if (phieuMua == null) {
            return;
        } else {
            String hoTenTV = "";
            String tenSanpham = "";
            holder.tv_mapm.setText("Mã Phiếu: " + phieuMua.getMaPM() + "");
            try {
                KhachhangDao tv_dao = new KhachhangDao(context);
                SanphamDao sanphamDao = new SanphamDao(context);
                KhachHang tv = tv_dao.getId(phieuMua.getMaTVpm() + "");
                SanPham sanPham = sanphamDao.getId(phieuMua.getMaSpm() + "");
                hoTenTV = tv.getHoTenTV();
                tenSanpham = sanPham.getTensp();
            } catch (Exception e) {
                hoTenTV = "Đã xóa Khách hàng";
                tenSanpham = "Đã xóa Sản phẩm";
            }

            // lấy họ tên từ id
//            ThanhVienDao tv_dao = new ThanhVienDao(context);
//            ThanhVien tv = tv_dao.getId(phieuMuon.getMaTVpm() + "");
            holder.tv_matvpm.setText("Tên Khách hàng: " + hoTenTV + "");
            // lấy tên sách
//            SachDao sachDao = new SachDao(context);
//            Sach sach = sachDao.getId(phieuMuon.getMaSpm() + "");
            holder.tv_maspm.setText("Tên Sản phẩm: " + tenSanpham + "");
            // fomat tiền
            Locale locale = new Locale("nv", "VN");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            String tien = numberFormat.format(phieuMua.getTienthue());
            holder.tv_giamua.setText("Tiền Thuế: " + tien);
            holder.tv_ngaymua.setText("Ngày Mua: " + phieuMua.getNgaymua());
            if (phieuMua.getTrasp() == 1) {
                holder.tv_trasp.setText("Đã Thanh Toán");
                holder.tv_trasp.setTextColor(Color.BLUE);
            } else {
                holder.tv_trasp.setText("Chưa Thanh Toán");
                holder.tv_trasp.setTextColor(Color.RED);
            }
        }
        holder.img_dellpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete");
                builder.setIcon(R.drawable.ic_dele);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phieuMuaDao = new PhieuMuaDao(context);
                        int kq = phieuMuaDao.DELETEPM(phieuMua);
                        if (kq > 0) {
                            list.clear();
                            list.addAll(phieuMuaDao.GETPM());
                            // load lại dữ liệu
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        holder.img_ditpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.custom_edit_phieumua, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(view);
                builder.setTitle("Sửa Phiếu Mua");
                EditText ed_ngaymuoned = (EditText) view.findViewById(R.id.ed_ngaymuoned);
                EditText ed_tienthueed = (EditText) view.findViewById(R.id.ed_giamuoned);
                Spinner spn_tved = (Spinner) view.findViewById(R.id.spinnertved);
                Spinner spn_sached = (Spinner) view.findViewById(R.id.spinnersached);
                ImageView img_dateed = (ImageView) view.findViewById(R.id.img_liched);
                CheckBox chk_trasached = (CheckBox) view.findViewById(R.id.chk_sachtraed);
                ed_ngaymuoned.setText(phieuMua.getNgaymua());
                ed_tienthueed.setText(Integer.toString(phieuMua.getTienthue()));
                // hiển thị
                if (phieuMua.getTrasp() == 1) {
                    chk_trasached.setChecked(true);
                } else {
                    chk_trasached.setChecked(false);

                }
                // spiner khach hang
                vienDao = new KhachhangDao(view.getContext());
                vienArrayList = new ArrayList<>();
                vienArrayList = (ArrayList<KhachHang>) vienDao.GETTV();
                khachHangSpiner = new KhachHangSpiner(view.getContext(), vienArrayList);
                spn_tved.setAdapter(khachHangSpiner);
                spn_tved.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV = vienArrayList.get(position).getIDTV();
//                        Log.d("rrrrrrrr",vienArrayList.get(position).getIDTV()+"");
                        Toast.makeText(view.getContext(), "Khách hàng: " + vienArrayList.get(position).getHoTenTV(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // Spiner san pham
                sanphamDaoe = new SanphamDao(view.getContext());
                spArrayList = new ArrayList<>();
                spArrayList = (ArrayList<SanPham>) sanphamDaoe.GETS();
                sanPhamSpiner = new SanPhamSpiner(view.getContext(), spArrayList);
                spn_sached.setAdapter(sanPhamSpiner);
                spn_sached.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maSp = spArrayList.get(position).getMasp();
                        Toast.makeText(view.getContext(), "Chọn sản phẩm: " + spArrayList.get(position).getTensp(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mst = 0;
                for (int i = 0; i < spArrayList.size(); i++) {
                    if (phieuMua.getMaSpm() == spArrayList.get(i).getMasp()) {
                        mst = i;
                    }
                }
                spn_sached.setSelection(mst);
                mtvt = 0;
                for (int i = 0; i < vienArrayList.size(); i++) {
                    if (phieuMua.getMaTVpm() == vienArrayList.get(i).getIDTV()) {
                        mtvt = i;
                    }
                }
                spn_tved.setSelection(mtvt);
                // date pikerdialog
                img_dateed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                calendar.set(year, month, dayOfMonth);
                                ed_ngaymuoned.setText(sdf.format(calendar.getTime()));
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }
                });
//                Intent intent = Contextm.getIntent();
//                maNV = intent.getStringExtra("admintion");
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (chk_trasached.isChecked()) {
                            Trasp = 1;
                        } else {
                            Trasp = 0;
                        }
                        if (phieuMua.getNgaymua().equals(ed_ngaymuoned.getText().toString())
                                && phieuMua.getTienthue() == Integer.parseInt(ed_tienthueed.getText().toString())
                                && phieuMua.getMaTVpm() == mtvt
                                && phieuMua.getMaSpm() == mst
                                && phieuMua.getTrasp() == phieuMua.getTrasp()) {

                            Toast.makeText(view.getContext(), "Không có thay đổi \n Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                        } else {
                            if (checkNgay(ed_ngaymuoned) && checkgiatien(ed_tienthueed)) {

                                //  PhieuMuon phieuMuon = new PhieuMuon();
//                                phieuMuon.setMaNVpm(maNV);
                                phieuMua.setMaTVpm(maTV);
                                phieuMua.setMaSpm(maSp);
                                phieuMua.setNgaymua(ed_ngaymuoned.getText().toString());
                                phieuMua.setTienthue(Integer.parseInt(ed_tienthueed.getText().toString()));
                                phieuMua.setTrasp(Trasp);
                                long kq = phieuMuaDao.UPDATEPM(phieuMua);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(phieuMuaDao.GETPM());
                                    Toast.makeText(view.getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(view.getContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public boolean checkNgay(EditText ed) {
        Date date = null;
        String value = ed.getText().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
                Toast.makeText(context.getApplicationContext(), "Sai dịnh dạng ngày!", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public boolean checkgiatien(EditText ed) {
        boolean check = true;
        try {
            Integer.parseInt(ed.getText().toString());
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), "Tiền thuế phải là số", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }


    public class PhieuMuahoder extends RecyclerView.ViewHolder{
        TextView tv_mapm, tv_matvpm, tv_maspm, tv_ngaymua, tv_giamua, tv_trasp;
        ImageView img_dellpm, img_ditpm;
        ConstraintLayout cnt_pm;
        public PhieuMuahoder(@NonNull View itemView) {
            super(itemView);

            tv_mapm = itemView.findViewById(R.id.tv_maphieumuon);
            tv_matvpm = itemView.findViewById(R.id.tv_maTVmuon);
            tv_maspm = itemView.findViewById(R.id.tv_masachphieumuon);
            tv_ngaymua = itemView.findViewById(R.id.tv_ngaymuon);
            tv_giamua = itemView.findViewById(R.id.tv_giathuesach);
            tv_trasp = itemView.findViewById(R.id.tv_checktrasach);
            img_dellpm = itemView.findViewById(R.id.img_delete_pm);
            img_ditpm = itemView.findViewById(R.id.img_edit_pm);
            cnt_pm = itemView.findViewById(R.id.cns_pm);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            cnt_pm.setAnimation(animation);

            phieuMuaDao = new PhieuMuaDao(context);
        }
    }
}
