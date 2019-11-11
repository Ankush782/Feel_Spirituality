package com.dsss.ankush.liftpaymaintainance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HymnAdapter extends BaseAdapter {
    Context c;
    String p, h;
    int images[]={R.drawable.sa,R.drawable.sb,R.drawable.sd,R.drawable.se,R.drawable.sf,R.drawable.sg,R.drawable.sh,R.drawable.si,R.drawable.sj,R.drawable.sk,R.drawable.sc

    };


    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> val = new ArrayList<>();

    HymnAdapter(Context c, String path, String h, ArrayList<String> x, ArrayList<String> hymn) {
        data = x;
        this.c = c;
        p = path;
        this.h = h;
        this.val = hymn;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).
                    inflate(R.layout.hymnline, parent, false);
        }


        // get current item to be displayed
        TextView t = convertView.findViewById(R.id.language);
        TextView d = convertView.findViewById(R.id.data);
        t.setText(Integer.toString(position+1) + ". " + data.get(position));
        d.setText(val.get(position));
        return convertView;


    }
}

