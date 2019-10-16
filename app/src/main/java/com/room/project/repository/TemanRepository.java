package com.room.project.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.room.project.Database.DatabaseTeman;
import com.room.project.entity.Teman;

import java.util.List;

public class TemanRepository {

    private String DB_NAME = "db_teman";

    private DatabaseTeman database;
    public TemanRepository(Context context) {
        database = Room.databaseBuilder(context, DatabaseTeman.class, DB_NAME).build();
    }

    public void insertTeman(String temanNama,
                              String temanAlamat,
                              String temanTelepon,
                              String temanEmail) {

        insertTeman(temanNama, temanAlamat, temanTelepon, temanEmail);
    }

    public void insertTeman(final Teman teman) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.temanDao().insertTeman(teman);
                return null;
            }
        }.execute();
    }

    public void updateTeman(final Teman teman) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.temanDao().updateTeman(teman);
                return null;
            }
        }.execute();
    }

    public void deleteTeman(final int id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.temanDao().deleteTeman(id);
                return null;
            }
        }.execute();

    }

    public void deleteAllTeman() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.temanDao().deleteAllTeman();
                return null;
            }

        }.execute();

    }

    public LiveData<List<Teman>> getTeman() {
        return database.temanDao().fetchAllTeman();
    }
}
