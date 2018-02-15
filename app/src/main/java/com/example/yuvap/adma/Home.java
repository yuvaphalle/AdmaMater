package com.example.yuvap.adma;


import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.IgnoreExtraProperties;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;


public class Home extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ClipData.Item logout;
    private DrawerLayout myDrawer;
    private TextView textViewUserEmail;
        private ActionBarDrawerToggle myToggle;
    protected FirebaseUser mFirebaseUser;

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Button sign_out,fetch;
    private TextView userdata;
    DatabaseReference rootRef,demoRef;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.bar);
        this.setContentView(R.layout.activity_home);
        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myToggle = new ActionBarDrawerToggle(this, myDrawer, R.string.Open, R.string.Close);
        firebaseAuth = FirebaseAuth.getInstance();
        myDrawer.addDrawerListener(myToggle);
        fetch = (Button) findViewById(R.id.fetch);


        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        demoRef = rootRef.child("Users");









        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUserEmail.setText("Welcome " + user.getEmail());
        userdata =(TextView) findViewById(R.id.userd) ;











        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sign_out=(Button) findViewById(R.id.signout);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();

                Intent intent = new Intent(Home.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();




            }
        });



    }











    @IgnoreExtraProperties
    public class User {


        public String name;
        public String email;

        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public User() {
        }

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }


















}



