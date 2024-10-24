package com.example.trabalhovinho;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.adapter.ClienteSelectAdapter;
import com.example.trabalhovinho.adapter.VinhoSelectAdapter;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.dao.CompraDAO;
import com.example.trabalhovinho.database.dao.VinhoDAO;
import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.CompraModel;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.ArrayList;

public class FormularioComprasActivity extends AppCompatActivity {
    private Spinner spinnerClientes, spinnerVinhos;
    private ClienteDAO clienteDAO;
    private VinhoDAO vinhoDAO;
    private CompraDAO compraDAO;
    private ArrayList<ClienteModel> listaClientes;
    private ArrayList<VinhoModel> listaVinhos;
    private long idCompraEdit;
    private long idClienteSelecionado = -1;
    private long idVinhoSelecionado = -1;
    private long idUsuarioLogado;
    private EditText quantidadeVinhosEditText;
    private TextView valorTotalTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_compras);
        spinnerClientes = findViewById(R.id.clienteSpinner);
        spinnerVinhos = findViewById(R.id.vinhoSpinner);
        quantidadeVinhosEditText = findViewById(R.id.quantidadeEditText);
        valorTotalTextView = findViewById(R.id.valorTotalTextView);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        idUsuarioLogado = preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO,-1);
        idCompraEdit = preferences.getLong(SharedKeys.KEY_ID_COMPRA_EDIT,-1);
        clienteDAO = new ClienteDAO(this);
        vinhoDAO = new VinhoDAO(this);
        compraDAO = new CompraDAO(this);
        listaClientes = clienteDAO.selectAll(idUsuarioLogado);
        listaVinhos = vinhoDAO.selectAll(idUsuarioLogado);

        //Montagem seletor clientes
        ClienteModel itemPadraoCliente = new ClienteModel();
        itemPadraoCliente.setNome("Selecione um cliente");
        itemPadraoCliente.setId(-1);
        listaClientes.add(0, itemPadraoCliente);
        spinnerClientes.setAdapter(new ClienteSelectAdapter(FormularioComprasActivity.this, listaClientes));
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

        //Montagem seletor vinhos
        VinhoModel itemPadraoVinho = new VinhoModel();
        itemPadraoVinho.setNome("Selecione um vinho");
        itemPadraoVinho.setId(-1);
        listaVinhos.add(0, itemPadraoVinho);
        spinnerVinhos.setAdapter(new VinhoSelectAdapter(FormularioComprasActivity.this, listaVinhos));
        spinnerVinhos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    return;
                }
                VinhoModel vinhoSelecionado = (VinhoModel) parent.getItemAtPosition(position);
                idVinhoSelecionado = vinhoSelecionado.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Montagem qtd vinhos
        quantidadeVinhosEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
            setaValorTotal();}

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //PREENCHE CAMPOS SE ESTÁ EDITANDO
        if(idCompraEdit!=-1){
            CompraModel compraEdit = compraDAO.selectById(idCompraEdit);
            idClienteSelecionado = compraEdit.getId_cliente();
            idVinhoSelecionado = compraEdit.getId_vinho();
            quantidadeVinhosEditText.setText(compraEdit.getQtd_vinhos());
            //seta spinner clientes
            for (int i = 0; i < listaClientes.size(); i++) {
                if (listaClientes.get(i).getId() == compraEdit.getId()) {
                    spinnerClientes.setSelection(i);
                    break;
                }
            }
            //seta spinner vinhos
            for (int i = 0; i < listaVinhos.size(); i++) {
                if (listaVinhos.get(i).getId() == compraEdit.getId()) {
                    spinnerClientes.setSelection(i);
                    break;
                }
            }
            setaValorTotal();
        }
    }

    private void setaValorTotal(){
        float valorTotal = 0;
        if(idVinhoSelecionado!=-1 && !quantidadeVinhosEditText.getText().toString().trim().isEmpty()){
            int qtdVinhos = Integer.parseInt(quantidadeVinhosEditText.getText().toString().trim());
            valorTotal = 5*qtdVinhos;
        }
        valorTotalTextView.setText(String.format("R$ %.2f", valorTotal));
    }

    private void redirecionarParaLista() {
        Intent it = new Intent(FormularioComprasActivity.this, PaginaListaComprasActivity.class);
        startActivity(it);
    }

    private void mostrarAlerta(String titulo, String mensagem, String botaoOK) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setPositiveButton(botaoOK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void mostrarAlerta2(String titulo, String mensagem, String botaoOK) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setPositiveButton(botaoOK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        redirecionarParaLista();
                    }
                })
                .show();
    }

}
