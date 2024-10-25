package com.example.trabalhovinho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RelatorioVendasMesActivity extends AppCompatActivity {
    private Spinner mesSelect, anoSelect;
    private ImageView setinha;
    int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
    String[] meses = {"Selecione um mês", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    int[] anos = {anoAtual-10, anoAtual-9, anoAtual-8, anoAtual-7, anoAtual-6, anoAtual-5, anoAtual-4, anoAtual-3, anoAtual-2, anoAtual-1, anoAtual};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios_de_vendas_mes);
        mesSelect = findViewById(R.id.selectMes);
        anoSelect = findViewById(R.id.selectAno);
        setinha = findViewById(R.id.setinha);

        setinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(RelatorioVendasMesActivity.this, PaginaMenuActivity.class);
                startActivity(it);
            }
        });

        ArrayAdapter<String> adapterMeses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meses);
        adapterMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mesSelect.setAdapter(adapterMeses);
        mesSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    return;
                }
                String mesSelecionado = (String) parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<String> anosString = new ArrayList<>();
            anosString.add("Ano");
        for (int ano : anos) {
            anosString.add(String.valueOf(ano));
        }

        ArrayAdapter<String> adapterAnos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anosString);
        adapterAnos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        anoSelect.setAdapter(adapterAnos);
        anoSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    return;
                }
                String anoSelecionado = (String) parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}
