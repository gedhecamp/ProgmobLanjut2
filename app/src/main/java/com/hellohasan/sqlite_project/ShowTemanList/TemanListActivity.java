package com.hellohasan.sqlite_project.ShowTemanList;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.TemanCreateDialog;
import com.hellohasan.sqlite_project.TemanCreateListener;
import com.hellohasan.sqlite_project.entity.Teman;
import com.hellohasan.sqlite_project.repository.TemanRepository;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class TemanListActivity extends AppCompatActivity implements TemanCreateListener {


    private List<Teman> temanList = new ArrayList<>();
    private TemanRepository temanRepository;
    private TextView temanListEmptyTextView;
    private RecyclerView recyclerView;
    private TemanListRecyclerViewAdapter temanListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.studentRecyclerView);
        temanListEmptyTextView = (TextView) findViewById(R.id.emptyStudentListTextView);
        temanRepository = new TemanRepository(getApplicationContext());


        temanRepository.getTeman().observe(this, new Observer<List<Teman>>() {
            @Override
            public void onChanged(@Nullable List<Teman> teman) {
                temanList.addAll(teman);
                viewVisibility();
            }
        });

        FragmentManager fm = getSupportFragmentManager();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        temanListRecyclerViewAdapter = new TemanListRecyclerViewAdapter(this, temanList,fm);
        recyclerView.setAdapter(temanListRecyclerViewAdapter);
        Log.e("data ",temanList.toString());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTemanCreateDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Yakin ingin menghapus semua Data?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            temanRepository.deleteAllTeman();

                            temanList.clear();
                            temanListRecyclerViewAdapter.notifyDataSetChanged();
                            viewVisibility();
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

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(temanList.isEmpty())
            temanListEmptyTextView.setVisibility(View.VISIBLE);
        else
            temanListEmptyTextView.setVisibility(View.GONE);
    }

    private void openTemanCreateDialog() {
        TemanCreateDialog temanCreateDialog = TemanCreateDialog.newInstance("Create Data", this);
        temanCreateDialog.show(getSupportFragmentManager(), "Create Data");
    }

    @Override
    public void onTemanCreated(Teman teman) {
        temanList.add(teman);
        temanListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(teman.getTemanNama());
    }
}
