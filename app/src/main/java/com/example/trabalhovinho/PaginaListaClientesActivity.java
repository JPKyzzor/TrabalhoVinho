package com.example.trabalhovinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.Shared.UsefulFunctions;
import com.example.trabalhovinho.adapter.ClienteAdapter;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.model.ClienteModel;

import java.util.ArrayList;

public class PaginaListaClientesActivity extends AppCompatActivity {
    private Button botaoCadastro;
    private ListView listViewClientes;
    private ClienteDAO clienteDAO;
    private ImageView setinha;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lista_clientes);
        botaoCadastro = findViewById(R.id.addClientButton);
        setinha = findViewById(R.id.setinha);
        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsefulFunctions.finalizaIntent(PaginaListaClientesActivity.this);
            }
        });
        listViewClientes = findViewById(R.id.clientListView);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaListaClientesActivity.this, FormularioClienteActivity.class);
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
        clienteDAO = new ClienteDAO(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaListaClientesActivity.this);
        ArrayList<ClienteModel> lista = clienteDAO.selectAll(preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1));
        Log.d("ListaClientes", "Tamanho da lista: " + lista.size());
        listViewClientes.setAdapter(new ClienteAdapter(PaginaListaClientesActivity.this,lista));
    }

}
