package com.example.trabalhovinho.database.model;

public class VinhoModel {
    public static final String TABLE_NAME = "tb_vinhos";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_USUARIO = "id_usuario",
            COLUNA_NOME = "nome",
            COLUNA_TIPO = "tipo",
            COLUNA_SAFRA = "safra",
            COLUNA_PRECO = "preco",
            COLUNA_ESTOQUE = "estoque";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_ID_USUARIO + " INTEGER NOT NULL, " +
                    COLUNA_NOME + " TEXT NOT NULL, " +
                    COLUNA_TIPO + " TEXT NOT NULL, " +
                    COLUNA_SAFRA + " TEXT NOT NULL, " +
                    COLUNA_PRECO + " REAL NOT NULL, " +
                    COLUNA_ESTOQUE + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + COLUNA_ID_USUARIO + ") REFERENCES tb_usuarios(_id)" +
                    ");";

    public static final String
            DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long id;
    private long id_usuario;
    private String nome;
    private String tipo;
    private String safra;
    private float preco;
    private int estoque;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSafra() {
        return safra;
    }

    public void setSafra(String safra) {
        this.safra = safra;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
