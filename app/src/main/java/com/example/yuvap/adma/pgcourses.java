package com.example.yuvap.adma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class pgcourses extends AppCompatActivity implements View.OnClickListener {
    private CardView apply, mca, mba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgcourses);

        apply = (CardView) findViewById(R.id.apply);
        mca = (CardView) findViewById(R.id.mca);
        mba = (CardView) findViewById(R.id.mba);

        apply.setOnClickListener(this);
        mca.setOnClickListener(this);
        mba.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.apply:
                i = new Intent(this, applypg.class);
                startActivity(i);
                break;
            case R.id.mca:
                i = new Intent(this, mca.class);
                startActivity(i);
                break;
            case R.id.mba:
                i = new Intent(this, mba.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

}
