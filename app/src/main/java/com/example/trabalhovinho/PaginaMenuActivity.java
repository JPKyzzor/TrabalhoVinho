package com.example.trabalhovinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.database.dao.UsuarioDAO;

public class PaginaMenuActivity extends AppCompatActivity {

    private ImageView botaoClientes, botaoVinhos, botaoCompras, botaoRelatorioCliente, botaoRelatorioMes, botaoLogout;
    private TextView nomeCliente;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_menu);
        botaoClientes = findViewById(R.id.botaoClientes);
        botaoRelatorioCliente = findViewById(R.id.botaoRelatorioCliente);
        botaoRelatorioMes = findViewById(R.id.botaoRelatorioMes);
        botaoVinhos = findViewById(R.id.botaoVinhos);
        botaoCompras = findViewById(R.id.botaoCompras);
        botaoLogout = findViewById(R.id.botaoLogout);
        nomeCliente = findViewById(R.id.menuNomeCliente);
        usuarioDAO = new UsuarioDAO(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaMenuActivity.this);
        String nomeUsuario = usuarioDAO.selectById(preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1)).getNome();
        nomeCliente.setText(String.format("Olá, %s", nomeUsuario));
        botaoClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaMenuActivity.this, PaginaListaClientesActivity.class);
                startActivity(it);
            }
        });
        botaoVinhos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaMenuActivity.this, PaginaListaVinhosActivity.class);
                startActivity(it);
            }
        });
        botaoCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaMenuActivity.this, PaginaListaComprasActivity.class);
                startActivity(it);
            }
        });
        botaoRelatorioMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PaginaMenuActivity.this, RelatorioVendasMesActivity.class);
                startActivity(it);
            }
        });
        botaoRelatorioCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PaginaMenuActivity.this, RelatorioVendasClienteActivity.class);
                startActivity(it);
            }
        });
        botaoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaMenuActivity.this, PaginaLoginActivity.class);
                startActivity(it);
            }
        });
    }
}
