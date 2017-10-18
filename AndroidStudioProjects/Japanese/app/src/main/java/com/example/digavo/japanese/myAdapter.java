package com.example.digavo.japanese;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class myAdapter extends ArrayAdapter {
    Context context;
    String[] wordsEn;
    String[] wordsJp;
    int[] visible; // 0- En & Jp visible, 1- En visible, 2- Jp visible
    public myAdapter(Context context, int layout, String[] items, String[] items2, int[] vis) {
        super(context, R.layout.words_layout, items);
        this.context = context;
        this.wordsEn = items;
        this.wordsJp = items2;
        this.visible = vis;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.words_layout, null);
        TextView label1 = (TextView) row.findViewById(R.id.tvEn);
        TextView label2 = (TextView) row.findViewById(R.id.tvJp);

        label1.setText(wordsEn[position]);
        label2.setText(wordsJp[position]);
        if (visible[position]==0) {
            label1.setVisibility(View.VISIBLE);
            label2.setVisibility(View.VISIBLE);
        }
        else if (visible[position]==1){
            label1.setVisibility(View.VISIBLE);
            label2.setVisibility(View.INVISIBLE);
        }
        else if (visible[position]==2){
            label1.setVisibility(View.INVISIBLE);
            label2.setVisibility(View.VISIBLE);
        }

        return (row);
    }
}
