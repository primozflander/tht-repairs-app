package com.example.ninica;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomListAdapter extends BaseAdapter {
    private ArrayList<ListItem> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<ListItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View v, ViewGroup vg) {
        final ViewError error;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row, null);
            error = new ViewError();
            error.errorName = (TextView) v.findViewById(R.id.errorName);
            error.errorCount = (TextView) v.findViewById(R.id.errorCount);
            v.setTag(error);
        } else {
            error = (ViewError) v.getTag();
        }

        Button removeBtn = (Button)v.findViewById(R.id.remove_button);
        Button addBtn = (Button)v.findViewById(R.id.add_button);

        removeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String current_count = error.errorCount.getText().toString();
                if (!current_count.equals("0")){
                    String new_count = String.valueOf(Integer.valueOf(current_count) - 1);
                    error.errorCount.setText(new_count);
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String current_count = error.errorCount.getText().toString();
                String new_count = String.valueOf(Integer.valueOf(current_count) + 1);
                listData.get(position).setErrorCount(new_count);
                error.errorCount.setText(new_count);
            }
        });

        error.errorName.setText(listData.get(position).getErrorName());
        error.errorCount.setText(listData.get(position).getErrorCount());
        return v;
    }

    static class ViewError {
        TextView errorName;
        TextView errorCount;
    }
}