package com.hellohasan.sqlite_project;

import com.hellohasan.sqlite_project.entity.Teman;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

@Dao
public interface DaoTeman {

    @Insert
    Long insertTeman(Teman teman);


    @Query("SELECT * FROM tb_teman ORDER BY id desc")
    LiveData<List<Teman>> fetchAllProducts();


    @Update
    void updateTeman(Teman teman);


    @Query("Delete from tb_teman where id=:id")
    void deleteTeman(int id);

    @Query("Delete from tb_teman")
    void deleteAllProduct();
}
