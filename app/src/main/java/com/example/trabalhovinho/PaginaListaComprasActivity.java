package com.example.trabalhovinho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaginaListaComprasActivity extends AppCompatActivity {
    private Button botaoCadastro;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lista_clientes);
        botaoCadastro = findViewById(R.id.addClientButton);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaListaComprasActivity.this, FormularioComprasActivity.class);
                startActivity(it);
            }
        });
    }
}