package com.room.project.ShowTemanList;

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


import com.facebook.stetho.Stetho;
import com.room.project.R;
import com.room.project.TemanCreateDialog;
import com.room.project.TemanCreateListener;
import com.room.project.entity.Teman;
import com.room.project.repository.TemanRepository;
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

        Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.studentRecyclerView);
        temanListEmptyTextView = (TextView) findViewById(R.id.emptyStudentListTextView);
        temanRepository = new TemanRepository(getApplicationContext());
        final FragmentManager fm = getSupportFragmentManager();

        temanRepository.getTeman().observe(this, new Observer<List<Teman>>() {
            @Override
            public void onChanged(@Nullable List<Teman> teman) {

                temanList.addAll(teman);
                recyclerView.setLayoutManager(new LinearLayoutManager(TemanListActivity.this, LinearLayoutManager.VERTICAL, false));
                temanListRecyclerViewAdapter = new TemanListRecyclerViewAdapter(TemanListActivity.this, temanList,fm);
                recyclerView.setAdapter(temanListRecyclerViewAdapter);
                Log.e("data ",temanList.toString());
                viewVisibility();
            }
        });


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
        Log.e("ppppp","jj j "+temanList.toString());
        viewVisibility();
        Logger.d(teman.getTemanNama());
    }
}
