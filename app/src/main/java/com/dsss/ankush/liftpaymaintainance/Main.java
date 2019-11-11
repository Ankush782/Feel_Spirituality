package com.dsss.ankush.liftpaymaintainance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

import Model.User;

import static com.dsss.ankush.liftpaymaintainance.Hymn.c;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button notic,document,add,share,members,security;
    StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("documents").child(new Date().toString());
    ProgressDialog progressDialog;
    ArrayList<String> data=new ArrayList<>();
    static DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("maintainance").child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    static User user;

    MenuItem menuItem;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuItem=(MenuItem)findViewById(R.id.action_settings);
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



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ProgressDialog progressDialog=new ProgressDialog(Main.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Select Category");
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressDialog.show();

        listView=(ListView)findViewById(R.id.categories);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("categories");
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






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SavedHymns.class));

                Snackbar.make(view, "Downloaded Hymns", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.popup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("language", item.getTitle().toString());
        editor.commit();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            PopupMenu popup = new PopupMenu(Main.this, item.getActionView());
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    return true;
                }
            });

            popup.show();//showing popup menu
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.saved) {
            startActivity(new Intent(getApplicationContext(),SavedHymns.class));
        } else if (id == R.id.lastread) {
            Intent i=new Intent(getApplicationContext(),Hymn.class);
            i.putExtra("sub",user.getCategory());
            i.putExtra("hymn",user.getSubcategory());
            i.putExtra("h",user.getHymn());
            startActivity(i);


        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();

        } else if (id == R.id.history) {
            startActivity(new Intent(getApplicationContext(),Histor.class));

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id="+this.getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_send) {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","abc@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Your Suggestin");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
