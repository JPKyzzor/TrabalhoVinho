package com.example.trabalhovinho.database.model;

public class CompraModel {
    public static final String TABLE_NAME = "tb_compras";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_USUARIO = "id_usuario",
            COLUNA_ID_CLIENTE = "id_cliente",
            COLUNA_ID_VINHO = "id_vinho",
            COLUNA_DATA = "data",
            COLUNA_QTD_VINHOS = "qtd_vinhos",
            COLUNA_PRECO_TOTAL = "preco_total",
            TRIGGER_ESTOQUE = "trigger_estoque";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_ID_USUARIO + " INTEGER NOT NULL, " +
            COLUNA_ID_CLIENTE + " INTEGER NOT NULL, " +
            COLUNA_ID_VINHO + " INTEGER NOT NULL, " +
            COLUNA_DATA + " TEXT NOT NULL, " +
            COLUNA_QTD_VINHOS + " INTEGER NOT NULL, " +
            COLUNA_PRECO_TOTAL + " REAL NOT NULL, " +
            "FOREIGN KEY (" + COLUNA_ID_USUARIO + ") REFERENCES tb_usuarios(_id), " +
            "FOREIGN KEY (" + COLUNA_ID_CLIENTE + ") REFERENCES tb_clientes(_id), " +
            "FOREIGN KEY (" + COLUNA_ID_VINHO + ") REFERENCES tb_vinhos(_id)" +
            ");";

    public static final String CREATE_TRIGGER_ATUALIZAR_ESTOQUE_INSERT =
            "CREATE TRIGGER IF NOT EXISTS trigger_estoque_insert " +
                    "AFTER INSERT ON " + TABLE_NAME + " " +
                    "BEGIN " +
                    "   UPDATE " + VinhoModel.TABLE_NAME + " " +
                    "   SET " + VinhoModel.COLUNA_ESTOQUE + " = " +
                    "   CASE WHEN (" + VinhoModel.COLUNA_ESTOQUE + " - NEW." + COLUNA_QTD_VINHOS + ") < 0 THEN 0 " +
                    "        ELSE " + VinhoModel.COLUNA_ESTOQUE + " - NEW." + COLUNA_QTD_VINHOS + " END " +
                    "   WHERE _id = NEW." + COLUNA_ID_VINHO + ";" +
                    "END;";

    public static final String CREATE_TRIGGER_ATUALIZAR_ESTOQUE_UPDATE =
            "CREATE TRIGGER IF NOT EXISTS trigger_estoque_update " +
                    "AFTER UPDATE ON " + TABLE_NAME + " " +
                    "BEGIN " +
                    "   UPDATE " + VinhoModel.TABLE_NAME + " " +
                    "   SET " + VinhoModel.COLUNA_ESTOQUE + " = " + VinhoModel.COLUNA_ESTOQUE + " + OLD." + COLUNA_QTD_VINHOS + " " +
                    "   WHERE _id = OLD." + COLUNA_ID_VINHO + ";" +
                    "   " +
                    "   UPDATE " + VinhoModel.TABLE_NAME + " " +
                    "   SET " + VinhoModel.COLUNA_ESTOQUE + " = " +
                    "   CASE WHEN (" + VinhoModel.COLUNA_ESTOQUE + " - NEW." + COLUNA_QTD_VINHOS + ") < 0 THEN 0 " +
                    "        ELSE " + VinhoModel.COLUNA_ESTOQUE + " - NEW." + COLUNA_QTD_VINHOS + " END " +
                    "   WHERE _id = NEW." + COLUNA_ID_VINHO + ";" +
                    "END;";




    public static final String
            DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long id;
    private long id_usuario;
    private long id_cliente;
    private long id_vinho;
    private String data;
    private int qtd_vinhos;
    private float preco_total;

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

    public float getPreco_total() {
        return preco_total;
    }

    public void setPreco_total(float preco_total) {
        this.preco_total = preco_total;
    }
}
