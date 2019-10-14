package com.hellohasan.sqlite_project;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.hellohasan.sqlite_project.Database.DatabaseTeman;
import com.hellohasan.sqlite_project.repository.TemanRepository;


public class TemanCreateDialog extends DialogFragment {

    private static TemanCreateListener temanCreateListener;

    private EditText editTemanNama;
    private EditText editTemanAlamat;
    private EditText editTemanTelepon;
    private EditText editTemanEmail;
    private Button createButton;
    private Button cancelButton;

    private String namaString = "", alamatString = "",teleponString = "",emailString = "";

    public TemanCreateDialog() {
        // Required empty public constructor
    }

    public static TemanCreateDialog newInstance(String title, TemanCreateListener listener){
        temanCreateListener = listener;
        TemanCreateDialog productCreateDialogFragment = new TemanCreateDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        temanCreateDialog.setArguments(args);

        temanCreateDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return temanCreateDialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_create_dialog, container, false);

        editTemanNama = view.findViewById(R.id.create_teman_nama);
        editTemanAlamat = view.findViewById(R.id.create_teman_alamat);
        editTemanTelepon = view.findViewById(R.id.create_teman_telepon);
        editTemanEmail = view.findViewById(R.id.create_teman_email);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.createCancelButton);

        String title = getArguments().getString("Create DatabaseTeman");
        getDialog().setTitle("Create DatabaseTeman");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaString = editTemanNama.getText().toString();
                alamatString = editTemanAlamat.getText().toString();
                teleponString = editTemanTelepon.getText().toString();
                emailString = editTemanEmail.getText().toString();
                if (editProductHarga.getText().toString().equals("")){
                    hargaInt="0";
                }
                else {
                    hargaInt = editProductHarga.getText().toString();
                }

                if (editProductQty.getText().toString().equals("")){
                    qtyInt="0";
                }
                else {
                    qtyInt = editProductQty.getText().toString();
                }

                TemanRepository productRepository = new TemanRepository(getContext());
                DatabaseTeman databaseTeman = new DatabaseTeman();
                databaseTeman.setTemanNama(namaString);
                databaseTeman.setTemanAlamat(alamatString);
                databaseTeman.setTemanTelepon(teleponString);
                databaseTeman.setTemanEmail(emailString);

                temanRepository.insertTeman(databaseTeman);
                temanCreateListener.onProductCreated(databaseTeman);
                getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
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
