package com.example.myapplicationduan1.LopAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationduan1.LopDao.KhachhangDao;
import com.example.myapplicationduan1.LopModel.KhachHang;
import com.example.myapplicationduan1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KH_Adapter extends RecyclerView.Adapter<KH_Adapter.khhoder> implements Filterable {
    Context context;
    List<KhachHang> list;
    KhachhangDao vienDao;
    List<KhachHang> mlistOld;

    public KH_Adapter(Context context, List<KhachHang> list){
        this.context = context;
        this.list = list;
        this.mlistOld=list;
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
                    List<KhachHang> listtv = new ArrayList<>();
                    for (KhachHang khachHang : mlistOld) {
                        if (khachHang.getHoTenTV().toLowerCase().contains(strSearch.toLowerCase())) {
                            listtv.add(khachHang);
                        }
                        ;
                    }
                    list = listtv;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<KhachHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public khhoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_khachhang, parent, false);

        return new khhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull khhoder holder, int position) {
        KhachHang khachHang = list.get(position);
        if (khachHang == null) {
            return;
        } else {
            holder.tv_vtri.setText("Vị Trí: " + (position + 1));
            holder.tv_maTV.setText("Mã TV: " + khachHang.getIDTV() + "");
            holder.tv_hoten.setText("Họ Và Tên: " + khachHang.getHoTenTV());
            holder.tv_namsinh.setText("Năm Sinh: " + khachHang.getNamsinhTV());
        }
        if (position % 2 == 1 || position % 2 != 0) {
            holder.tv_maTV.setTextColor(Color.GREEN);
            holder.tv_hoten.setTextColor(Color.GREEN);
            holder.tv_namsinh.setTextColor(Color.GREEN);
        } else {
            holder.tv_maTV.setTextColor(Color.RED);
            holder.tv_hoten.setTextColor(Color.RED);
            holder.tv_namsinh.setTextColor(Color.RED);
        }

        holder.img_dele.setOnClickListener(new View.OnClickListener() {
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
                        vienDao = new KhachhangDao(context);
                        int kq = vienDao.DELETETV(khachHang);
                        if (kq > 0) {
                            list.clear();
                            list.addAll(vienDao.GETTV());
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
        holder.img_edi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.custom_update_kh, null);
                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).create();
                dialog.setView(view);
                dialog.setTitle("                Sửa Khách hàng ");
                EditText ed_edhotentv = view.findViewById(R.id.ed_hotentvedit);
                EditText ed_namsinhtv = view.findViewById(R.id.ed_namstvedit);
                ed_edhotentv.setText(khachHang.getHoTenTV());
                ed_namsinhtv.setText(khachHang.getNamsinhTV());
                AppCompatButton btn_save = view.findViewById(R.id.btn_themtvsua);
                AppCompatButton btn_huy = view.findViewById(R.id.btn_clentvedit);
                dialog.setCancelable(true);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (khachHang.getHoTenTV().equals(ed_edhotentv.getText().toString()) && khachHang.getNamsinhTV().equals(ed_namsinhtv.getText().toString())) {
                            Toast.makeText(context.getApplicationContext(), "Không Có Gì Thay Đổi \n Sửa Thất Bại!", Toast.LENGTH_SHORT).show();
                        } else {
                            vienDao = new KhachhangDao(context);
                            khachHang.setHoTenTV(ed_edhotentv.getText().toString());
                            khachHang.setNamsinhTV(ed_namsinhtv.getText().toString());
                            long kq = vienDao.UPDATETV(khachHang);
                            if (kq > 0) {
                                list.clear();
                                list.addAll(vienDao.GETTV());
                                notifyDataSetChanged();
                                ed_edhotentv.setText("");
                                ed_namsinhtv.setText("");
                                dialog.dismiss();
                                Toast.makeText(context.getApplicationContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context.getApplicationContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
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

    public class khhoder extends RecyclerView.ViewHolder{

        TextView tv_maTV, tv_hoten, tv_namsinh, tv_vtri;
        ImageView img_dele, img_edi;

        ConstraintLayout constraintLayout;
        public khhoder(@NonNull View itemView) {
            super(itemView);
            tv_maTV = itemView.findViewById(R.id.tv_idtv);
            tv_hoten = itemView.findViewById(R.id.tv_hotentv);
            tv_namsinh = itemView.findViewById(R.id.tv_namsinhtv);
            img_dele = itemView.findViewById(R.id.img_deletetv);
            img_edi = itemView.findViewById(R.id.img_edittv);
            tv_vtri = itemView.findViewById(R.id.tv_vtri);
            constraintLayout = itemView.findViewById(R.id.cns_tv);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            constraintLayout.setAnimation(animation);
        }
    }
}
