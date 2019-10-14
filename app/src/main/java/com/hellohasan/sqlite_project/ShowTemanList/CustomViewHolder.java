package com.hellohasan.sqlite_project.ShowTemanList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView txtNama, txtAlamat, txtTelepon, txtEmail;
    ImageView imgDelete;
    ImageView imgEdit;

    public CustomViewHolder(View itemView) {
        super(itemView);

        txtNama = itemView.findViewById(R.id.item_teman_nama);
        txtAlamat = itemView.findViewById(R.id.item_teman_alamat);
        txtTelepon = itemView.findViewById(R.id.item_teman_telepon);
        txtEmail = itemView.findViewById(R.id.item_teman_email);
        imgDelete = itemView.findViewById(R.id.crossImageView);
        imgEdit = itemView.findViewById(R.id.editImageView);
    }
}
