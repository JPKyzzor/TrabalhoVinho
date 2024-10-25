package com.example.trabalhovinho;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaginaListaComprasActivity extends AppCompatActivity {
    private Button botaoCadastro;
    private ImageView setinha;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lista_compras);
        botaoCadastro = findViewById(R.id.addPurchaseButton);
        setinha = findViewById(R.id.setinha);

        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PaginaListaComprasActivity.this, PaginaMenuActivity.class);
                startActivity(it);
            }
        });
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaListaComprasActivity.this, FormularioComprasActivity.class);
                startActivity(it);
            }
        });
    }
}