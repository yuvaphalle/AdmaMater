package com.example.yuvap.adma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class mobile extends AppCompatActivity {
    EditText phone,vcode,codeTextField;
    TextView msg;
    Button getcode,verify;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        phone = (EditText) findViewById(R.id.phone);
        vcode = (EditText) findViewById(R.id.code);
        getcode=(Button)findViewById(R.id.getcode);
        verify=(Button)findViewById(R.id.verify);
        codeTextField = findViewById(R.id.code);
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                // code after the OTP is verified
                Toast.makeText(mobile.this,"Phone number Verified",Toast.LENGTH_LONG).show();

                // setting flags true for successful verification.

                final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("phoneNumber").setValue(phone.getText().toString());
                myRef.child("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("flag").setValue("true");

                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();

                // Redirecting the user to next application page.
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                vcode.setVisibility(View.GONE);
                verify.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(mobile.this,"Login Failed",Toast.LENGTH_LONG).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                // validating phone number
                if(phone.getText().toString().isEmpty()&& phone.getText().toString().length()!=10){
                    Toast.makeText(getApplication(), "OTP ERROR", Toast.LENGTH_SHORT).show();
                }else {
                mVerificationId = verificationId;
                mResendToken = token;
                }
            }
        };
    }

    public void GetCode(View view) {
        // validating phoneTextField
        if (phone.getText().toString().isEmpty() || phone.getText().toString().length() != 10) {
            Toast.makeText(getApplication(), "OTP ERROR", Toast.LENGTH_SHORT).show();
        } else {
            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
            myRef.child("Users").orderByChild("phoneNumber").equalTo(phone.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // Checking if the entered number has already been used for registration.
                            if (dataSnapshot.hasChildren()) {
                                Toast.makeText(getApplication(), "This number has already been registered with us", Toast.LENGTH_SHORT).show();
                                recreate();
                            } else {

                                // Request OTP
                                codePhone();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        }
    }

    public void verify(View view) {

        // validating codeTextField
        if (codeTextField.getText().toString().isEmpty()){
            Toast.makeText(getApplication(),"Please enter OTP",Toast.LENGTH_LONG).show();
        }else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, vcode.getText().toString());
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            vcode.setVisibility(View.GONE);
                            verify.setVisibility(View.GONE);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(mobile.this,"Invslid code",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void codePhone(){

        // Code to fetch the OTP within 60 seconds
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
        phone.setVisibility(View.GONE);
        getcode.setVisibility(View.GONE);
        vcode.setVisibility(View.VISIBLE);
        verify.setVisibility(View.VISIBLE);
    }


}