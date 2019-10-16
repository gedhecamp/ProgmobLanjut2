package com.room.project;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.room.project.entity.Teman;
import com.room.project.repository.TemanRepository;

import java.util.List;


public class TemanUpdateDialog extends DialogFragment {

    private static int idTeman;
    private static int temanItemPosition;
    private static TemanUpdateListener temanUpdateListener;

    private Teman mTeman;

    private EditText editTemanNama;
    private EditText editTemanAlamat;
    private EditText editTemanTelepon;
    private EditText editTemanEmail;
    private Button updateButton;
    private Button cancelButton;

    private String namaString = "", alamatString = "",teleponString = "",emailString = "";

    private TemanRepository temanRepository;

    public TemanUpdateDialog() {
        // Required empty public constructor
    }

    public static TemanUpdateDialog newInstance(int id, int position, TemanUpdateListener listener){
        idTeman = id;
        temanItemPosition = position;
        temanUpdateListener = listener;
        TemanUpdateDialog temanUpdateDialog = new TemanUpdateDialog();
        Bundle args = new Bundle();
        args.putString("title", "Update Teman");
        temanUpdateDialog.setArguments(args);

        temanUpdateDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return temanUpdateDialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_update_dialog, container, false);

        temanRepository = new TemanRepository(getContext());

        editTemanNama = view.findViewById(R.id.studentNameEditText);
        editTemanAlamat = view.findViewById(R.id.registrationEditText);
        editTemanTelepon = view.findViewById(R.id.phoneEditText);
        editTemanEmail = view.findViewById(R.id.emailEditText);

        updateButton = view.findViewById(R.id.updateStudentInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString("Update Data");
        getDialog().setTitle("Update Data");

        temanRepository.getTeman().observe(this, new Observer<List<Teman>>() {
            @Override
            public void onChanged(@Nullable List<Teman> teman) {
                for(final Teman mProduct : teman) {
                    if (mProduct.getId() == idTeman){

                        editTemanNama.setText(mProduct.getTemanNama());
                        editTemanAlamat.setText(mProduct.getTemanAlamat());
                        editTemanTelepon.setText(mProduct.getTemanTelepon());
                        editTemanEmail.setText(mProduct.getTemanEmail());

                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                namaString = editTemanNama.getText().toString();
                                alamatString = editTemanAlamat.getText().toString();
                                teleponString = editTemanTelepon.getText().toString();
                                emailString = editTemanEmail.getText().toString();

                                mTeman.setTemanNama(namaString);
                                mTeman.setTemanAlamat(alamatString);
                                mTeman.setTemanTelepon(teleponString);
                                mTeman.setTemanEmail(emailString);

                                temanRepository.updateTeman(mTeman);

                                temanUpdateListener.onTemanInfoUpdated(mTeman, temanItemPosition);
                                getDialog().dismiss();
                            }
                        });

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getDialog().dismiss();
                            }
                        });
                    }

                }
            }
        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
