package com.example.somewhatbettergroupexpensemanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomSimpleAdapter extends ArrayAdapter {

    ArrayList<Transaction> arr;
    Context mContext;
    public CustomSimpleAdapter(Context context, ArrayList<Transaction> arr) {
        super(context, R.layout.list_items, arr);
        this.arr = arr;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false);
        TextView id = view.findViewById(R.id.list_id);
        TextView date = view.findViewById(R.id.list_date);
        TextView description = view.findViewById(R.id.list_description);
        TextView amount = view.findViewById(R.id.list_amount);
        id.setText(arr.get(position).id+"");
        date.setText(arr.get(position).date+"");
        description.setText(arr.get(position).description+"");
        amount.setText(arr.get(position).amount+"");
        return view;
    }
}