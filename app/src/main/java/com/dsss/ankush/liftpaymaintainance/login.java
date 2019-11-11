package com.dsss.ankush.liftpaymaintainance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email,password;
    Button login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ProgressDialog progressDialog=new ProgressDialog(login.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),Main.class));

        }
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected())
                {
                    if(validate())
                    {
                        progressDialog.show();
                        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getApplicationContext(),"Login Succesfull",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),Main.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Login Failed ,Try Again",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();


                            }
                        });
                    }
                    else
                    {

                    }

                }
                else
                {

                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });


    }
    boolean connected()
    {
        return true;
    }
    boolean validate()
    {
        if(TextUtils.isEmpty(email.getText().toString())||TextUtils.isEmpty(password.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"PLease Enter Both Feild",Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return  true
                    ;
        }
    }
}
