package com.example.yuvap.adma;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import com.github.clans.fab.FloatingActionMenu;

public class About extends AppCompatActivity {
    com.github.clans.fab.FloatingActionButton direction, call;
    FloatingActionMenu fMenu;
    String number1;
    String destination="18.533403, 73.835083";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        direction = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.direction);
        call = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.call);
        fMenu = (com.github.clans.fab.FloatingActionMenu) findViewById(R.id.fMenu);

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?daddr=" + 18.533403 + "," + 73.835083 + " (" + "Where the college is located at" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8600673541"));
                if (ActivityCompat.checkSelfPermission(About.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

    }
}
