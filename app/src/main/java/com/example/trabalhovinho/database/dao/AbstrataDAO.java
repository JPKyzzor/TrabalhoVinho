package com.example.trabalhovinho.database.dao;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstrataDAO {

    protected SQLiteOpenHelper db_helper;
    protected SQLiteDatabase db;

    protected final void Open(){
        db = db_helper.getWritableDatabase();
    }
    protected final void Close(){
        db_helper.close();
    }
}
