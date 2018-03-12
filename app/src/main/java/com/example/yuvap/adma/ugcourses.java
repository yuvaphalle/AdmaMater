package com.example.yuvap.adma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

public class ugcourses extends home2
        implements NavigationView.OnNavigationItemSelectedListener{

    private CardView Apply, bca, bba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ugcourses);

        Apply=(CardView)findViewById(R.id.apply);
        bca=(CardView)findViewById(R.id.bca);
        bba=(CardView)findViewById(R.id.bba);

        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(ugcourses.this, bachelor.class);
                startActivity(intent);
            }
        });

        bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(ugcourses.this, bca.class);
                startActivity(intent);
            }
        });
        bba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(ugcourses.this, bba.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
