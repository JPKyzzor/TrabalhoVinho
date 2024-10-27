package com.example.trabalhovinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.adapter.RelatoriosAdapter;
import com.example.trabalhovinho.database.dao.CompraDAO;
import com.example.trabalhovinho.database.model.CompraModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RelatorioVendasMesActivity extends AppCompatActivity {
    private Spinner mesSelect, anoSelect;
    private ImageView setinha;
    private CompraDAO compraDAO;
    private ListView listViewVendas;
    private TextView valorTotal, quantidadeVinhos;
    int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
    String[] meses = {"Selecione um mês", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    int[] anos = {anoAtual-10, anoAtual-9, anoAtual-8, anoAtual-7, anoAtual-6, anoAtual-5, anoAtual-4, anoAtual-3, anoAtual-2, anoAtual-1, anoAtual};
    Boolean selecionouMes = false, selecionouAno = false;
    String mesSelecionado, anoSelecionado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios_de_vendas_mes);
        mesSelect = findViewById(R.id.selectMes);
        anoSelect = findViewById(R.id.selectAno);
        setinha = findViewById(R.id.setinha);
        listViewVendas = findViewById(R.id.vendasListView);
        valorTotal = findViewById(R.id.valorTotal);
        quantidadeVinhos = findViewById(R.id.quantidadeTotalComprada);

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
                mesSelecionado = String.format(Locale.ROOT, "%02d", position);

                selecionouMes = true;
                Log.d(mesSelecionado, "Item Selecionado: ");
                CarregaDados();
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
                anoSelecionado = (String) parent.getItemAtPosition(position);
                selecionouAno = true;
                Log.d(anoSelecionado, "Item Selecionado: ");
                CarregaDados();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
        private void CarregaDados() {
            if(selecionouAno && selecionouMes){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RelatorioVendasMesActivity.this);
                compraDAO = new CompraDAO(RelatorioVendasMesActivity.this);
                Pair<ArrayList<CompraModel>, Pair<Integer, Float>> vendas = compraDAO.selectAllByMes(preferences.getLong(SharedKeys.KEY_ID_USUARIO_LOGADO, -1), mesSelecionado, anoSelecionado);
                listViewVendas.setAdapter(new RelatoriosAdapter(RelatorioVendasMesActivity.this, vendas));
                valorTotal.setText(String.format(Locale.US, "R$ %.2f",vendas.second.second));
                quantidadeVinhos.setText(String.valueOf(vendas.second.first));

            }
        }


}
