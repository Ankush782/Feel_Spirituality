package com.dsss.ankush.liftpaymaintainance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import Model.User;

public class register extends AppCompatActivity {
    EditText name,phone,email,password;
    Button register;
    String mVerificationId;
    String mResendToken;
     ProgressDialog progressDialog;
     User user;
     DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("maintainance").child("users");
        progressDialog=new ProgressDialog(register.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please verifying phone..");
        name=(EditText)findViewById(R.id.namer);
        email=(EditText)findViewById(R.id.emailr);
        phone=(EditText)findViewById(R.id.phoner);
        password=(EditText)findViewById(R.id.passwordr);
        register=(Button)findViewById(R.id.registerr);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected())
                {
                    if(validate())
                    {
                        progressDialog.show();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phone.getText().toString(),        // Phone number to verify
                                60,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                register.this,               // Activity (for callback binding)
                                mCallbacks);
                    }

                }
                else
                {

                }
            }
        });

    }
    boolean connected()
    {
      return true;
    }
    boolean validate()
    {
        if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(phone.getText().toString())) {



            Toast.makeText(getApplicationContext(),"PLease Enter Both Feild",Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return  true
                    ;
        }

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Succesfully Registered",Toast.LENGTH_LONG).show();
                    user=new User(name.getText().toString(),email.getText().toString(),phone.getText().toString(),"no");
                    user.setPassword(password.getText().toString());
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                    startActivity(new Intent(getApplicationContext(),Main.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
          //  mResendToken = forceResendingToken;
        }
    }
;}