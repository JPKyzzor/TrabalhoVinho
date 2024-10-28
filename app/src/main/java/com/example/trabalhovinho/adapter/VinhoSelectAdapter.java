package com.example.trabalhovinho.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.trabalhovinho.R;
import com.example.trabalhovinho.database.model.VinhoModel;

import java.util.ArrayList;

public class VinhoSelectAdapter extends BaseAdapter {
    private ArrayList<VinhoModel> listaVinhos;
    private Activity activity;

    public VinhoSelectAdapter(Activity activity, ArrayList<VinhoModel> listaVinhos){
        this.activity = activity;
        this.listaVinhos = listaVinhos;
    }

    @Override
    public int getCount() {
        return listaVinhos!=null ? listaVinhos.size() : 0;
    }

    public Object getItem(int indice){
        return listaVinhos!=null ? listaVinhos.get(indice) : null;
    }

    @Override
    public long getItemId(int position){
        return listaVinhos!=null ? listaVinhos.get(position).getId() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.activity_item_select_vinho, parent, false);
        }
        VinhoModel vinho = listaVinhos.get(position);
        TextView nome = convertView.findViewById(R.id.nomeVinho);
        String texto = vinho.getNome()+'/'+vinho.getTipo()+'/'+vinho.getSafra();
        nome.setText(texto);

        return convertView;
    }


}
