package com.example.trabalhovinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.Shared.UsefulFunctions;
import com.example.trabalhovinho.adapter.CompraAdapter;
import com.example.trabalhovinho.adapter.VinhoAdapter;
import com.example.trabalhovinho.database.dao.VinhoDAO;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.ArrayList;

public class PaginaListaVinhosActivity extends AppCompatActivity {
    private ListView listViewVinhos;
    private Button botaoCadastro;
    private VinhoDAO vinhoDAO;
    private ImageView setinha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lista_vinhos);
        botaoCadastro = findViewById(R.id.addWineButton);
        listViewVinhos = findViewById(R.id.wineListView);
        setinha = findViewById(R.id.setinha);

        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsefulFunctions.finalizaIntent(PaginaListaVinhosActivity.this);
            }
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaListaVinhosActivity.this);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putLong(SharedKeys.KEY_ID_VINHO_EDIT, -1);
                edit.apply();
                Intent it = new Intent(PaginaListaVinhosActivity.this, FormularioVinhoActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    private void carregarDados() {
        vinhoDAO = new VinhoDAO(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaListaVinhosActivity.this);
        ArrayList<VinhoModel> lista = vinhoDAO.selectAll(preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1));

        if (lista.isEmpty()) {
            findViewById(R.id.wineListView).setVisibility(View.GONE);
            findViewById(R.id.semVinhosMensagem).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.wineListView).setVisibility(View.VISIBLE);
            findViewById(R.id.semVinhosMensagem).setVisibility(View.GONE);
            listViewVinhos.setAdapter(new VinhoAdapter(PaginaListaVinhosActivity.this, lista));
        }
    }
}
