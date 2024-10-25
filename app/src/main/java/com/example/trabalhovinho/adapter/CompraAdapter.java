package com.example.trabalhovinho.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

public class CompraAdapter extends BaseAdapter {
    private ArrayList<CompraModel> listaCompras;
    private Activity activity;
    private ClienteDAO clienteDAO;
    private VinhoDAO vinhoDAO;

    public CompraAdapter(Activity activity, ArrayList<CompraModel> listaVinhos){
        this.activity = activity;
        this.listaCompras = listaVinhos;
        this.clienteDAO = new ClienteDAO(activity);
        this.vinhoDAO = new VinhoDAO(activity);
    }

    @Override
    public int getCount() {
        return listaCompras !=null ? listaCompras.size() : 0;
    }

    public Object getItem(int indice){
        return listaCompras !=null ? listaCompras.get(indice) : null;
    }

    @Override
    public long getItemId(int position){
        return listaCompras !=null ? listaCompras.get(position).getId() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.activity_item_lista_compra, parent, false);
        }
        CompraModel compra = listaCompras.get(position);
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

        ImageButton botaoEdit = convertView.findViewById(R.id.botaoEditar);
        botaoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putLong(SharedKeys.KEY_ID_COMPRA_EDIT, compra.getId());
                edit.apply();
                Intent it = new Intent(activity, FormularioComprasActivity.class);
                activity.startActivity(it);
            }
        });

        return convertView;
    }


}
