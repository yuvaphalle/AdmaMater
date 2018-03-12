package com.example.yuvap.adma;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.w3c.dom.Text;

public class faq extends AppCompatActivity {
    ExpandableRelativeLayout layoutq1, layoutq2, layoutq3, layoutq4, layoutq5, layoutq6;
    TextView a1, a2, a3, a4, a5, a6, a7, a8, a9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        a1=(TextView) findViewById(R.id.answer1);
        a2=(TextView) findViewById(R.id.answer2);
        a3=(TextView) findViewById(R.id.answer3);
        a4=(TextView) findViewById(R.id.answer4);
        a5=(TextView) findViewById(R.id.answer5);
        a6=(TextView) findViewById(R.id.answer6);

    }

    public void q1(View view){
        layoutq1 = (ExpandableRelativeLayout) findViewById(R.id.layoutQ1);
        layoutq1.toggle();
    }

    public void q2(View view){
        layoutq2 = (ExpandableRelativeLayout) findViewById(R.id.layoutQ2);
        layoutq2.toggle();
    }

    public void q3(View view){
        layoutq3 = (ExpandableRelativeLayout) findViewById(R.id.layoutQ3);
        layoutq3.toggle();
    }

    public void q4(View view){
        layoutq4 = (ExpandableRelativeLayout) findViewById(R.id.layoutQ4);
        layoutq4.toggle();
    }

    public void q5(View view){
        layoutq5 = (ExpandableRelativeLayout) findViewById(R.id.layoutQ5);
        layoutq5.toggle();
    }

    public void q6(View view){
        layoutq6 = (ExpandableRelativeLayout) findViewById(R.id.layoutQ6);
        layoutq6.toggle();
    }
}

