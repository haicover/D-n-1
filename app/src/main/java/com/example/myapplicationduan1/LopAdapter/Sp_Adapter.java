package com.example.myapplicationduan1.LopAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationduan1.LopDao.LoaispDao;
import com.example.myapplicationduan1.LopDao.SanphamDao;
import com.example.myapplicationduan1.LopModel.LoaiSanpham;
import com.example.myapplicationduan1.LopModel.SanPham;
import com.example.myapplicationduan1.R;
import com.example.myapplicationduan1.SpinerAdapter.LoaiSpSpiner;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Sp_Adapter extends RecyclerView.Adapter<Sp_Adapter.SpHoder> implements Filterable {
    Context context;
    List<SanPham> list;
    SanphamDao dao;
    int ms, mst;
    ArrayList<LoaiSanpham> loaiSpches;
    LoaispDao loaispDao;
    List<SanPham> mlistOld;
    int typeLoad;

    public Sp_Adapter(Context context, List<SanPham> list, SanphamDao dao, int typeLoad) {
        this.context = context;
        this.list = list;
        this.dao = dao;
        this.mlistOld = list;
        this.typeLoad = typeLoad;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    list = mlistOld;
                } else {
                    List<SanPham> listtv = new ArrayList<>();
                    for (SanPham sanPham : mlistOld) {
                        if (String.valueOf(sanPham.getMasp()).contains(strSearch)) {
                            listtv.add(sanPham);
                        }
                    }
                    list = listtv;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<SanPham>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public SpHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_sp, parent, false);
        return new SpHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpHoder holder, int position) {
        SanPham sanPham = list.get(position);
        if (sanPham == null) {
            return;
        } else {

            if (typeLoad == 1) {
                holder.img_add.setVisibility(View.GONE);
                holder.img_edits.setVisibility(View.GONE);
                holder.tv_ms.setVisibility(View.GONE);
            } else {
                holder.tv_total.setVisibility(View.GONE);
            }

            String tenLoai;
            try {
                LoaispDao loaiSpDao = new LoaispDao(context);
                LoaiSanpham loaiSanpham = loaiSpDao.getId(String.valueOf(sanPham.getMalsp()));
                tenLoai = loaiSanpham.getTenLSp();
            } catch (Exception e) {
                tenLoai = "Đã xóa loại sản phẩm";
            }

            holder.tv_ms.setText("Mã Sản phẩm: " + sanPham.getMasp() + "");
            holder.tv_mls.setText("Loại Sản phẩm: " + tenLoai);
            holder.tv_tens.setText("Tên Sản phẩm: " + sanPham.getTensp());
            holder.tv_nsx.setText("NSX: " + sanPham.getNsx());
            holder.tv_total.setText("Số lượng: " + sanPham.getSoLuong());
            Locale locale = new Locale("nv", "VN");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            String tien = numberFormat.format(sanPham.getGiasp());
            holder.tv_gias.setText("Giá Sản phẩm: " + tien);
        }
        holder.img_dels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete");
                builder.setIcon(R.drawable.ic_dele);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new SanphamDao(context);
                        long kq = 0;
                        if (typeLoad == 1) {
                            kq = dao.DeleteCart(sanPham);
                        } else {
                            kq = dao.DELETES(sanPham);
                        }
                        if (kq > 0) {
                            list.clear();
                            if (typeLoad == 1) {
                                list.addAll(dao.GetDataCart());
                            } else {
                                list.addAll(dao.GETS());
                            }
                            Toast.makeText(context.getApplicationContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            dialog.cancel();
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
        holder.img_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.custom_edit_sp, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(view);
                builder.setTitle("                Sửa Sản phẩm");
                EditText ed_teneds = (EditText) view.findViewById(R.id.tensached);
                Spinner spneds = (Spinner) view.findViewById(R.id.spin_lsached);
                EditText ed_giaeds = (EditText) view.findViewById(R.id.giasached);
                ed_teneds.setText(sanPham.getTensp());
                ed_giaeds.setText(Integer.toString(sanPham.getGiasp()));
                loaiSpches = new ArrayList<>();
                loaispDao = new LoaispDao(view.getContext());
                loaiSpches = (ArrayList<LoaiSanpham>) loaispDao.GETLS();
                LoaiSpSpiner spiner = new LoaiSpSpiner(view.getContext(), loaiSpches);
                spneds.setAdapter(spiner);
                spneds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ms = loaiSpches.get(position).getMaLSp();
                        Toast.makeText(view.getContext(), "Chọn: " + loaiSpches.get(position).getTenLSp(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mst = 0;
                for (int i = 0; i < loaiSpches.size(); i++) {
                    if (sanPham.getMalsp() == loaiSpches.get(i).getMaLSp()) {
                        mst = i;
                    }
                }
                spneds.setSelection(mst);
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (sanPham.getTensp().equals(ed_teneds.getText().toString()) && sanPham.getGiasp() == Integer.parseInt(ed_giaeds.getText().toString())
                                && sanPham.getMalsp() == mst) {
                            Toast.makeText(context.getApplicationContext(), "Không Có Gì Thay Đổi \n   Sửa Thất Bại!", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                int giaThue = Integer.parseInt(ed_giaeds.getText().toString());
                                dao = new SanphamDao(context);
                                sanPham.setTensp(ed_teneds.getText().toString());
                                sanPham.setGiasp(giaThue);
                                sanPham.setMalsp(ms);
                                long kq = dao.UPDATES(sanPham);
                                if (kq > 0) {
                                    list.clear();
                                    list.addAll(dao.GETS());
                                    Toast.makeText(view.getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                                    ed_teneds.setText("");
                                    ed_giaeds.setText("");
                                    spneds.setSelection(0);
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(view.getContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(view.getContext(), "Giá thuê phải là số", Toast.LENGTH_SHORT).show();
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

        holder.img_add.setOnClickListener(v -> {
            dao = new SanphamDao(context);
            long kq;
            if (dao.isTableExist(sanPham)) {
                kq = dao.updateCart(sanPham);
            } else {
                kq = dao.AddCarts(sanPham);
            }
            if (kq > 0) {
                Toast.makeText(context.getApplicationContext(), "Thêm vào giỏ hành thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class SpHoder extends RecyclerView.ViewHolder {
        TextView tv_ms, tv_mls, tv_tens, tv_gias, tv_nsx, tv_total;
        ImageView img_dels, img_edits, img_add;
        ConstraintLayout cns_lays;

        public SpHoder(@NonNull View itemView) {
            super(itemView);
            tv_ms = itemView.findViewById(R.id.tv_masach);
            tv_total = itemView.findViewById(R.id.tv_total);
            tv_mls = itemView.findViewById(R.id.tv_maloais);
            tv_tens = itemView.findViewById(R.id.tv_tensach);
            tv_gias = itemView.findViewById(R.id.tv_giasach);
            tv_nsx = itemView.findViewById(R.id.tv_tacgia);
            img_dels = itemView.findViewById(R.id.img_deltsach);
            img_edits = itemView.findViewById(R.id.img_editsach);
            img_add = itemView.findViewById(R.id.img_add_sp);
            cns_lays = itemView.findViewById(R.id.conss);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            cns_lays.setAnimation(animation);
        }
    }
}
