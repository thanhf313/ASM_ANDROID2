package com.example.asmandroid2.database;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmandroid2.R;
import com.example.asmandroid2.Screen.RegisterActivity;
import com.example.asmandroid2.dao.SanPhamDao;
import com.example.asmandroid2.model.SanPham;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHodler> {
    private  final Context context;
    private ArrayList<SanPham> list;
    private final SanPhamDao sanPhamDao;

    public SanPhamAdapter(Context context, ArrayList<SanPham> list, SanPhamDao sanPhamDao) {
        this.context = context;
        this.list = list;
        this.sanPhamDao = sanPhamDao;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_recycler,parent,false);

        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHodler holder, int position) {
    // set dữ liệu lên từng item trên recycler
        holder.tvTenSP.setText(list.get(position).getTenSP());
        holder.tvGiaSP.setText(String.valueOf(list.get(position).getGiaSP())+ "VNĐ -");
        holder.tvSLSP.setText("SL: "+ String.valueOf(list.get(position).getSlSP()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public TextView tvTenSP,tvGiaSP,tvSLSP;
        public ImageView imgViews,icAdd, imgDelete,imgEdit;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);

            // ánh xạ
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            tvSLSP = itemView.findViewById(R.id.tvSLSP);
            imgViews = itemView.findViewById(R.id.imgView);
            icAdd = imgViews.findViewById(R.id.imgIC);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }
}
