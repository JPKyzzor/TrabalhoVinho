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
import com.example.trabalhovinho.database.dao.VinhoDAO;
import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.Calendar;
import java.util.Locale;

public class FormularioVinhoActivity extends AppCompatActivity {
    private EditText campoNome, campoTipo, campoSafra, campoEstoque, campoPreco;
    private Button botaoCadastrar, botaoCancelar;
    private VinhoDAO vinhoDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_vinhos);
        campoNome = findViewById(R.id.wineNameEditText);
        campoTipo = findViewById(R.id.typeEditText);
        campoSafra = findViewById(R.id.safraEditText);
        campoEstoque = findViewById(R.id.stockEditText);
        campoPreco = findViewById(R.id.priceEditText);
        botaoCadastrar = findViewById(R.id.saveWineButton);
        botaoCancelar = findViewById(R.id.cancelWineButton);
        vinhoDAO = new VinhoDAO(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FormularioVinhoActivity.this);
        long idVinhoEdicao = preferences.getLong(SharedKeys.KEY_ID_VINHO_EDIT, -1);

        if (idVinhoEdicao != -1) {
            VinhoModel vinhoEdit = vinhoDAO.selectById(idVinhoEdicao);
            campoNome.setText(vinhoEdit.getNome());
            campoEstoque.setText(String.valueOf(vinhoEdit.getEstoque()));
            campoPreco.setText(String.format(Locale.US, "%.2f", vinhoEdit.getPreco()));
            campoSafra.setText(vinhoEdit.getSafra());
            campoTipo.setText(vinhoEdit.getTipo());
        }
        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarParaLista();
            }
        });
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = campoNome.getText().toString().trim();
                String tipo = campoTipo.getText().toString().trim();
                String estoque = campoEstoque.getText().toString().trim();
                String preco = campoPreco.getText().toString().trim();
                String safra = campoSafra.getText().toString().trim();
                long idUsuario = preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1);

                //Validações vinho
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
                //Validação estoque
                if (estoque.isEmpty()) {
                    mostrarAlerta("Erro", "Campo estoque não está preenchido", "Ok");
                    return;
                }
                if (!estoque.matches("\\d+")) {
                    mostrarAlerta("Erro", "O campo estoque só pode possuir números inteiros", "Ok");
                    return;
                }
                try {
                     int valorEstoque = Integer.parseInt(estoque);
                    if (valorEstoque < 0) {
                        mostrarAlerta("Erro", "O campo estoque deve ser maior ou igual a zero", "Ok");
                        return;
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Erro", "Valor inválido no campo estoque", "Ok");
                    return;
                }
                // Validação da safra
                if (safra.isEmpty()) {
                    mostrarAlerta("Erro", "Campo safra não está preenchido", "Ok");
                    return;
                }
                if (!safra.matches("\\d{4}")) {
                    mostrarAlerta("Erro", "O campo safra deve conter um ano válido com 4 números", "Ok");
                    return;
                }
                int anoSafra = Integer.parseInt(safra);
                int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
                if (anoSafra < 1900 || anoSafra > anoAtual) {
                    mostrarAlerta("Erro", "A safra deve ser um ano entre 1900 e o ano atual", "Ok");
                    return;
                }
                // Validação do preco
                if (preco.isEmpty()) {
                    mostrarAlerta("Erro", "Campo de preço não está preenchido", "Ok");
                    return;
                }
                if (!preco.matches("\\d+(\\.\\d{1,2})?|\\d+(,\\d{1,2})?")) {
                    mostrarAlerta("Erro", "O campo de preço deve conter um número válido, com até 2 casas decimais", "Ok");
                    return;
                }
                float valorPreco = Float.parseFloat(preco.replace(",", "."));
                if (valorPreco <= 0) {
                    mostrarAlerta("Erro", "O preço deve ser maior que zero", "Ok");
                    return;
                }
                // Validação do tipo
                if(tipo.isEmpty()){
                    mostrarAlerta("Erro", "O tipo do vinho não foi digitado", "Ok");
                    return;
                }
                if (tipo.length() > 20) {
                    mostrarAlerta("Erro", "O tipo de vinho é muito longo", "Ok");
                    return;
                }
                if (!tipo.matches("[a-zA-Z\\p{L}\\s]+")) {
                    mostrarAlerta("Erro", "O tipo de vinho não pode conter números ou caracteres especiais", "Ok");
                    return;
                }
                VinhoModel vinho = new VinhoModel();
                if (idVinhoEdicao != -1) {
                    vinho.setId(idVinhoEdicao);
                }
                vinho.setNome(nome);
                vinho.setSafra(safra);
                vinho.setEstoque(Integer.parseInt(estoque));
                vinho.setTipo(tipo);
                vinho.setPreco(Float.parseFloat(preco));
                vinho.setId_usuario(idUsuario);
                if (idVinhoEdicao != -1) {
                    vinhoDAO.update(vinho);
                    mostrarAlerta2("Sucesso", "Vinho editado com sucesso", "Ir para vinhos");
                } else {
                    vinhoDAO.insert(vinho);
                    mostrarAlerta2("Sucesso", "Vinho criado com sucesso", "Ir para vinhos");
                }
            }
        });
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
    private void redirecionarParaLista() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
