package com.hellohasan.sqlite_project.Database;
import android.content.Context;

import com.hellohasan.sqlite_project.DaoTeman;
import com.hellohasan.sqlite_project.entity.Teman;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


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