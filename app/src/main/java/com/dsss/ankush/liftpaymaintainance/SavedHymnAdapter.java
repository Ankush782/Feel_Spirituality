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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import Model.saved;


public class SavedHymnAdapter extends BaseAdapter{
    Context c;
    String language;
    ArrayList<saved> s;
    int images[]={R.drawable.sa,R.drawable.sb,R.drawable.sd,R.drawable.se,R.drawable.sf,R.drawable.sg,R.drawable.sh,R.drawable.si,R.drawable.sj,R.drawable.sk,R.drawable.sc

    };
    SavedHymnAdapter(Context c, ArrayList<saved> s)
    {
        this.c=c;
        this.s=s;
    }
    @Override
    public int getCount() {
        return s.size();
    }

    @Override
    public Object getItem(int position) {
        return s.get(position);
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

        // get current item to be displayed
        ImageView i=(ImageView)convertView.findViewById(R.id.banner);
        i.setImageResource(images[new Random().nextInt(11)]);
        TextView t=convertView.findViewById(R.id.category);
        t.setText(Integer.toString(position+1)+". "+s.get(position).getName());
        CardView cardView=convertView.findViewById(R.id.card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(c,SavedHymnShow.class);
                i.putExtra("meaning",s.get(position).getMeaning());
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
                language = settings.getString("language", "English");
                i.putExtra("language",language);
                i.putExtra("shlok",s.get(position).get(language));
                c.startActivity(i);



            }
        });
        return convertView;
    }
}
