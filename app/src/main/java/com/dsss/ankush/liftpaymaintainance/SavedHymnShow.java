package com.dsss.ankush.liftpaymaintainance;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class SavedHymnShow extends AppCompatActivity {
     String data,meaning;
     TextView l,d,m,md;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_hymn_show);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Hymn");
        data=getIntent().getStringExtra("shlok");
        meaning=getIntent().getStringExtra("meaning");
        l=(TextView)findViewById(R.id.language);
        d=(TextView)findViewById(R.id.shlok);
        m=(TextView)findViewById(R.id.meaning);
        md=(TextView)findViewById(R.id.meaningdata);
        l.setText(getIntent().getStringExtra("language"));
        d.setText(data);
        m.setText("Meaning");
        md.setText(meaning);

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
