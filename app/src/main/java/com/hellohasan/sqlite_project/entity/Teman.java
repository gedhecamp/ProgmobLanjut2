package com.hellohasan.sqlite_project.entity;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "db_teman")
public class Teman implements Serializable {

        @PrimaryKey(autoGenerate = true)
        public int id;

        @ColumnInfo(name = "teman_nama")
        public String temanNama;

        @ColumnInfo(name = "teman_alamat")
        public String temanAlamat;

        @ColumnInfo(name = "teman_telepon")
        public String temanTelepon;

        @ColumnInfo(name = "teman_email")
        public String temanEmail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    public String getTemanNama() {
        return temanNama;
    }

    public void setTemanNama(String temanNama) {
        this.temanNama = temanNama;
    }

    public String getTemanAlamat() {
        return temanAlamat;
    }

    public void setTemanAlamat(String temanAlamat) {
        this.temanAlamat = temanAlamat;
    }

    public String getTemanTelepon() {
        return temanTelepon;
    }

    public void setTemanTelepon(String temanTelepon) {
        this.temanTelepon = temanTelepon;
    }

    public String getTemanEmail() {
        return temanEmail;
    }

    public void setTemanEmail(String temanEmail) {
        this.temanEmail = temanEmail;
    }
}