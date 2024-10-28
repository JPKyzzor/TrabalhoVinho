package com.example.trabalhovinho;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.Shared.UsefulFunctions;
import com.example.trabalhovinho.adapter.ClienteSelectAdapter;
import com.example.trabalhovinho.adapter.VinhoSelectAdapter;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.dao.CompraDAO;
import com.example.trabalhovinho.database.dao.VinhoDAO;
import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.CompraModel;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FormularioComprasActivity extends AppCompatActivity {
    private Spinner spinnerClientes, spinnerVinhos;
    private Button cadastroButton, cancelarButton;
    private ClienteDAO clienteDAO;
    private VinhoDAO vinhoDAO;
    private CompraDAO compraDAO;
    private ArrayList<ClienteModel> listaClientes;
    private ArrayList<VinhoModel> listaVinhos;
    private long idCompraEdit;
    private long idClienteSelecionado = -1;
    private long idVinhoSelecionado = -1;
    private long idUsuarioLogado;
    private EditText quantidadeVinhosEditText, dataVendaEditText;
    private TextView valorTotalTextView;
    private Calendar calendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_compras);
        spinnerClientes = findViewById(R.id.clienteSpinner);
        spinnerVinhos = findViewById(R.id.vinhoSpinner);
        quantidadeVinhosEditText = findViewById(R.id.quantidadeEditText);
        dataVendaEditText = findViewById(R.id.dataVendaEditText);
        valorTotalTextView = findViewById(R.id.valorTotalTextView);
        cancelarButton = findViewById(R.id.cancelarCompraButton);
        cadastroButton = findViewById(R.id.cadastrarCompraButton);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        idUsuarioLogado = preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO,-1);
        idCompraEdit = preferences.getLong(SharedKeys.KEY_ID_COMPRA_EDIT,-1);
        clienteDAO = new ClienteDAO(this);
        vinhoDAO = new VinhoDAO(this);
        compraDAO = new CompraDAO(this);
        listaClientes = clienteDAO.selectAll(idUsuarioLogado);
        listaVinhos = vinhoDAO.selectAll(idUsuarioLogado);

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarParaLista();
            }
        });
        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qtdVinhos = quantidadeVinhosEditText.getText().toString().trim();
                String data = dataVendaEditText.getText().toString().trim();
                if(idClienteSelecionado==-1){
                    mostrarAlerta("Erro", "Selecione um cliente", "Ok");
                    return;
                }
                if(idVinhoSelecionado==-1){
                    mostrarAlerta("Erro", "Selecione um vinho", "Ok");
                    return;
                }
                //validacao de estoque disponivel
                if (qtdVinhos.isEmpty()) {
                    mostrarAlerta("Erro", "Campo estoque não está preenchido", "Ok");
                    return;
                }
                if (!qtdVinhos.matches("\\d+")) {
                    mostrarAlerta("Erro", "O campo estoque só pode possuir números inteiros", "Ok");
                    return;
                }
                int qtdVinhosInt;
                try{
                    qtdVinhosInt = Integer.parseInt(qtdVinhos);
                    if(!estoqueValido(idCompraEdit, qtdVinhosInt, idVinhoSelecionado)) {
                        return;
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Erro", "Valor inválido no campo estoque", "Ok");
                    return;
                }
                //validação datepicker
                if(data.isEmpty()){
                    mostrarAlerta("Erro", "Selecione uma data", "Ok");
                    return;
                }

                CompraModel compra = new CompraModel();
                compra.setId_usuario(idUsuarioLogado);
                compra.setId_cliente(idClienteSelecionado);
                compra.setId_vinho(idVinhoSelecionado);
                compra.setPreco_total(setaValorTotal());
                compra.setQtd_vinhos(qtdVinhosInt);
                compra.setData(data);
                if(idCompraEdit!=-1){
                    compra.setId(idCompraEdit);
                    compraDAO.update(compra);
                    mostrarAlerta2("Sucesso", "Compra editada com sucesso", "Ir para vinhos");
                }else{
                    compraDAO.insert(compra);
                    Log.d("QTDVINHO",String.valueOf(compra.getQtd_vinhos()));
                    mostrarAlerta2("Sucesso", "Compra criada com sucesso", "Ir para vinhos");
                }
            }
        });
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
        itemPadraoVinho.setNome("Nome do vinho");
        itemPadraoVinho.setTipo("tipo");
        itemPadraoVinho.setSafra("safra");
        itemPadraoVinho.setId(-1);
        listaVinhos.add(0, itemPadraoVinho);
        spinnerVinhos.setAdapter(new VinhoSelectAdapter(FormularioComprasActivity.this, listaVinhos));
        spinnerVinhos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

        //Montagem date picker
        calendar = Calendar.getInstance();
        dataVendaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Criação do DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormularioComprasActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                // Ajustar o mês (os meses começam em 0)
                                String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                                dataVendaEditText.setText(date);
                            }
                        },
                        year, month, day);
                datePickerDialog.show(); // Exibir o DatePicker
            }
        });

        //PREENCHE CAMPOS SE ESTÁ EDITANDO
        if(idCompraEdit!=-1){
            CompraModel compraEdit = compraDAO.selectById(idCompraEdit);
            idClienteSelecionado = compraEdit.getId_cliente();
            idVinhoSelecionado = compraEdit.getId_vinho();
            quantidadeVinhosEditText.setText(String.valueOf(compraEdit.getQtd_vinhos()));
            dataVendaEditText.setText(compraEdit.getData());
            valorTotalTextView.setText(String.valueOf(compraEdit.getPreco_total()));
            //seta spinner clientes
            for (int i = 0; i < listaClientes.size(); i++) {
                if (listaClientes.get(i).getId() == compraEdit.getId_cliente()) {
                    spinnerClientes.setSelection(i);
                    break;
                }
            }
            //seta spinner vinhos
            for (int i = 0; i < listaVinhos.size(); i++) {
                if (listaVinhos.get(i).getId() == compraEdit.getId_vinho()) {
                    spinnerVinhos.setSelection(i);
                    break;
                }
            }
            setaValorTotal();
        }

    }

    private float setaValorTotal(){
        float valorTotal = 0;
        if(idVinhoSelecionado!=-1 && !quantidadeVinhosEditText.getText().toString().trim().isEmpty()){
            int qtdVinhos = Integer.parseInt(quantidadeVinhosEditText.getText().toString().trim());
            valorTotal = vinhoDAO.selectById(idVinhoSelecionado).getPreco()*qtdVinhos;
        }
        valorTotalTextView.setText(String.format(Locale.US, "R$ %.2f", valorTotal));
        return valorTotal;
    }

    private void redirecionarParaLista() {
        UsefulFunctions.finalizaIntent(this);
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

    private boolean estoqueValido(long idCompraAntiga, int qtdVinhosNovos, long idNovoVinho){
        VinhoModel vinhoVinculado = vinhoDAO.selectById(idNovoVinho);
        if (qtdVinhosNovos < 1) {
            mostrarAlerta("Erro", "O campo de quantidade de vinhos deve ser maior que zero", "Ok");
            return false;
        }
        if(idCompraAntiga==-1){
            //nova compra
            if(vinhoVinculado.getEstoque()<qtdVinhosNovos){
                mostrarAlerta("Erro", "Você não tem estoque suficiente: "+ vinhoVinculado.getEstoque() + " restantes", "Ok");
                return false;
            }
        }
        else{
            //edição
            CompraModel compraAntiga = compraDAO.selectById(idCompraAntiga);
            //vinho alterou
            if(compraAntiga.getId_vinho() != idNovoVinho){
                VinhoModel novoVinho = vinhoDAO.selectById(idNovoVinho);
                if(novoVinho.getEstoque()<qtdVinhosNovos){
                    mostrarAlerta("Erro", "Você não tem estoque suficiente: "+ novoVinho.getEstoque() + " restantes", "Ok");
                    return false;
                }
            }
            //vinho não alterou
            else{
                if(vinhoVinculado.getEstoque()+compraAntiga.getQtd_vinhos()<qtdVinhosNovos){
                    mostrarAlerta("Erro", "Você não tem estoque suficiente: "+ vinhoVinculado.getEstoque() + " restantes", "Ok");
                    return false;
                }
            }
        }
        return true;
    }

}
