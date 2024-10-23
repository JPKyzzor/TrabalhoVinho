package com.example.trabalhovinho.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabalhovinho.R;
import com.example.trabalhovinho.database.model.ClienteModel;

import java.util.ArrayList;

public class ClienteAdapter extends BaseAdapter {
    private ArrayList<ClienteModel> listaClientes;
    private Activity activity;

    public ClienteAdapter(Activity activity, ArrayList<ClienteModel> listaClientes){
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
            convertView = activity.getLayoutInflater().inflate(R.layout.activity_item_lista_cliente, parent, false);
        }

        ClienteModel cliente = listaClientes.get(position);
        TextView nome = convertView.findViewById(R.id.nomeCliente);
        nome.setText(cliente.getNome());

        TextView cpf_cnpj = convertView.findViewById(R.id.cpfCnpjCliente);
        cpf_cnpj.setText(cliente.getCpf_cnpj());

        TextView endereco = convertView.findViewById(R.id.enderecoCliente);
        endereco.setText(cliente.getCidade()+'/'+cliente.getEstado());

        TextView telefone = convertView.findViewById(R.id.telefoneCliente);
        telefone.setText(cliente.getTelefone());
        return convertView;
    }


}
