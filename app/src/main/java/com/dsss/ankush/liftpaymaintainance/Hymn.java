package com.dsss.ankush.liftpaymaintainance;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import Model.DB_MAnager;
import Model.History;
import Model.User;

public class Hymn extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<>();
    ListView listView;
    static Context c;
   static String cat,sub,h;
    String now;
   static User user;
   MenuItem last;
   ArrayList<String> hymnsss;
   static DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("maintainance").child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
   String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         language = settings.getString("language", "English");

        hymnsss=new ArrayList<>();
        now="x";
        last=(MenuItem)findViewById(R.id.last) ;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra("h"));
        c = Hymn.this;
        cat=getIntent().getStringExtra("sub");
        sub=getIntent().getStringExtra("hymn");
        h=getIntent().getStringExtra("h");
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(c,databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });




        final ProgressDialog progressDialog = new ProgressDialog(Hymn.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        listView = (ListView) findViewById(R.id.hymn);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("categories").child(getIntent().getStringExtra("sub")).child(getIntent().getStringExtra("hymn")).child(getIntent().getStringExtra("h"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    data.add(d.getKey());
                    hymnsss.add(d.getValue(String.class));
                    now=d.getKey();
                    //listView.setAdapter(new listviewAdapter(getApplicationContext(),"",data));
                }
                progressDialog.dismiss();
                ArrayList<String> s=new ArrayList<String>();
                s.add(language);
                s.add("Meaning");
                ArrayList<String> v=new ArrayList<>();
                v.add(hymnsss.get(data.indexOf(language)));
                v.add(hymnsss.get(data.indexOf("Meaning")));


                listView.setAdapter(new HymnAdapter(getApplicationContext(), getIntent().getStringExtra("sub"), getIntent().getStringExtra("hymn"), s,v));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menux, menu);
        return true;
    }

    public void saveHymn() {
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        builder.setTitle("Alert...");

        builder.setMessage("Do you want it offline");
        builder.setIcon(R.drawable.spiritual);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              DB_MAnager db_mAnager=new DB_MAnager(c);
              db_mAnager.open();
              db_mAnager.insert(h,hymnsss.get(2),hymnsss.get(0),hymnsss.get(1),hymnsss.get(3),hymnsss.get(4));
              db_mAnager.close();
                Toast.makeText(c,"Downloading",Toast.LENGTH_LONG).show();

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.last) {
            saveState();
            //finish();

        }

        if(item.getItemId()==R.id.download)
        {
   saveHymn();
        }
        return super.onOptionsItemSelected(item);
    }

    static String m_Text;

    static void saveState() {
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        builder.setTitle("Check...");

        builder.setMessage("Completed this Hymn");
        builder.setIcon(R.drawable.spiritual);
       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               user.setCategory(cat);
               user.setSubcategory(sub);
               user.setHymn(h);
               dbr.setValue(user);
               History history=new History();
               history.setCat(cat);
               history.setSub(sub);
               history.setHymn(h);
               FirebaseDatabase.getInstance().getReference().child("history").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(new Date().toString()).setValue(history);

               Toast.makeText(c,"Succesfully saved",Toast.LENGTH_LONG).show();



           }
       }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       });
       builder.show();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
         //   last.setChecked(true);
            //saveState();
            finish();
            return true; //I have tried here true also
        }
        return super.onKeyDown(keyCode, event);
    }
      void closeAcivity()
    {

    }
}