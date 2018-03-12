package com.example.yuvap.adma;

import android.annotation.SuppressLint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

public class bachelor extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private static final String TAG = bachelor.class.getSimpleName();
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;
    private DatabaseReference mDatabase;
    private String application;




    protected FirebaseUser mFirebaseUser;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "WrongViewCast"})


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bachelor);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(bachelor.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }


        final RandomAlphanumeric ran = new RandomAlphanumeric();
        final String tokenForAuthentication = ran.generateRandomAlphanumeric(50);

                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();


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



        mDatabase.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                application = dataSnapshot.child("ApplicationNumber").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }else {

            final DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
            myref.child("Bachelor").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
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
                        String userIdd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String bachelor= "bachelor_";
                        String app = bachelor+application;

                        WebView myWebView = findViewById(R.id.webview);
                        assert myWebView != null;
                        WebSettings webSettings = myWebView.getSettings();

                        myWebView.setWebChromeClient(new WebChromeClient());
                        myWebView.getSettings().setJavaScriptEnabled(true);
                        myWebView.getSettings().setDomStorageEnabled(true);
                        myWebView.getSettings().setAllowFileAccess(true);
                        myWebView.getSettings().setAllowContentAccess(true);
                        myWebView.getSettings().setLoadWithOverviewMode(true);

                        myWebView.addJavascriptInterface(new bachelor(), "injectedObject");







                        if (Build.VERSION.SDK_INT >= 21) {
                            webSettings.setMixedContentMode(0);
                            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                        } else if (Build.VERSION.SDK_INT >= 19) {
                            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                        } else if (Build.VERSION.SDK_INT < 19) {
                            myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }



                        myWebView.setWebChromeClient(new WebChromeClient() {



                            public boolean onShowFileChooser(
                                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                                    WebChromeClient.FileChooserParams fileChooserParams) {
                                if (mUMA != null) {
                                    mUMA.onReceiveValue(null);
                                }
                                mUMA = filePathCallback;
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(bachelor.this.getPackageManager()) != null) {
                                    File photoFile = null;
                                    try {
                                        photoFile = createImageFile();
                                        takePictureIntent.putExtra("PhotoPath", mCM);
                                    } catch (IOException ex) {
                                        Log.e(TAG, "Image file creation failed", ex);
                                    }
                                    if (photoFile != null) {
                                        mCM = "file:" + photoFile.getAbsolutePath();
                                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                    } else {
                                        takePictureIntent = null;
                                    }
                                }
                                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                                contentSelectionIntent.setType("*/*");
                                Intent[] intentArray;
                                if (takePictureIntent != null) {
                                    intentArray = new Intent[]{takePictureIntent};
                                } else {
                                    intentArray = new Intent[0];
                                }

                                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                                startActivityForResult(chooserIntent, FCR);
                                return true;
                            }
                        });






                            // Creating url parameters URL + tokenForAuthentication.
                        myWebView.setWebViewClient(new Callback());

                        String url = "https://admamater.000webhostapp.com/bachelor.html?param1=" + userId +"&param2=" +userIdd + "&param3=" +app ;
                        myWebView.loadUrl(url);

                        Toast.makeText(getApplicationContext(), app, Toast.LENGTH_LONG).show();

                        // Testing tokenForAuthentication.

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }



            });





        }


    }

    public class Callback extends WebViewClient {
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
        }
    }
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }






}
