package com.dsss.ankush.liftpaymaintainance;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class listviewAdapter extends BaseAdapter {
    Context c;
    String p;
    int images[]={R.drawable.sa,R.drawable.sb,R.drawable.sd,R.drawable.se,R.drawable.sf,R.drawable.sg,R.drawable.sh,R.drawable.si,R.drawable.sj,R.drawable.sk,R.drawable.sc

    };

    ArrayList<String> data=new ArrayList<>();
    listviewAdapter(Context c,String path,ArrayList<String> x)
    {
        data=x;
       this.c=c;
       p=path;

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
                    inflate(R.layout.listitem, parent, false);
        }

        // get current item to be displayed
        ImageView i=(ImageView)convertView.findViewById(R.id.banner);
        i.setImageResource(images[new Random().nextInt(11)]);
        TextView t=convertView.findViewById(R.id.category);
        t.setText(Integer.toString(position+1)+". "+data.get(position));
        CardView cardView=convertView.findViewById(R.id.card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(c,SubCategory.class).putExtra("sub",data.get(position)));
            }
        });
        return convertView;
    }
}
