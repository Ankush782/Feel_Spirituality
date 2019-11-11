package com.dsss.ankush.liftpaymaintainance;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Model.History;

public class Histor extends AppCompatActivity {
    ArrayList<History> h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histor);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("History");
        actionBar.setDisplayHomeAsUpEnabled(true);
        h=new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("history").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {

                    History hhh=new History();
                   hhh=d.getValue(History.class);
                   h.add(hhh);

                }
                ListView listView=(ListView)findViewById(R.id.hh);
                listView.setAdapter(new HistoryDapter(getApplicationContext(),h));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


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
