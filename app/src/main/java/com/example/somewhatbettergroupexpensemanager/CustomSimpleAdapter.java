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

    ArrayList<String> arr;
    Context mContext;
    public CustomSimpleAdapter(Context context, ArrayList<String> arr) {
        super(context, R.layout.list_items, arr);
        this.arr = arr;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false);
        TextView text = view.findViewById(R.id.list_text);
        text.setText(arr.get(position));
        return view;
    }
}