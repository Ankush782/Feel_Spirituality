package com.dsss.ankush.liftpaymaintainance;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import Model.Notice;

public class notice extends AppCompatActivity {
    EditText title,ms;
    String t,m;
    Button send;
    Notice notice;


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("maintainance").child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("notice");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        notice=new Notice();
        notice.setTitle("x");
        notice.setMessage("x");
        title=(EditText)findViewById(R.id.title);
        ms=(EditText)findViewById(R.id.message);
        send=(Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=title.getText().toString();
                m=ms.getText().toString();
                notice.setTitle(t);
                notice.setMessage(m);
                databaseReference.child(notice.getTitle()+new Date().toString()).setValue(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Succesfully Sent",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Something went wrong..",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}
