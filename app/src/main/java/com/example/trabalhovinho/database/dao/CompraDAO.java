package com.example.trabalhovinho.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.trabalhovinho.database.DBOpenHelper;
import com.example.trabalhovinho.database.model.CompraModel;

import java.util.ArrayList;

public class CompraDAO extends AbstrataDAO {

    private final String[] colunas = {
            CompraModel.COLUNA_ID,
            CompraModel.COLUNA_ID_USUARIO,
            CompraModel.COLUNA_ID_CLIENTE,
            CompraModel.COLUNA_ID_VINHO,
            CompraModel.COLUNA_DATA,
            CompraModel.COLUNA_QTD_VINHOS,
            CompraModel.COLUNA_PRECO_TOTAL
    };

    public CompraDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(CompraModel compra) {
        long insertRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CompraModel.COLUNA_ID_USUARIO, compra.getId_usuario());
            contentValues.put(CompraModel.COLUNA_ID_CLIENTE, compra.getId_cliente());
            contentValues.put(CompraModel.COLUNA_ID_VINHO, compra.getId_vinho());
            contentValues.put(CompraModel.COLUNA_DATA, compra.getData());
            contentValues.put(CompraModel.COLUNA_QTD_VINHOS, compra.getQtd_vinhos());
            contentValues.put(CompraModel.COLUNA_PRECO_TOTAL, compra.getPreco_total());

            insertRows = db.insert(CompraModel.TABLE_NAME, null, contentValues);
        } finally {
            Close();
        }
        return insertRows;
    }

    public long update(CompraModel compra) {
        long updateRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CompraModel.COLUNA_ID_USUARIO, compra.getId_usuario());
            contentValues.put(CompraModel.COLUNA_ID_CLIENTE, compra.getId_cliente());
            contentValues.put(CompraModel.COLUNA_ID_VINHO, compra.getId_vinho());
            contentValues.put(CompraModel.COLUNA_DATA, compra.getData());
            contentValues.put(CompraModel.COLUNA_QTD_VINHOS, compra.getQtd_vinhos());
            contentValues.put(CompraModel.COLUNA_PRECO_TOTAL, compra.getPreco_total());

            updateRows = db.update(CompraModel.TABLE_NAME, contentValues, CompraModel.COLUNA_ID + " = ?", new String[]{String.valueOf(compra.getId())});
        } finally {
            Close();
        }
        return updateRows;
    }

    public long deleteById(long id) {
        long deleteRows = 0;
        try {
            Open();
            deleteRows = db.delete(CompraModel.TABLE_NAME, CompraModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return deleteRows;
    }

    public ArrayList<CompraModel> selectAll(long id_usuario) {
        ArrayList<CompraModel> listaCompra = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(CompraModel.TABLE_NAME, colunas, CompraModel.COLUNA_ID_USUARIO + " = ?", new String[]{String.valueOf(id_usuario)}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CompraModel compra = new CompraModel();
                compra.setId(cursor.getLong(0));
                compra.setId_usuario(cursor.getLong(1));
                compra.setId_cliente(cursor.getLong(2));
                compra.setId_vinho(cursor.getLong(3));
                compra.setData(cursor.getString(4));
                compra.setQtd_vinhos(cursor.getInt(5));
                compra.setPreco_total(cursor.getFloat(6));
                listaCompra.add(compra);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            Close();
        }

        return listaCompra;
    }

    public ArrayList<CompraModel> selectAllByClient(long id_cliente) {
        ArrayList<CompraModel> listaCompra = new ArrayList<>();
        try {
            Open();
            Cursor cursor = db.query(CompraModel.TABLE_NAME, colunas, CompraModel.COLUNA_ID_CLIENTE + " = ?", new String[]{String.valueOf(id_cliente)}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CompraModel compra = new CompraModel();
                compra.setId(cursor.getLong(0));
                compra.setId_usuario(cursor.getLong(1));
                compra.setId_cliente(cursor.getLong(2));
                compra.setId_vinho(cursor.getLong(3));
                compra.setData(cursor.getString(4));
                compra.setQtd_vinhos(cursor.getInt(5));
                listaCompra.add(compra);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            Close();
        }

        return listaCompra;
    }

    public ArrayList<CompraModel> selectAllByMes(long id_usuario, int mes, int ano) {
        ArrayList<CompraModel> listaCompra = new ArrayList<>();
        try {
            Open();
            String filtroData = ano + "-" + String.format("%02d", mes) + "%";
            Cursor cursor = db.query(CompraModel.TABLE_NAME, colunas,
                    CompraModel.COLUNA_ID_USUARIO + " = ? AND " + CompraModel.COLUNA_DATA + " LIKE ?",
                    new String[]{String.valueOf(id_usuario), filtroData}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CompraModel compra = new CompraModel();
                compra.setId(cursor.getLong(0));
                compra.setId_usuario(cursor.getLong(1));
                compra.setId_cliente(cursor.getLong(2));
                compra.setId_vinho(cursor.getLong(3));
                compra.setData(cursor.getString(4));
                compra.setQtd_vinhos(cursor.getInt(5));
                listaCompra.add(compra);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            Close();
        }

        return listaCompra;
    }


    public CompraModel selectById(long id) {
        CompraModel compra = null;
        try {
            Open();
            Cursor cursor = db.query(CompraModel.TABLE_NAME, colunas, CompraModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                compra = new CompraModel();
                compra.setId(cursor.getLong(0));
                compra.setId_usuario(cursor.getLong(1));
                compra.setId_cliente(cursor.getLong(2));
                compra.setId_vinho(cursor.getLong(3));
                compra.setData(cursor.getString(4));
                compra.setQtd_vinhos(cursor.getInt(5));
            }
            if (cursor != null) {
                cursor.close();
            }
        } finally {
            Close();
        }
        return compra;
    }
}
