package com.example.trabalhovinho;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.PasswordUtils;
import com.example.trabalhovinho.Shared.UsefulFunctions;
import com.example.trabalhovinho.database.dao.UsuarioDAO;
import com.example.trabalhovinho.database.model.UsuarioModel;

public class FormularioUsuarioActivity extends AppCompatActivity {

    private EditText nomeEditText, emailEditText, senhaEditText, confirmarSenhaEditText;
    private Button botaoCadastrar, botaoRetornar;
    private CheckBox lembrarLogin;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuario);
        usuarioDAO = new UsuarioDAO(this);
        nomeEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        senhaEditText = findViewById(R.id.passwordEditText);
        confirmarSenhaEditText = findViewById(R.id.passwordConfirmEditText);
        botaoCadastrar = findViewById(R.id.registerButton);
        botaoRetornar = findViewById(R.id.backToLoginButton);

        botaoRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarParaLogin();
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();
                String senha2 = confirmarSenhaEditText.getText().toString().trim();
                if(nome.isEmpty()){
                    mostrarAlerta("Campo nome não está preenchido");
                    return;
                }
                if(email.isEmpty()){
                    mostrarAlerta("Campo email não está preenchido");
                    return;
                }
                if(senha.isEmpty()){
                    mostrarAlerta("Campo senha não está preenchido");
                    return;
                }
                if (!nome.matches("[a-zA-Z\\p{L}\\s]+")) {
                    mostrarAlerta("O nome não pode conter números ou caracteres especiais");
                    return;
                }
                int maxNameLength = 50;
                int maxEmailLength = 100;
                if (nome.length() > maxNameLength) {
                    mostrarAlerta("O nome é muito longo");
                    return;
                }
                if (email.length() > maxEmailLength) {
                    mostrarAlerta("O e-mail é muito longo");
                    return;
                }
                if (senha2.isEmpty()) {
                    mostrarAlerta("Campo de confirmação de senha não está preenchido");
                    return;
                }
                if(usuarioDAO.checkIfEmailExists(email)){
                    mostrarAlerta("Email já está em uso");
                    return;
                }
                if(!senha.equals(senha2)){
                    mostrarAlerta("Senhas não coincidem");
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mostrarAlerta("E-mail inválido");
                    return;
                }
                if (senha.length() < 8 || !senha.matches(".*[A-Z].*") || !senha.matches(".*[a-z].*") || !senha.matches(".*\\d.*") || !senha.matches(".*[@#^$%!&*].*")) {
                    mostrarAlerta("A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.");
                    return;
                }
                byte[] salt = PasswordUtils.generateSalt();
                String saltString = Base64.encodeToString(salt, Base64.DEFAULT);
                String senhaCriptoGrafada = PasswordUtils.generateHash(senha,salt);
                UsuarioModel usuario = new UsuarioModel();
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setSenha(senhaCriptoGrafada);
                usuario.setSalt(saltString);
                usuarioDAO.insert(usuario);
                mostrarAlerta2("Usuário criado com sucesso");
            }
        });
    }

    private void redirecionarParaLogin(){
        UsefulFunctions.finalizaIntent(this);
    }

    private void mostrarAlerta(String mensagem) {
        new AlertDialog.Builder(this)
        .setTitle("Erro")
        .setMessage(mensagem)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .show();
    }

    private void mostrarAlerta2(String mensagem) {
        new AlertDialog.Builder(this)
                .setTitle("Sucesso")
                .setMessage(mensagem)
                .setPositiveButton("Ir para login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        redirecionarParaLogin();
                    }
                })
                .show();
    }
}
