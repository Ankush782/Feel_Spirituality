package com.dsss.ankush.liftpaymaintainance;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Hymns extends AppCompatActivity {
    ArrayList<String> data=new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymns);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Select Hymn");

        final ProgressDialog progressDialog=new ProgressDialog(Hymns.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        listView=(ListView)findViewById(R.id.hymns);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("categories").child(getIntent().getStringExtra("sub")).child(getIntent().getStringExtra("hymn"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    data.add(d.getKey());
                    //listView.setAdapter(new listviewAdapter(getApplicationContext(),"",data));
                }
                progressDialog.dismiss();
                listView.setAdapter(new HymnsAdapter(getApplicationContext(),getIntent().getStringExtra("sub"),getIntent().getStringExtra("hymn"),data));

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
