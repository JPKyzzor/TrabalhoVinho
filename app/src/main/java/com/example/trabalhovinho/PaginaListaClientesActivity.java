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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.Shared.UsefulFunctions;
import com.example.trabalhovinho.adapter.ClienteAdapter;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.model.ClienteModel;

import java.util.ArrayList;

public class PaginaListaClientesActivity extends AppCompatActivity {
    private ListView listViewClientes;
    private Button botaoCadastro;
    private ClienteDAO clienteDAO;
    private ImageView setinha;
    private TextView semClientesMensagem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lista_clientes);

        botaoCadastro = findViewById(R.id.addClientButton);
        listViewClientes = findViewById(R.id.clientListView);
        setinha = findViewById(R.id.setinha);
        semClientesMensagem = findViewById(R.id.semClientesMensagem);

        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsefulFunctions.finalizaIntent(PaginaListaClientesActivity.this);
            }
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaListaClientesActivity.this);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putLong(SharedKeys.KEY_ID_CLIENTE_EDIT, -1);
                edit.apply();
                Intent it = new Intent(PaginaListaClientesActivity.this, FormularioClienteActivity.class);
                startActivity(it);
            }
        });
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

        if (lista.isEmpty()) {
            listViewClientes.setVisibility(View.GONE);
            semClientesMensagem.setVisibility(View.VISIBLE);
        } else {
            listViewClientes.setVisibility(View.VISIBLE);
            semClientesMensagem.setVisibility(View.GONE);
            listViewClientes.setAdapter(new ClienteAdapter(PaginaListaClientesActivity.this, lista));
        }
    }
}
