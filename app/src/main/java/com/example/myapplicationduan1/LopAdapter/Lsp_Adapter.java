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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationduan1.LopDao.LoaispDao;
import com.example.myapplicationduan1.LopModel.LoaiSanpham;
import com.example.myapplicationduan1.R;

import java.util.List;

public class Lsp_Adapter extends RecyclerView.Adapter<Lsp_Adapter.LoaiSphoder>{
    Context context;
    List<LoaiSanpham> sanphamList;
    LoaispDao loDao;

    public Lsp_Adapter(Context context, List<LoaiSanpham> spList, LoaispDao loDao) {
        this.context = context;
        this.sanphamList = spList;
        this.loDao = loDao;
        // commit
    }

    @NonNull
    @Override
    public LoaiSphoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_loaisp, parent, false);
        return new LoaiSphoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSphoder holder, int position) {
        LoaiSanpham loaiSanpham = sanphamList.get(position);
        if (loaiSanpham == null) {
            return;
        } else {
            holder.tv_IDls.setText("Mã Loại Sản phẩm: " + loaiSanpham.getMaLSp() + "");
            holder.tv_tenls.setText("Tên Loại Sản phẩm: " + loaiSanpham.getTenLSp());
            holder.tv_nhacc.setText("Nhà Cung Cấp: " + loaiSanpham.getNhacc());
        }
        if (holder.tv_tenls.getText().toString().contains("N")) {
            holder.tv_IDls.setTextColor(Color.GREEN);
            holder.tv_tenls.setTextColor(Color.GREEN);
            holder.tv_nhacc.setTextColor(Color.GREEN);
        } else if (holder.tv_tenls.getText().toString().contains("A")) {
            holder.tv_IDls.setTextColor(Color.RED);
            holder.tv_tenls.setTextColor(Color.RED);
            holder.tv_nhacc.setTextColor(Color.RED);
        } else {
            holder.tv_IDls.setTextColor(Color.BLACK);
            holder.tv_tenls.setTextColor(Color.BLACK);
            holder.tv_nhacc.setTextColor(Color.BLACK);
        }
        holder.imgdel.setOnClickListener(new View.OnClickListener() {
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
                        loDao = new LoaispDao(context);
                        long kq = loDao.DELETELS(loaiSanpham);
                        if (kq > 0) {
                            sanphamList.clear();
                            sanphamList.addAll(loDao.GETLS());
                            // load dữ liệu
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
        holder.imgdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.custom_edit_loaisp, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(view);
                builder.setTitle("                Sửa Loại Sản phẩm");
                EditText ed_loasc = (EditText) view.findViewById(R.id.ed_editls);
                EditText ed_ncc = (EditText) view.findViewById(R.id.edit_ncc);
                ed_loasc.setText(loaiSanpham.getTenLSp());
                ed_ncc.setText(loaiSanpham.getNhacc());
                builder.setCancelable(true);
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (loaiSanpham.getTenLSp().equals(ed_loasc.getText().toString()) && loaiSanpham.getNhacc().equals(ed_ncc.getText().toString())) {
                            Toast.makeText(context.getApplicationContext(), "Không Có Gì Thay Đổi \n   Sửa Thất Bại!", Toast.LENGTH_SHORT).show();

                        } else {
                            loDao = new LoaispDao(context);
                            loaiSanpham.setTenLSp(ed_loasc.getText().toString());
                            loaiSanpham.setNhacc(ed_ncc.getText().toString());
                            long kq = loDao.UPDATELS(loaiSanpham);
                            if (kq > 0) {
                                sanphamList.clear();
                                sanphamList.addAll(loDao.GETLS());
                                notifyDataSetChanged();
                                ed_loasc.setText("");
                                ed_ncc.setText("");
                                dialog.dismiss();
                                Toast.makeText(context.getApplicationContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context.getApplicationContext(), "Sửa Thất Bại", Toast.LENGTH_SHORT).show();

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
        if (sanphamList != null) {
            return sanphamList.size();
        }
        return 0;
    }

    public class LoaiSphoder extends RecyclerView.ViewHolder{
        TextView tv_IDls, tv_tenls, tv_nhacc;
        ImageView imgdel, imgdit;
        CardView cardView;
        public LoaiSphoder(@NonNull View view) {
            super(view);
            tv_IDls = (TextView) view.findViewById(R.id.tv_id_loaisach);
            tv_tenls = (TextView) view.findViewById(R.id.tv_ten_loaisach);
            tv_nhacc = (TextView) view.findViewById(R.id.tv_nhaccc);
            imgdel = (ImageView) view.findViewById(R.id.imgdells);
            imgdit = (ImageView) view.findViewById(R.id.imgditls);
            cardView = view.findViewById(R.id.cns_ls);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.transition);
            cardView.setAnimation(animation);
        }
    }
}
