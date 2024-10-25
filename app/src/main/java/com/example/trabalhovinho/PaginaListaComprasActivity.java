package com.example.trabalhovinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
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
import com.example.trabalhovinho.adapter.ClienteAdapter;
import com.example.trabalhovinho.adapter.CompraAdapter;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.dao.CompraDAO;
import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.CompraModel;

import java.util.ArrayList;

public class PaginaListaComprasActivity extends AppCompatActivity {
    private Button botaoCadastro;
    private CompraDAO compraDAO;
    private ListView listViewCompras;
    private ImageView setinha;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lista_compras);
        botaoCadastro = findViewById(R.id.addPurchaseButton);
        listViewCompras = findViewById(R.id.purchaseListView);
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaListaComprasActivity.this);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putLong(SharedKeys.KEY_ID_COMPRA_EDIT, -1);
                edit.apply();
                Intent it = new Intent(PaginaListaComprasActivity.this, FormularioComprasActivity.class);
                startActivity(it);
            }
        });
        carregarDados();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    private void carregarDados(){
        compraDAO = new CompraDAO(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaListaComprasActivity.this);
        ArrayList<CompraModel> lista = compraDAO.selectAll(preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1));
        Log.d("ListaCompras", "Tamanho da lista: " + lista.size());
        listViewCompras.setAdapter(new CompraAdapter(PaginaListaComprasActivity.this,lista));
    }
}