package com.example.trabalhovinho.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.CompraModel;
import com.example.trabalhovinho.database.model.UsuarioModel;
import com.example.trabalhovinho.database.model.VinhoModel;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trabalhovinhos.db";
    private static final int DATABASE_VERSION = 2;

    public DBOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioModel.CREATE_TABLE);
        db.execSQL(ClienteModel.CREATE_TABLE);
        db.execSQL(VinhoModel.CREATE_TABLE);
        db.execSQL(CompraModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(CompraModel.CREATE_TRIGGER_ATUALIZAR_ESTOQUE_INSERT);
            db.execSQL(CompraModel.CREATE_TRIGGER_ATUALIZAR_ESTOQUE_UPDATE);
        }
    }
}
