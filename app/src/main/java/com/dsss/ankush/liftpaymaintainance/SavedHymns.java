package com.dsss.ankush.liftpaymaintainance;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import Model.saved;

public class SavedHymns extends AppCompatActivity {
   android.content.Context c;
   ArrayList<saved> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_hymns);
         Model.DB_MAnager db_mAnager=new Model.DB_MAnager(this);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Offline Hymns");
              db_mAnager.open();
        Cursor cursor=db_mAnager.fetch();
        while (cursor.moveToNext())
        {
            saved s=new saved();
            s.setId(cursor.getString(0));
            s.setName(cursor.getString(1));
            s.setEnglish(cursor.getString(4));
            s.setHindi(cursor.getString(3));
            s.setTamil(cursor.getString(5));
            s.setTelugu(cursor.getString(6));
            s.setMeaning(cursor.getString(2));
            data.add(s);
        }
        ListView listView=(ListView)findViewById(R.id.savedhymns);
        listView.setAdapter(new SavedHymnAdapter(this,data));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
