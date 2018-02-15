package com.example.yuvap.adma;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.util.Random;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.firebase.database.ValueEventListener;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.webkit.JavascriptInterface;

import com.google.firebase.database.ValueEventListener;

public class google extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    protected FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);


        final RandomAlphanumeric ran = new RandomAlphanumeric();
        final String tokenForAuthentication = ran.generateRandomAlphanumeric(50);

                firebaseAuth = FirebaseAuth.getInstance();

//        mFirebaseUser.getIdToken(true)
//                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                    public void onComplete(@NonNull Task<GetTokenResult> task) {
//                        if (task.isSuccessful()) {
//                            String idToken = task.getResult().getToken();
//                            // Send token to your backend via HTTPS
//                            // ...
//                        } else {
//                            // Handle error -> task.getException();
//                        }
//                    }
//                });









        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }else {

            final DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
            myref.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("data").exists()){
                        Toast.makeText(getApplication(),"You have already submitted the form",Toast.LENGTH_LONG).show();
                        Intent homeActivity = new Intent(getApplicationContext(),Home.class);
                        startActivity(homeActivity);
                        finish();
                    }    else {
                        @SuppressLint("JavascriptInterface")

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        WebView myWebView = findViewById(R.id.webview);
                        myWebView.setWebChromeClient(new WebChromeClient());
                        myWebView.getSettings().setJavaScriptEnabled(true);
                        myWebView.getSettings().setDomStorageEnabled(true);
                        myWebView.addJavascriptInterface(new google(), "injectedObject");






                        // Creating url parameters URL + tokenForAuthentication.
                        String url = "https://admamater.000webhostapp.com/?param1=" + userId +"&param2=" +tokenForAuthentication ;
                        myWebView.loadUrl(url);

                        // Testing tokenForAuthentication.
                        Toast.makeText(getApplicationContext(),""+tokenForAuthentication,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






        }

    }





}
