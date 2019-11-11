package com.dsss.ankush.liftpaymaintainance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import Model.Contact;

public class AddContact extends AppCompatActivity {
    ImageView imageView;
    EditText name,no,address;
    Contact contact;
    Button add;
    String ns,n,a;
    StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Conttacts");
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        contact=new Contact();
        contact.setName("x");
        contact.setNumber("x")
        ;
        contact.setAddress("x")
        ;
        contact.setAddress("x");
         progressDialog=new ProgressDialog(this);
         progressDialog.setMessage("Please wait...");
         progressDialog.setCancelable(false);
         progressDialog.setTitle("Adding");
        imageView=(ImageView)this.findViewById(R.id.imageshow);
        name=(EditText)this.findViewById(R.id.namec);
        no=(EditText)this.findViewById(R.id.phonec);
        address=(EditText)this.findViewById(R.id.addressc);
        add=(Button)findViewById(R.id.addc);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
              get();

                contact.setAddress(a);
                contact.setName(ns);
                contact.setNumber(n);
                FirebaseDatabase.getInstance().getReference().child("maintainance").child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(contact.getName()).setValue(contact).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Succesfully added..",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                });
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageView.setImageURI(data.getData());
        storageReference.child(new Date().toString()).putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                contact.setPicture(taskSnapshot.getDownloadUrl().toString());
            }
        });


    }
    void get()
    {
        ns=name.getText().toString();
        n=no.getText().toString();
        a=address.getText().toString();
    }
}
