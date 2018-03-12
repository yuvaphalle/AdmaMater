package com.example.yuvap.adma;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class fees extends AppCompatActivity {

    Spinner spinner;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        spinner = findViewById(R.id.spinnerTopics);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("feeSubmit").getValue()=="true"){
                            Intent i = new Intent(getApplicationContext(),home2.class);
                            startActivity(i);
                            finish();
                        }else {

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
                                mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
                                mChannel.enableLights(true);
                                mChannel.setLightColor(Color.RED);
                                mChannel.enableVibration(true);
                                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                mNotificationManager.createNotificationChannel(mChannel);
                            }
                            b1 = (Button)findViewById(R.id.buttonSubscribe);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final String topic = spinner.getSelectedItem().toString();
                                    if (topic.equals("Pay_Fees_Now")){
                                        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("feeSubmit").setValue("true")
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        FirebaseMessaging.getInstance().subscribeToTopic(topic);
                                                        Toast.makeText(getApplicationContext(), "Topic Subscribed", Toast.LENGTH_LONG).show();
                                                        Intent intent=new Intent(fees.this,home2.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                    FirebaseMessaging.getInstance().subscribeToTopic(topic);
                                    Toast.makeText(getApplicationContext(), "Topic Subscribed", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(fees.this,home2.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
}