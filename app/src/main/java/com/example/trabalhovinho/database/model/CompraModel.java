package com.example.trabalhovinho.database.model;

public class CompraModel {
    public static final String TABLE_NAME = "tb_compras";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_USUARIO = "id_usuario",
            COLUNA_ID_CLIENTE = "id_cliente",
            COLUNA_ID_VINHO = "id_vinho",
            COLUNA_DATA = "data",
            COLUNA_QTD_VINHOS = "qtd_vinhos";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_ID_USUARIO + " INTEGER NOT NULL, " +
            COLUNA_ID_CLIENTE + " INTEGER NOT NULL, " +
            COLUNA_ID_VINHO + " INTEGER NOT NULL, " +
            COLUNA_DATA + " TEXT NOT NULL, " +
            COLUNA_QTD_VINHOS + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + COLUNA_ID_USUARIO + ") REFERENCES tb_usuarios(_id), " +
            "FOREIGN KEY (" + COLUNA_ID_CLIENTE + ") REFERENCES tb_clientes(_id), " +
            "FOREIGN KEY (" + COLUNA_ID_VINHO + ") REFERENCES tb_vinhos(_id)" +
            ");";

    public static final String
            DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long id;
    private long id_usuario;
    private long id_cliente;
    private long id_vinho;
    private String data;
    private int qtd_vinhos;

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public long getId_vinho() {
        return id_vinho;
    }

    public void setId_vinho(long id_vinho) {
        this.id_vinho = id_vinho;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getQtd_vinhos() {
        return qtd_vinhos;
    }

    public void setQtd_vinhos(int qtd_vinhos) {
        this.qtd_vinhos = qtd_vinhos;
    }
}
