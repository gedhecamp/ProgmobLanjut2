package com.room.project.Database;

import android.content.Context;
import com.room.project.DaoTeman;
import com.room.project.entity.Teman;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Teman.class}, version = 1, exportSchema = false)
public abstract class DatabaseTeman extends RoomDatabase {

    private static DatabaseTeman instance;


    public abstract DaoTeman temanDao();


    public static DatabaseTeman getTeman(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseTeman.class,
                    "db_teman")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}