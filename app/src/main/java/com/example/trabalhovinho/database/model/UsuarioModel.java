package com.example.trabalhovinho.database.model;

public class UsuarioModel {
    public static final String TABLE_NAME = "tb_usuarios";

    public static final String
        COLUNA_ID = "_id",
        COLUNA_NOME = "nome",
        COLUNA_EMAIL = "email",
        COLUNA_SENHA = "senha";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + TABLE_NAME + " (" +
        COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUNA_NOME + " TEXT NOT NULL, " +
        COLUNA_EMAIL + " TEXT NOT NULL UNIQUE, " +
        COLUNA_SENHA + " TEXT NOT NULL" +
        ");";

    public static final String
        DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long id;
    private String nome;
    private String email;
    private String senha;

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

