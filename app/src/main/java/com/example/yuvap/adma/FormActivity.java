package com.example.yuvap.adma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FormActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    LinearLayout submit;
    Animation downtoup;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(FormActivity.this)
                .addOnConnectionFailedListener(FormActivity.this)
                .build();

        mGoogleApiClient.connect();
        // initializing views from xml
        final EditText firstName = findViewById(R.id.firstName);
        final EditText lastName = findViewById(R.id.lastName);
        Button registerButton = findViewById(R.id.register);



        submit=(LinearLayout) findViewById(R.id.submit);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        submit.setAnimation(downtoup);

        // Action to be performed by register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstname = firstName.getText().toString();
                final String lastname = lastName.getText().toString();


                Random rand = new Random();

                // Generate random integers in range 0 to 999
                int rannum = rand.nextInt(100000);

                String neww= Integer.toString(rannum);

                final String applicationno = firstname+lastname+neww;

                //Toast.makeText(getApplicationContext(), "vlaue is "+hello, Toast.LENGTH_LONG).show();
                // Print random integers
//                System.out.println("Random Integers: "+rannum);


                if (firstname.length()==0){
                    Toast.makeText(getApplicationContext(),"Please enter your first name",Toast.LENGTH_SHORT).show();
                }

                else if (lastname.length()==0){
                    Toast.makeText(getApplicationContext(),"Please enter your first name",Toast.LENGTH_SHORT).show();
                }

                else {
                    final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                    myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(
                                                new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.child("flag").getValue()!="true"){
                                                            myRef.child("Users")
                                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                    .child("flag").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    // Adding value in database. Map is used to create the object so as to enter multiple values into the database.
                                                                    Map<String,String> userDetails = new HashMap<>();
                                                                    userDetails.put("FirstName",firstname);
                                                                    userDetails.put("LastName",lastname);
                                                                    userDetails.put("userID",userId);
                                                                    userDetails.put("ApplicationNumber",applicationno);
                                                                    userDetails.put("flag","false");
                                                                    DatabaseReference myRefs = FirebaseDatabase
                                                                            .getInstance()
                                                                            .getReference()
                                                                            .child("Users")
                                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                    myRefs.setValue(userDetails);

                                                                    // Displaying message to the user regarding successful registration.
                                                                    Toast.makeText(getApplication(),"Done",Toast.LENGTH_SHORT).show();

                                                                    // Redirecting the user to Home Activity.
                                                                    Intent intent = new Intent(getApplicationContext(), mobile.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {}
                                                            });
                                                        }
                                                        else {
                                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                }
                                        );

                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}
