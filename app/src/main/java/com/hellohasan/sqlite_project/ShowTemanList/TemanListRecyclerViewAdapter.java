package com.hellohasan.sqlite_project.ShowTemanList;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.TemanUpdateDialog;
import com.hellohasan.sqlite_project.TemanUpdateListener;
import com.hellohasan.sqlite_project.entity.Teman;
import com.hellohasan.sqlite_project.repository.TemanRepository;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class TemanListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Teman> temanList;
    private FragmentManager fm;

    public TemanListRecyclerViewAdapter(Context context, List<Teman> temanList, FragmentManager fm) {
        this.context = context;
        this.temanList = temanList;
        this.fm = fm;

        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teman_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Teman product = temanList.get(position);

        Log.e("slfj","lsdjf id1 "+teman.getId());

        holder.txtNama.setText(teman.getTemanNama());
        holder.txtAlamat.setText(": "+teman.getTemanAlamat());
        holder.txtTelepon.setText(": "+teman.getTemanTelepon());
        holder.txtEmail.setText(": "+teman.getTemanEmail());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus data ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteStudent(itemPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TemanUpdateDialog temanUpdateDialog = TemanUpdateDialog.newInstance(teman.getId(), itemPosition, new TemanUpdateListener() {
                    @Override
                    public void onTemanInfoUpdated(Teman teman, int position) {
                        temanList.set(position, teman);
                        notifyDataSetChanged();
                    }
                });
                temanUpdateDialog.show(fm, "Update Data");
            }
        });
    }

    private void deleteTeman(int position) {
        TemanRepository temanRepository = new TemanRepository(context);
        Teman teman = temanList.get(position);
        Log.e("lsfkj lsk","id"+teman.getId());
        temanRepository.deleteTeman(teman.getId());

        temanList.remove(position);
        notifyDataSetChanged();
        ((TemanListActivity) context).viewVisibility();
        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return temanList.size();
    }
}
