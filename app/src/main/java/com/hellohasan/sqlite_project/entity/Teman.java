package com.hellohasan.sqlite_project.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Teman {
    @Entity(tableName = "data_teman")
    public static class EntitasTeman {

        @PrimaryKey(autoGenerate = true)
        public int Id;

        @ColumnInfo(name = "nama_teman")
        public String nama_teman;

        @ColumnInfo(name = "alamat_teman")
        public String alamat_teman;

        @ColumnInfo(name = "telp_teman")
        public String telp_teman;

        @ColumnInfo(name = "email_teman")
        public String email_teman;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getNama_teman() {
            return nama_teman;
        }

        public void setNama_teman(String nama_teman) {
            this.nama_teman = nama_teman;
        }

        public String getAlamat_teman() {
            return alamat_teman;
        }

        public void setAlamat_teman(String alamat_teman) {
            this.alamat_teman = alamat_teman;
        }

        public String getTelp_teman() {
            return telp_teman;
        }

        public void setTelp_teman(String telp_teman) {
            this.telp_teman = telp_teman;
        }

        public String getEmail_teman() {
            return email_teman;
        }

        public void setEmail_teman(String email_teman) {
            this.email_teman = email_teman;
        }
    }
}
