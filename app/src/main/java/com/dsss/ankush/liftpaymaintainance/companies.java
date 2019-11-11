package com.dsss.ankush.liftpaymaintainance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class companies extends AppCompatActivity {
    Button notic,document,add,share,members,security;
    StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("documents").child(new Date().toString());
    ProgressDialog progressDialog;
    ArrayList<String> data=new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
        final ProgressDialog progressDialog=new ProgressDialog(companies.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Select Category");
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressDialog.show();

         listView=(ListView)findViewById(R.id.categories);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("categories");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    data.add(d.getKey());
                    //listView.setAdapter(new listviewAdapter(getApplicationContext(),"",data));
                }
                progressDialog.dismiss();
                listView.setAdapter(new listviewAdapter(getApplicationContext(),"",data));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



           }


    }

