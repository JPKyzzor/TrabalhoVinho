package com.example.trabalhovinho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaginaListaVinhosActivity extends AppCompatActivity {
    private Button botaoCadastro;
    private ImageView setinha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lista_vinhos);
        botaoCadastro = findViewById(R.id.addWineButton);
        setinha = findViewById(R.id.setinha);

        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PaginaListaVinhosActivity.this, PaginaMenuActivity.class);
                startActivity(it);
            }
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaListaVinhosActivity.this, FormularioVinhoActivity.class);
                startActivity(it);
            }
        });
    }
}
