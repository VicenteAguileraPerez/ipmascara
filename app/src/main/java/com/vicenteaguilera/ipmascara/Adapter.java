package com.vicenteaguilera.ipmascara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

public class Adapter extends BaseAdapter {
    private List<RedModel> redes;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<RedModel> redes, Context context)
    {
        this.redes = redes;
        this.context=context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return redes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {

        view = layoutInflater.inflate(R.layout.item_red,viewGroup,false);
        TextView NetworkAddress = view.findViewById(R.id.textView_red);
        TextView Mask = view.findViewById(R.id.textView_mascara);
        ListView listView_networks = view.findViewById(R.id.listView_redes);

        NetworkAddress.setText("Red: "+redes.get(position).getRed());
        Mask.setText("Máscara: "+redes.get(position).getMascara());
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, redes.get(position).getRedes());
        listView_networks.setAdapter(arrayAdapter);

        return view;
    }
}
