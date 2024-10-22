package com.example.trabalhovinho.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.trabalhovinho.database.DBOpenHelper;
import com.example.trabalhovinho.database.model.UsuarioModel;

import java.util.ArrayList;

public class UsuarioDAO extends AbstrataDAO {

    private final String[] colunas = {
            UsuarioModel.COLUNA_ID,
            UsuarioModel.COLUNA_NOME,
            UsuarioModel.COLUNA_EMAIL,
            UsuarioModel.COLUNA_SENHA
    };

    public UsuarioDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(UsuarioModel usuario) {
        long insertRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UsuarioModel.COLUNA_NOME, usuario.getNome());
            contentValues.put(UsuarioModel.COLUNA_EMAIL, usuario.getEmail());
            contentValues.put(UsuarioModel.COLUNA_SENHA, usuario.getSenha());

            insertRows = db.insert(UsuarioModel.TABLE_NAME, null, contentValues);
        } finally {
            Close();
        }
        return insertRows;
    }

    public long update(UsuarioModel usuario) {
        long updateRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UsuarioModel.COLUNA_NOME, usuario.getNome());
            contentValues.put(UsuarioModel.COLUNA_EMAIL, usuario.getEmail());
            contentValues.put(UsuarioModel.COLUNA_SENHA, usuario.getSenha());

            updateRows = db.update(UsuarioModel.TABLE_NAME, contentValues, UsuarioModel.COLUNA_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        } finally {
            Close();
        }
        return updateRows;
    }

    public long deleteById(long id) {
        long deleteRows = 0;
        try {
            Open();
            deleteRows = db.delete(UsuarioModel.TABLE_NAME, UsuarioModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return deleteRows;
    }

    public ArrayList<UsuarioModel> selectAll() {
        ArrayList<UsuarioModel> listaUsuario = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(UsuarioModel.TABLE_NAME, colunas, null, null, null, null, null);
            cursor.moveToFirst(); // Move o cursor para o primeiro item
            while (!cursor.isAfterLast()) {
                UsuarioModel usuario = new UsuarioModel();
                usuario.setId(cursor.getLong(0));
                usuario.setNome(cursor.getString(1));
                usuario.setEmail(cursor.getString(2));
                usuario.setSenha(cursor.getString(3));
                listaUsuario.add(usuario);
                cursor.moveToNext(); // Move para o próximo item
            }
            cursor.close();
        } finally {
            Close();
        }

        return listaUsuario;
    }

    public UsuarioModel selectById(long id) {
        UsuarioModel usuario = null;
        try {
            Open();
            Cursor cursor = db.query(UsuarioModel.TABLE_NAME, colunas, UsuarioModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                usuario = new UsuarioModel();
                usuario.setId(cursor.getLong(0));
                usuario.setNome(cursor.getString(1));
                usuario.setEmail(cursor.getString(2));
                usuario.setSenha(cursor.getString(3));
            }
            if (cursor != null) {
                cursor.close();
            }
        } finally {
            Close();
        }
        return usuario;
    }
}
