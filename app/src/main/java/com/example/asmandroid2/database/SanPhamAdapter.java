package com.example.asmandroid2.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmandroid2.R;
import com.example.asmandroid2.Screen.RegisterActivity;
import com.example.asmandroid2.dao.SanPhamDao;
import com.example.asmandroid2.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;

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

        // bắt sự kiện delete
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.baseline_warning_amber_24);
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm ' " + list.get(holder.getAdapterPosition()).getTenSP() + "' Không?");
                builder.setPositiveButton("Đông ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int masp = list.get(holder.getAdapterPosition()).getMaSP();
                        Boolean check = sanPhamDao.deleteSP(masp);
                        if (check){
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = sanPhamDao.getListSP();
                            notifyItemRemoved(holder.getAdapterPosition());
                        }else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // sk edit
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham sanPham = list.get(holder.getAdapterPosition()); // khởi tạo
                dialogUpdateSP(sanPham);
            }
        });
    }

    private void dialogUpdateSP(SanPham sanPhamUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_edit,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        // ánh xạ các widget
        TextInputEditText edtNameSPEdit= view.findViewById(R.id.edtEditNameSP);
        TextInputEditText edtPriceSPEdit = view.findViewById(R.id.edtEditPriceSP);
        TextInputEditText edtQuantitySPEdit = view.findViewById(R.id.edtEditQuantitySP);
        Button btnUpdate = view.findViewById(R.id.btnUpdateSP);
        Button btnCancel = view.findViewById(R.id.btnCancelSP);

        // set dữ liệu lên các edit để lấy giá trị cữ của sp can update
        edtNameSPEdit.setText(sanPhamUpdate.getTenSP());
        edtPriceSPEdit.setText(String.valueOf(sanPhamUpdate.getGiaSP()));
        edtQuantitySPEdit.setText(String.valueOf(sanPhamUpdate.getSlSP()));

        // bắt sk
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy giá trị
                String name = edtNameSPEdit.getText().toString();
                String price = edtPriceSPEdit.getText().toString();
                String  quantity = edtQuantitySPEdit.getText().toString();

                SanPham sp = new SanPham();
                sp.setMaSP(sanPhamUpdate.getMaSP());
                sp.setTenSP(name);
                sp.setGiaSP(Integer.parseInt(price));
                sp.setSlSP(Integer.parseInt(quantity));

                boolean check = sanPhamDao.updateSP(sp);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = sanPhamDao.getListSP();
                    // đóng dialog
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // showw dialog khi goi
        dialog.show();
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
            icAdd = itemView.findViewById(R.id.imgIC);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }
}
