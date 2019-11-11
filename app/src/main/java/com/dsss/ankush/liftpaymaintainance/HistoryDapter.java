package com.dsss.ankush.liftpaymaintainance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import Model.History;

public class HistoryDapter extends BaseAdapter {
    Context c;
    ArrayList<History> h;
    int images[]={R.drawable.sa,R.drawable.sb,R.drawable.sd,R.drawable.se,R.drawable.sf,R.drawable.sg,R.drawable.sh,R.drawable.si,R.drawable.sj,R.drawable.sk,R.drawable.sc

    };

    HistoryDapter(Context c, ArrayList<History> h)
    {
        this.c=c;
        this.h=h;
        Toast.makeText(c,  Integer.toString(h.size()),Toast.LENGTH_LONG).show();

    }

    @Override
    public int getCount() {
        return h.size();

    }

    @Override
    public Object getItem(int position) {
        return h.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).
                    inflate(R.layout.listitem, parent, false);
        }
        int images[]={R.drawable.sa,R.drawable.sb,R.drawable.sd,R.drawable.se,R.drawable.sf,R.drawable.sg,R.drawable.sh,R.drawable.si,R.drawable.sj,R.drawable.sk,R.drawable.sc

        };
        ImageView i=(ImageView)convertView.findViewById(R.id.banner);
        i.setImageResource(images[new Random().nextInt(11)]);
        TextView t=convertView.findViewById(R.id.category);
        t.setText(Integer.toString(position+1)+". "+h.get(position).getHymn());
        CardView cardView=convertView.findViewById(R.id.card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(c,Hymn.class);
                i.putExtra("sub",h.get(position).getCat());
                i.putExtra("hymn",h.get(position).getSub());
                i.putExtra("h",h.get(position).getHymn());
                c.startActivity(i);
            }
        });
        return convertView;
    }
}
