package com.example.trabalhovinho;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.model.ClienteModel;

public class FormularioClienteActivity extends AppCompatActivity {
    private EditText campoNome, campoCpfCnpj, campoCidade, campoEstado, campoTelefone;
    private Button botaoCadastrar, botaoCancelar;
    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_clientes);
        campoNome = findViewById(R.id.clientNameEditText);
        campoCpfCnpj = findViewById(R.id.clientCpfCnpjEditText);
        campoCidade = findViewById(R.id.clientCityEditText);
        campoEstado = findViewById(R.id.clientStateEditText);
        campoTelefone = findViewById(R.id.clientPhoneEditText);
        botaoCadastrar = findViewById(R.id.saveClientButton);
        botaoCancelar = findViewById(R.id.cancelClientButton);
        clienteDAO = new ClienteDAO(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FormularioClienteActivity.this);
        long idClienteEdicao = preferences.getLong(SharedKeys.KEY_ID_CLIENTE_EDIT, -1);
        //VERIFICA SE ESTA EDITANDO
        if(idClienteEdicao!=-1){
            ClienteModel clienteEdit = clienteDAO.selectById(idClienteEdicao);
            campoNome.setText(clienteEdit.getNome());
            campoCpfCnpj.setText(clienteEdit.getCpf_cnpj());
            campoCidade.setText(clienteEdit.getCidade());
            campoEstado.setText(clienteEdit.getEstado());
            campoTelefone.setText(clienteEdit.getTelefone());
        }
        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarParaLista();
            }
        });
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString().trim();
                String cpfCnpj = campoCpfCnpj.getText().toString().trim();
                String cidade = campoCidade.getText().toString().trim();
                String estado = campoEstado.getText().toString().trim();
                String telefone = campoTelefone.getText().toString().trim();
                long idUsuario = preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1);

                // Validação do nome
                if (nome.isEmpty()) {
                    mostrarAlerta("Erro", "Campo nome não está preenchido", "Ok");
                    return;
                }
                if (!nome.matches("[a-zA-Z\\p{L}\\s]+")) {
                    mostrarAlerta("Erro", "O nome não pode conter números ou caracteres especiais", "Ok");
                    return;
                }
                if (nome.length() > 50) {
                    mostrarAlerta("Erro", "O nome é muito longo", "Ok");
                    return;
                }
                // Validação do CPF/CNPJ
                if (cpfCnpj.isEmpty()) {
                    mostrarAlerta("Erro", "Campo CPF/CNPJ não está preenchido", "Ok");
                    return;
                }
                if (!validarCpfCnpj(cpfCnpj)) {
                    mostrarAlerta("Erro", "CPF/CNPJ inválido", "Ok");
                    return;
                }
                if(idClienteEdicao!=-1){
                    //esta editando
                    if (clienteDAO.checkIfCpfCnpjIsUsed(idUsuario, cpfCnpj, idClienteEdicao)) {
                        mostrarAlerta("Erro", "CPF/CNPJ já está em uso", "Ok");
                        return;
                    }
                }else{
                    //novo cliente
                    if (clienteDAO.checkIfCpfCnpjIsUsed(idUsuario, cpfCnpj)) {
                        mostrarAlerta("Erro", "CPF/CNPJ já está em uso", "Ok");
                        return;
                    }
                }
                // Validação da cidade
                if (cidade.isEmpty()) {
                    mostrarAlerta("Erro", "Campo cidade não está preenchido", "Ok");
                    return;
                }
                if (!cidade.matches("[a-zA-Z\\p{L}\\s]+")) {
                    mostrarAlerta("Erro", "A cidade não pode conter números ou caracteres especiais", "Ok");
                    return;
                }
                if (cidade.length() > 50) {
                    mostrarAlerta("Erro", "O nome da cidade é muito longo", "Ok");
                    return;
                }
                // Validação do estado
                if (estado.isEmpty()) {
                    mostrarAlerta("Erro", "Campo estado não está preenchido", "Ok");
                    return;
                }
                if (!estado.matches("[A-Z]{2}")) {
                    mostrarAlerta("Erro", "Estado inválido. Deve conter 2 letras maiúsculas, ex: SP", "Ok");
                    return;
                }
                // Validação do telefone
                if (telefone.isEmpty()) {
                    mostrarAlerta("Erro", "Campo telefone não está preenchido", "Ok");
                    return;
                }
                String telefoneLimpo = telefone.replaceAll("[^\\d]", "");
                if (!telefoneLimpo.matches("\\d{11}")) {
                    mostrarAlerta("Erro", "Telefone inválido. Deve seguir o formato: (99) 99999-9999", "Ok");
                    return;
                }
                ClienteModel cliente = new ClienteModel();
                if(idClienteEdicao!=-1){
                    cliente.setId(idClienteEdicao);
                }
                cliente.setNome(nome);
                cliente.setCpf_cnpj(cpfCnpj.replaceAll("[^\\d]", ""));
                cliente.setCidade(cidade);
                cliente.setEstado(estado);
                cliente.setTelefone(telefone);
                cliente.setId_usuario(idUsuario);
                if(idClienteEdicao!=-1){
                    clienteDAO.update(cliente);
                    mostrarAlerta2("Sucesso","Cliente editado com sucesso", "Ir para clientes");
                }else{
                    clienteDAO.insert(cliente);
                    mostrarAlerta2("Sucesso","Cliente criado com sucesso", "Ir para clientes");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FormularioClienteActivity.this);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(SharedKeys.KEY_ID_CLIENTE_EDIT, -1);
        edit.apply();
    }

    private void redirecionarParaLista() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
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

    public boolean validarCpfCnpj(String cpfCnpj) {
        String cpfCnpjLimpo = cpfCnpj.replaceAll("[^\\d]", "");
        return cpfCnpjLimpo.matches("\\d{11}") || cpfCnpjLimpo.matches("\\d{14}");
    }
}
