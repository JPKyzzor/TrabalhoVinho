package com.example.trabalhovinho;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.UsefulFunctions;

public class PaginaEsqueceuSenhaActivity extends AppCompatActivity {
    private Button botaoVoltar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_esqueceu_a_senha);
        botaoVoltar = findViewById(R.id.backButton);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsefulFunctions.finalizaIntent(PaginaEsqueceuSenhaActivity.this);
            }
        });
    }
    

}
