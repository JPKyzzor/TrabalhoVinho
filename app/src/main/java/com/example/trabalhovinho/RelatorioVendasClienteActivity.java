package com.example.trabalhovinho;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.adapter.ClienteSelectAdapter;
import com.example.trabalhovinho.database.model.ClienteModel;

import java.util.ArrayList;

public class RelatorioVendasClienteActivity extends AppCompatActivity {
    private Spinner spinnerClientes;
    private ImageView setinha;
    private ArrayList<ClienteModel> listaClientes;
    private long idClienteSelecionado = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios_de_vendas_cliente);
        spinnerClientes = findViewById(R.id.selectCliente);
        setinha = findViewById(R.id.setinha);

        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(RelatorioVendasClienteActivity.this, PaginaMenuActivity.class);
                startActivity(it);
            }
        });


        ClienteModel itemPadraoCliente = new ClienteModel();
        itemPadraoCliente.setNome("Selecione um cliente");
        itemPadraoCliente.setId(-1);
        listaClientes.add(0, itemPadraoCliente);
        spinnerClientes.setAdapter(new ClienteSelectAdapter(RelatorioVendasClienteActivity.this, listaClientes));
        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    return;
                }
                ClienteModel clienteSelecionado = (ClienteModel) parent.getItemAtPosition(position);
                idClienteSelecionado = clienteSelecionado.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
