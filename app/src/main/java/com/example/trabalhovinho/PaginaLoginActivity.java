package com.example.trabalhovinho;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.PasswordUtils;
import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.database.dao.UsuarioDAO;
import com.example.trabalhovinho.database.model.UsuarioModel;

import java.util.ArrayList;

public class PaginaLoginActivity extends AppCompatActivity {
    private EditText campoEmail, campoSenha;
    private Button botaoEntrar, botaoCadastrar;
    private CheckBox checkLembrar;
    private ArrayList<UsuarioModel> usuarios;
    private UsuarioDAO usuarioDAO;
    private TextView botaoEsqueceuSenha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_login);
        usuarioDAO = new UsuarioDAO(this);
        campoEmail = findViewById(R.id.emailEditText);
        campoSenha = findViewById(R.id.passwordEditText);
        checkLembrar = findViewById(R.id.checkLembrar);
        botaoEntrar = findViewById(R.id.loginButton);
        botaoCadastrar = findViewById(R.id.createAccountButton);
        botaoEsqueceuSenha = findViewById(R.id.forgotPasswordTextView);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaLoginActivity.this, FormularioUsuarioActivity.class);
                startActivity(it);
            }
        });

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                UsuarioModel usuario = usuarioDAO.getUserByEmail(email);
                if (usuario == null) {
                    mostrarAlerta("Erro", "Usuário ou senha inválidos", "OK");
                    return;
                }
                byte[] storedSalt = Base64.decode(usuario.getSalt(), Base64.DEFAULT);
                if(PasswordUtils.verifyPassword(senha,usuario.getSenha(),storedSalt)){
                    fazerLogin(usuario.getId());
                }else{
                    mostrarAlerta("Erro", "Usuário ou senha inválidos", "OK");
                }
            }
        });

        botaoEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PaginaLoginActivity.this, PaginaEsqueceuSenhaActivity.class);
                startActivity(it);
            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaLoginActivity.this);
        campoEmail.setText(preferences.getString(SharedKeys.KEY_EMAIL_LEMBRAR, ""));
        campoSenha.setText(preferences.getString(SharedKeys.KEY_SENHA_LEMBRAR, ""));
    }

    private void fazerLogin(long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaLoginActivity.this);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(SharedKeys.KEY_ID_USUARIO_LOGADO, id);
        if (checkLembrar.isChecked()) {
            edit.putString(SharedKeys.KEY_EMAIL_LEMBRAR, campoEmail.getText().toString());
            edit.putString(SharedKeys.KEY_SENHA_LEMBRAR, campoSenha.getText().toString());
        }else{
            edit.putString(SharedKeys.KEY_EMAIL_LEMBRAR, null);
            edit.putString(SharedKeys.KEY_SENHA_LEMBRAR, null);
        }
        edit.apply();
        Intent it = new Intent(PaginaLoginActivity.this, PaginaMenuActivity.class);
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
}
