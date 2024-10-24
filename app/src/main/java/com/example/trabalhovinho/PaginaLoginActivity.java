package com.example.trabalhovinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_login);
        usuarioDAO = new UsuarioDAO(this);
        usuarios = usuarioDAO.selectAll();
        if(usuarios==null){
            usuarios = new ArrayList<>();
        }
        campoEmail = findViewById(R.id.emailEditText);
        campoSenha = findViewById(R.id.passwordEditText);
        checkLembrar = findViewById(R.id.checkLembrar);
        botaoEntrar = findViewById(R.id.loginButton);
        botaoCadastrar = findViewById(R.id.createAccountButton);

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
                if(checkLembrar.isChecked()){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaLoginActivity.this);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(SharedKeys.KEY_EMAIL_LEMBRAR,campoEmail.getText().toString());
                    edit.putString(SharedKeys.KEY_EMAIL_LEMBRAR, campoSenha.getText().toString());
                    edit.apply();
                }
                Intent it = new Intent(PaginaLoginActivity.this, PaginaMenuActivity.class);
                startActivity(it);
            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaginaLoginActivity.this);
        campoEmail.setText(preferences.getString(SharedKeys.KEY_EMAIL_LEMBRAR,""));
        campoSenha.setText(preferences.getString(SharedKeys.KEY_SENHA_LEMBRAR,""));
    }
}
