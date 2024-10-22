package com.example.trabalhovinho.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.trabalhovinho.database.DBOpenHelper;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.ArrayList;

public class VinhoDAO extends AbstrataDAO {

    private final String[] colunas = {
            VinhoModel.COLUNA_ID,
            VinhoModel.COLUNA_ID_USUARIO,
            VinhoModel.COLUNA_NOME,
            VinhoModel.COLUNA_TIPO,
            VinhoModel.COLUNA_SAFRA,
            VinhoModel.COLUNA_PRECO,
            VinhoModel.COLUNA_ESTOQUE
    };

    public VinhoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(VinhoModel vinho) {
        long insertRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(VinhoModel.COLUNA_ID_USUARIO, vinho.getId_usuario());
            contentValues.put(VinhoModel.COLUNA_NOME, vinho.getNome());
            contentValues.put(VinhoModel.COLUNA_TIPO, vinho.getTipo());
            contentValues.put(VinhoModel.COLUNA_SAFRA, vinho.getSafra());
            contentValues.put(VinhoModel.COLUNA_PRECO, vinho.getPreco());
            contentValues.put(VinhoModel.COLUNA_ESTOQUE, vinho.getEstoque());

            insertRows = db.insert(VinhoModel.TABLE_NAME, null, contentValues);
        } finally {
            Close();
        }
        return insertRows;
    }

    public long update(VinhoModel vinho) {
        long updateRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(VinhoModel.COLUNA_ID_USUARIO, vinho.getId_usuario());
            contentValues.put(VinhoModel.COLUNA_NOME, vinho.getNome());
            contentValues.put(VinhoModel.COLUNA_TIPO, vinho.getTipo());
            contentValues.put(VinhoModel.COLUNA_SAFRA, vinho.getSafra());
            contentValues.put(VinhoModel.COLUNA_PRECO, vinho.getPreco());
            contentValues.put(VinhoModel.COLUNA_ESTOQUE, vinho.getEstoque());

            updateRows = db.update(VinhoModel.TABLE_NAME, contentValues, VinhoModel.COLUNA_ID + " = ?", new String[]{String.valueOf(vinho.getId())});
        } finally {
            Close();
        }
        return updateRows;
    }

    public long deleteById(long id) {
        long deleteRows = 0;
        try {
            Open();
            deleteRows = db.delete(VinhoModel.TABLE_NAME, VinhoModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return deleteRows;
    }

    public ArrayList<VinhoModel> selectAll(long id_usuario) {
        ArrayList<VinhoModel> listaVinho = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(VinhoModel.TABLE_NAME, colunas, VinhoModel.COLUNA_ID_USUARIO + " = ?", new String[]{String.valueOf(id_usuario)}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                VinhoModel vinho = new VinhoModel();
                vinho.setId(cursor.getLong(0));
                vinho.setId_usuario(cursor.getLong(1));
                vinho.setNome(cursor.getString(2));
                vinho.setTipo(cursor.getString(3));
                vinho.setSafra(cursor.getString(4));
                vinho.setPreco(cursor.getFloat(5));
                vinho.setEstoque(cursor.getInt(6));
                listaVinho.add(vinho);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            Close();
        }

        return listaVinho;
    }


    public VinhoModel selectById(long id) {
        VinhoModel vinho = null;
        try {
            Open();
            Cursor cursor = db.query(VinhoModel.TABLE_NAME, colunas, VinhoModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                vinho = new VinhoModel();
                vinho.setId(cursor.getLong(0));
                vinho.setId_usuario(cursor.getLong(1));
                vinho.setNome(cursor.getString(2));
                vinho.setTipo(cursor.getString(3));
                vinho.setSafra(cursor.getString(4));
                vinho.setPreco(cursor.getFloat(5));
                vinho.setEstoque(cursor.getInt(6));
            }
            if (cursor != null) {
                cursor.close();
            }
        } finally {
            Close();
        }
        return vinho;
    }
}
