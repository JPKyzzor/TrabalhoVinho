package com.example.trabalhovinho.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trabalhovinho.R;
import com.example.trabalhovinho.database.model.ClienteModel;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.ArrayList;

public class VinhoAdapter extends BaseAdapter {
    private ArrayList<VinhoModel> listaVinhos;
    private Activity activity;

    public VinhoAdapter(Activity activity, ArrayList<VinhoModel> listaVinhos){
        this.activity = activity;
        this.listaVinhos = listaVinhos;
    }

    @Override
    public int getCount() {
        return listaVinhos !=null ? listaVinhos.size() : 0;
    }

    public Object getItem(int indice){
        return listaVinhos !=null ? listaVinhos.get(indice) : null;
    }

    @Override
    public long getItemId(int position){
        return listaVinhos !=null ? listaVinhos.get(position).getId() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.activity_item_lista_vinho, parent, false);
        }

        VinhoModel vinho = listaVinhos.get(position);

        TextView nome = convertView.findViewById(R.id.nomeVinho);
        nome.setText(vinho.getNome());

        TextView precoVinho = convertView.findViewById(R.id.precoVinho);
        precoVinho.setText(String.format("R$ %.2f", vinho.getPreco()));

        TextView safraVinho = convertView.findViewById(R.id.safraVinho);
        safraVinho.setText(vinho.getSafra());

        TextView tipoVinho = convertView.findViewById(R.id.tipoVinho);
        tipoVinho.setText(vinho.getTipo());

        TextView estoque = convertView.findViewById(R.id.estoqueVinho);
        estoque.setText(String.valueOf(vinho.getEstoque()));

        return convertView;
    }


}
