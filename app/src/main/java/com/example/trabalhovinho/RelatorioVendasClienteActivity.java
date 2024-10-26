package com.example.trabalhovinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.adapter.ClienteSelectAdapter;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.model.ClienteModel;

import java.util.ArrayList;

public class RelatorioVendasClienteActivity extends AppCompatActivity {
    private Spinner spinnerClientes;
    private ImageView setinha;
    private ArrayList<ClienteModel> listaClientes;
    private long idClienteSelecionado, idUsuarioLogado;
    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios_de_vendas_cliente);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        idUsuarioLogado = preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1);

        clienteDAO = new ClienteDAO(this);
        listaClientes = clienteDAO.selectAll(idUsuarioLogado);

        spinnerClientes = findViewById(R.id.selectCliente);
        setinha = findViewById(R.id.setinha);

        ClienteModel itemPadraoCliente = new ClienteModel();
        itemPadraoCliente.setNome("Selecione um cliente");
        itemPadraoCliente.setId(-1);
        listaClientes.add(0, itemPadraoCliente);

        spinnerClientes.setAdapter(new ClienteSelectAdapter(this, listaClientes));

        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(RelatorioVendasClienteActivity.this, PaginaMenuActivity.class);
                startActivity(it);
            }
        });

        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                ClienteModel clienteSelecionado = (ClienteModel) parent.getItemAtPosition(position);
                idClienteSelecionado = clienteSelecionado.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
