package com.example.trabalhovinho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaginaMenuActivity extends AppCompatActivity {

    private ImageView botaoClientes, botaoVinhos, botaoCompras, botaoRelatorioCliente, botaoRelatorioMes, botaoLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_menu);
        botaoClientes = findViewById(R.id.botaoClientes);
        botaoVinhos = findViewById(R.id.botaoVinhos);
        botaoCompras = findViewById(R.id.botaoCompras);
        botaoLogout = findViewById(R.id.botaoLogout);
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
        botaoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaMenuActivity.this, PaginaLoginActivity.class);
                startActivity(it);
            }
        });
    }
}
