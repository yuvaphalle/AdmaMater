package com.example.yuvap.adma;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;

import com.bumptech.glide.Glide;
import com.example.yuvap.adma.models.Users;
import com.example.yuvap.adma.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home2 extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView mDisplayImageView;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private FirebaseAuth firebaseAuth;
    protected FirebaseUser mFirebaseUser;
    private TextView appid;
    private DatabaseReference mDatabase;
    private CardView ug, pg, upi, faq, about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationManager mNotificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = new NotificationChannel(com.example.yuvap.adma.Constants.CHANNEL_ID, com.example.yuvap.adma.Constants.CHANNEL_NAME, importance);
//            mChannel.setDescription(com.example.yuvap.adma.Constants.CHANNEL_DESCRIPTION);
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            mChannel.enableVibration(true);
//            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            mNotificationManager.createNotificationChannel(mChannel);
//        }
//
//        /*
//        * Displaying a notification locally
//        */
//        MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");
//





        ug = (CardView) findViewById(R.id.ugcourses);
        pg = (CardView) findViewById(R.id.pgcourses);
//        upi = (CardView) findViewById(R.id.update);
        faq = (CardView) findViewById(R.id.faq);
        about = (CardView) findViewById(R.id.about);


        ug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home2.this, ugcourses.class);
                startActivity(intent);
            }
        });

        pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home2.this, pgcourses.class);
                startActivity(intent);
            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home2.this, About.class);
                startActivity(intent);
            }
        });


        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home2.this, faq1.class);
                startActivity(intent);
            }
        });


//        ug.setOnClickListener(this);
//        pg.setOnClickListener(this);
//        faq.setOnClickListener(this);
//        about.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);

        mEmailTextView=(TextView) navHeaderView.findViewById(R.id.gemail);
        mDisplayImageView = (ImageView) navHeaderView.findViewById(R.id.photo);
        mNameTextView = (TextView) navHeaderView.findViewById(R.id.name);
        appid= (TextView) navHeaderView.findViewById(R.id.applicationid);


        FirebaseUser user = firebaseAuth.getCurrentUser();
        mEmailTextView.setText(user.getEmail());
        //mDisplayImageView.setImageURI(user.getPhotoUrl());
        mNameTextView.setText(user.getDisplayName());
        Glide.with(home2.this).load(user.getPhotoUrl()).override(200,200).fitCenter().into(mDisplayImageView);

        mDatabase.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appplication = dataSnapshot.child("ApplicationNumber").getValue().toString();
                appid.setText(appplication);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //corner

        if (id == R.id.Logout) {
            firebaseAuth.getInstance().signOut();

            Intent intent = new Intent(home2.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.aboutt) {

            startActivity(new Intent(this, About.class));


      } else if (id == R.id.faqq) {

             startActivity(new Intent(this, faq1.class));


      } else if (id == R.id.ugcc) {

             startActivity(new Intent(this, ugcourses.class));

        } else if (id == R.id.pgcc) {

             startActivity(new Intent(this, pgcourses.class));

         }
       else if (id == R.id.log) {

        firebaseAuth.getInstance().signOut();

        Intent intent = new Intent(home2.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        }


             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
