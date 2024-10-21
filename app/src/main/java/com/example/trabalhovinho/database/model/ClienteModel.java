package com.example.trabalhovinho.database.model;

public class ClienteModel {
    public static final String TABLE_NAME = "tb_clientes";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_ID_USUARIO = "id_usuario",
            COLUNA_NOME = "nome",
            COLUNA_CPF_CNPJ = "cpf_cnpj",
            COLUNA_CIDADE = "cidade",
            COLUNA_ESTADO = "estado",
            COLUNA_TELEFONE = "telefone";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUNA_ID_USUARIO + " INTEGER NOT NULL, " +
                    COLUNA_NOME + " TEXT NOT NULL, " +
                    COLUNA_CPF_CNPJ + " TEXT NOT NULL UNIQUE, " +
                    COLUNA_CIDADE + " TEXT NOT NULL, " +
                    COLUNA_ESTADO + " TEXT NOT NULL, " +
                    COLUNA_TELEFONE + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + COLUNA_ID_USUARIO + ") REFERENCES tb_usuarios(_id)" +
                    ");";

    public static final String
            DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private long id;
    private long id_usuario;
    private String nome;
    private String cpf_cnpj;
    private String cidade;
    private String estado;
    private String telefone;

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

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
