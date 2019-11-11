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

public class SubCategory extends AppCompatActivity {
    ArrayList<String> data=new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Select SubCategory");

        final ProgressDialog progressDialog=new ProgressDialog(SubCategory.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        listView=(ListView)findViewById(R.id.subcategories);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("categories").child(getIntent().getStringExtra("sub"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    data.add(d.getKey());
                    //listView.setAdapter(new listviewAdapter(getApplicationContext(),"",data));
                }
                progressDialog.dismiss();
                listView.setAdapter(new Subcategoriesadapter(getApplicationContext(),getIntent().getStringExtra("sub"),data));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
       // listView.setAdapter(new listviewAdapter(getApplicationContext(),"",data));

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
