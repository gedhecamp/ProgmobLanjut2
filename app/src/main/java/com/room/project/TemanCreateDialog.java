package com.room.project;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.room.project.entity.Teman;
import com.room.project.repository.TemanRepository;


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
        TemanCreateDialog temanCreateDialog = new TemanCreateDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        temanCreateDialog.setArguments(args);

        temanCreateDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return temanCreateDialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_create_dialog, container, false);

        editTemanNama = view.findViewById(R.id.studentNameEditText);
        editTemanAlamat = view.findViewById(R.id.registrationEditText);
        editTemanTelepon = view.findViewById(R.id.phoneEditText);
        editTemanEmail = view.findViewById(R.id.emailEditText);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString("Create Data");
        getDialog().setTitle("Create Data");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaString = editTemanNama.getText().toString();
                alamatString = editTemanAlamat.getText().toString();
                teleponString = editTemanTelepon.getText().toString();
                emailString = editTemanEmail.getText().toString();

                TemanRepository temanRepository = new TemanRepository(getContext());
                Teman teman = new Teman();
                teman.setTemanNama(namaString);
                teman.setTemanAlamat(alamatString);
                teman.setTemanTelepon(teleponString);
                teman.setTemanEmail(emailString);

                temanRepository.insertTeman(teman);
                temanCreateListener.onTemanCreated(teman);
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
