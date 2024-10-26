package com.example.trabalhovinho.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.trabalhovinho.FormularioComprasActivity;
import com.example.trabalhovinho.R;
import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.database.dao.ClienteDAO;
import com.example.trabalhovinho.database.dao.VinhoDAO;
import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.CompraModel;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.ArrayList;
import java.util.Locale;

public class RelatoriosAdapter extends BaseAdapter {
    private Pair<ArrayList<CompraModel>, Float> listaVendas;
    private Activity activity;
    private ClienteDAO clienteDAO;
    private VinhoDAO vinhoDAO;

    public RelatoriosAdapter(Activity activity, Pair<ArrayList<CompraModel>, Float> listaVendas){
        this.activity = activity;
        this.listaVendas = listaVendas;
        this.clienteDAO = new ClienteDAO(activity);
        this.vinhoDAO = new VinhoDAO(activity);
    }

    @Override
    public int getCount() {
        return listaVendas !=null ? listaVendas.first.size() : 0;
    }

    public Object getItem(int indice){
        return listaVendas !=null ? listaVendas.first.get(indice) : null;
    }

    @Override
    public long getItemId(int position){
        return listaVendas !=null ? listaVendas.first.get(position).getId() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.activity_item_lista_compra_relatorio, parent, false);
        }
        CompraModel compra = listaVendas.first.get(position);
        ClienteModel cliente = clienteDAO.selectById(compra.getId_cliente());
        VinhoModel vinho = vinhoDAO.selectById(compra.getId_vinho());


        TextView nomeCliente = convertView.findViewById(R.id.nomeCliente);
        nomeCliente.setText(cliente.getNome());

        TextView nomeVinho = convertView.findViewById(R.id.nomeVinho);
        nomeVinho.setText(vinho.getNome());

        TextView precoTotal = convertView.findViewById(R.id.precoTotal);
        precoTotal.setText(String.format(Locale.US, "R$ %.2f", compra.getPreco_total()));

        TextView qtdVendidas = convertView.findViewById(R.id.qtdVendidos);
        qtdVendidas.setText(String.valueOf(compra.getQtd_vinhos()));

        TextView data = convertView.findViewById(R.id.dataCompra);
        data.setText(compra.getData());

        return convertView;
    }


}
