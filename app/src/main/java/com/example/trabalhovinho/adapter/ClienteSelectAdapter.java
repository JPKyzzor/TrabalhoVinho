package com.example.trabalhovinho.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabalhovinho.FormularioClienteActivity;
import com.example.trabalhovinho.PaginaListaClientesActivity;
import com.example.trabalhovinho.PaginaLoginActivity;
import com.example.trabalhovinho.R;
import com.example.trabalhovinho.Shared.SharedKeys;
import com.example.trabalhovinho.database.model.ClienteModel;

import java.util.ArrayList;

public class ClienteSelectAdapter extends BaseAdapter {
    private ArrayList<ClienteModel> listaClientes;
    private Activity activity;

    public ClienteSelectAdapter(Activity activity, ArrayList<ClienteModel> listaClientes){
        this.activity = activity;
        this.listaClientes = listaClientes;
    }

    @Override
    public int getCount() {
        return listaClientes!=null ? listaClientes.size() : 0;
    }

    public Object getItem(int indice){
        return listaClientes!=null ? listaClientes.get(indice) : null;
    }

    @Override
    public long getItemId(int position){
        return listaClientes!=null ? listaClientes.get(position).getId() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.activity_item_select_cliente, parent, false);
        }
        ClienteModel cliente = listaClientes.get(position);
        TextView nome = convertView.findViewById(R.id.nomeCliente);
        nome.setText(cliente.getNome());

        return convertView;
    }


}
