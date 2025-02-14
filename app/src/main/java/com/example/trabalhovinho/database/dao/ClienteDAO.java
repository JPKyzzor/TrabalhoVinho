package com.example.trabalhovinho.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.trabalhovinho.database.DBOpenHelper;
import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.UsuarioModel;

import java.util.ArrayList;

public class ClienteDAO extends AbstrataDAO {

    private final String[] colunas = {
            ClienteModel.COLUNA_ID,
            ClienteModel.COLUNA_ID_USUARIO,
            ClienteModel.COLUNA_NOME,
            ClienteModel.COLUNA_CPF_CNPJ,
            ClienteModel.COLUNA_CIDADE,
            ClienteModel.COLUNA_ESTADO,
            ClienteModel.COLUNA_TELEFONE
    };

    public ClienteDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(ClienteModel cliente) {
        long insertRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ClienteModel.COLUNA_ID_USUARIO, cliente.getId_usuario());
            contentValues.put(ClienteModel.COLUNA_NOME, cliente.getNome());
            contentValues.put(ClienteModel.COLUNA_CPF_CNPJ, cliente.getCpf_cnpj());
            contentValues.put(ClienteModel.COLUNA_CIDADE, cliente.getCidade());
            contentValues.put(ClienteModel.COLUNA_ESTADO, cliente.getEstado());
            contentValues.put(ClienteModel.COLUNA_TELEFONE, cliente.getTelefone());

            insertRows = db.insert(ClienteModel.TABLE_NAME, null, contentValues);
        } finally {
            Close();
        }
        return insertRows;
    }

    public long update(ClienteModel cliente) {
        long updateRows = 0;
        try {
            Open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ClienteModel.COLUNA_ID_USUARIO, cliente.getId_usuario());
            contentValues.put(ClienteModel.COLUNA_NOME, cliente.getNome());
            contentValues.put(ClienteModel.COLUNA_CPF_CNPJ, cliente.getCpf_cnpj());
            contentValues.put(ClienteModel.COLUNA_CIDADE, cliente.getCidade());
            contentValues.put(ClienteModel.COLUNA_ESTADO, cliente.getEstado());
            contentValues.put(ClienteModel.COLUNA_TELEFONE, cliente.getTelefone());

            updateRows = db.update(ClienteModel.TABLE_NAME, contentValues, ClienteModel.COLUNA_ID + " = ?", new String[]{String.valueOf(cliente.getId())});
        } finally {
            Close();
        }
        return updateRows;
    }

    public long deleteById(long id) {
        long deleteRows = 0;
        try {
            Open();
            deleteRows = db.delete(ClienteModel.TABLE_NAME, ClienteModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
        return deleteRows;
    }

    public ArrayList<ClienteModel> selectAll(long id_usuario) {
        ArrayList<ClienteModel> listaCliente = new ArrayList<>();
        Cursor cursor = null;
        try {
            Open();
            if (id_usuario == -1) {
                Log.e("ClienteDAO", "ID do usuário inválido: " + id_usuario);
                return listaCliente;
            }

            cursor = db.query(ClienteModel.TABLE_NAME, colunas,
                    ClienteModel.COLUNA_ID_USUARIO + " = ?",
                    new String[]{String.valueOf(id_usuario)}, null, null, null);

            // Verifica se o cursor não é nulo e contém dados
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ClienteModel cliente = new ClienteModel();
                    cliente.setId(cursor.getLong(0));
                    cliente.setId_usuario(cursor.getLong(1));
                    cliente.setNome(cursor.getString(2));
                    cliente.setCpf_cnpj(cursor.getString(3));
                    cliente.setCidade(cursor.getString(4));
                    cliente.setEstado(cursor.getString(5));
                    cliente.setTelefone(cursor.getString(6));
                    listaCliente.add(cliente);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ClienteDAO", "Erro ao consultar clientes: ", e);
        } finally {
            if (cursor != null) {
                cursor.close(); // Garante que o cursor seja fechado
            }
            Close();
        }
        Log.d("ClienteDAO", "Total de clientes retornados: " + listaCliente.size()); // Log para depuração
        return listaCliente;
    }

    public ClienteModel selectById(long id) {
        ClienteModel cliente = null;
        try {
            Open();
            Cursor cursor = db.query(ClienteModel.TABLE_NAME, colunas, ClienteModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                cliente = new ClienteModel();
                cliente.setId(cursor.getLong(0));
                cliente.setId_usuario(cursor.getLong(1));
                cliente.setNome(cursor.getString(2));
                cliente.setCpf_cnpj(cursor.getString(3));
                cliente.setCidade(cursor.getString(4));
                cliente.setEstado(cursor.getString(5));
                cliente.setTelefone(cursor.getString(6));
            }
            if (cursor != null) {
                cursor.close();
            }
        } finally {
            Close();
        }
        return cliente;
    }

    public boolean checkIfCpfCnpjIsUsed(long id_usuario, String cpf_cnpj) {
        return checkIfCpfCnpjIsUsed(id_usuario, cpf_cnpj, -1);
    }

    public boolean checkIfCpfCnpjIsUsed(long idUsuario, String cpfCnpj, long idCliente) {
        boolean existe;
        String queryString;
        String[] selectionArgs;
        if (idCliente == -1) {
            queryString = ClienteModel.COLUNA_ID_USUARIO + " = ? AND " + ClienteModel.COLUNA_CPF_CNPJ + " = ?";
            selectionArgs = new String[]{String.valueOf(idUsuario), cpfCnpj};
        } else {
            queryString = ClienteModel.COLUNA_ID_USUARIO + " = ? AND " + ClienteModel.COLUNA_CPF_CNPJ + " = ? AND " + ClienteModel.COLUNA_ID + " != ?";
            selectionArgs = new String[]{String.valueOf(idUsuario), cpfCnpj, String.valueOf(idCliente)};
        }
        try {
            Open();
            Cursor cursor = db.query(ClienteModel.TABLE_NAME, colunas, queryString, selectionArgs, null, null, null);
            existe = cursor.moveToFirst();
            cursor.close();
        } finally {
            Close();
        }
        return existe;
    }
}
