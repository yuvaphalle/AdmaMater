package com.example.yuvap.adma;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.widget.*;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class update extends AppCompatActivity {

    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;

    Button submit;
    Button clear;

    ImageButton tenms;
    ImageButton twelvems;
    ImageButton idproof;

    ProgressBar progressBar;

    EditText fname;
    EditText mname;
    EditText lname;
    EditText email;
    EditText altEmail;
    EditText mobno;
    EditText phno;
    EditText address;
    EditText city;
    EditText pin;
    EditText dob;

    Spinner state;
    Spinner category;

    DatePickerDialog dobcal;

    RadioGroup gender;

    RadioButton male;
    RadioButton female;
    RadioButton other;

    ArrayAdapter adapter;

    //the firebase objects for storage and database
    StorageReference stapplicant;
    DatabaseReference dbApplicant;

    private String date;
    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);

        clear = (Button) findViewById(R.id.clear);
        submit = (Button) findViewById(R.id.submit);
        tenms = (ImageButton) findViewById(R.id.tenms);
        twelvems = (ImageButton) findViewById(R.id.twelvems);
        idproof = (ImageButton) findViewById(R.id.idproof);

        dob = (EditText) findViewById(R.id.dob);
        fname = (EditText) findViewById(R.id.firstName);
        mname = (EditText) findViewById(R.id.middleName);
        lname = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        altEmail = (EditText) findViewById(R.id.altEmail);
        mobno = (EditText) findViewById(R.id.mobNo);
        phno = (EditText) findViewById(R.id.phoneNo);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        pin = (EditText) findViewById(R.id.pinCode);

        state = (Spinner) findViewById(R.id.state);
        category = (Spinner) findViewById(R.id.category);

        stapplicant = FirebaseStorage.getInstance().getReference();
        dbApplicant = FirebaseDatabase.getInstance().getReference("Applicants");

        //dob date picker
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                dobcal = new DatePickerDialog(update.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                dob.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                dobcal.show();
            }
        });

        //tenth marksheet choosing
        tenms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //twelfth marksheet choosing
        twelvems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        //clear button
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname.setText("");
                mname.setText("");
                lname.setText("");
                email.setText("");
                altEmail.setText("");
                mobno.setText("");
                phno.setText("");
                address.setText("");
                state.setSelection(0);
                city.setText("");
                pin.setText("");
                gender.clearCheck();
                dob.setText("");
                category.setSelection(0);
            }
        });

        //making fields mandatory
        /**/

        //Sending data to store in Database
        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
                if (check == 4) {
                    addApplicant();
                }
            }
        });*/


    }

    //Method to add applicant data to database
    /*private void addApplicant() {
        String first_name = fname.getText().toString().trim();
        String middle_name = mname.getText().toString().trim();
        String last_name = lname.getText().toString().trim();
        String date_of_birth = dob.getText().toString().trim();
        String sex = "";
        if (male.isChecked()) {
            sex = male.getText().toString();
        } else if (female.isChecked()) {
            sex = female.getText().toString();
        } else if (other.isChecked()) {
            sex = other.getText().toString();
        }
        String e_mail = email.getText().toString();
        String alt_email = altEmail.getText().toString();
        String mob_no = mobno.getText().toString();
        String ph_no = phno.getText().toString();
        String address_ = address.getText().toString();
        String state_ = state.getSelectedItem().toString();
        String city_ = city.getText().toString();
        String pin_code = pin.getText().toString();
        String category_ = category.getSelectedItem().toString();

        if (!TextUtils.isEmpty(first_name) || !TextUtils.isEmpty(last_name) || !TextUtils.isEmpty(date_of_birth)
                || !TextUtils.isEmpty(e_mail) || !TextUtils.isEmpty(mob_no) || !TextUtils.isEmpty(address_)
                || !TextUtils.isEmpty(state_) || !TextUtils.isEmpty(city_)) {

            String id = dbApplicant.push().getKey();
            Applicant applicant = new Applicant(id, first_name, middle_name, last_name, date_of_birth, sex, e_mail, alt_email, mob_no
                    , ph_no, address_, state_, city_, pin_code, category_);
            dbApplicant.child(id).setValue(applicant);
            Toast.makeText(this, "Information updated", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(update.this,
                    MainActivity.class);
            startActivity(myIntent);
        } else {

            Toast.makeText(this, "Please fill all the required fields.", Toast.LENGTH_LONG).show();
        }

    }*/

    //check e-mail, alt-email, mobno, phno values to see if they have required length and/or regular expressions
    /*private void checkFields() {

        String e_mail = email.getText().toString();
        String alt_email = altEmail.getText().toString();
        String mob_no = mobno.getText().toString();
        String ph_no = phno.getText().toString();
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher1 = pattern.matcher(e_mail);
        Matcher matcher2 = pattern.matcher(alt_email);
        int lenmob, lenph;
        lenmob = mob_no.length();
        lenph = ph_no.length();

        if (!matcher1.find()) {
            email.setError("Invalid e-mail address");
            check -= 1;
        } else
            check += 1;

        if (!matcher2.find()) {
            altEmail.setError("Invalid e-mail address");
            check -= 1;
        } else
            check += 1;

        if (lenmob != 10) {
            mobno.setError("Invalid mobile number");
            check -= 1;
        } else
            check += 1;

        if (lenph != 10) {
            phno.setError("Invalid phone number");
            check -= 1;
        } else
            check += 1;
    }*/


}



/*pin.getText().toString().trim().equals("") || fname.getText().toString().trim().equals("")
                || lname.getText().toString().trim().equals("") || dob.getText().toString().trim().equals("")
                || email.getText().toString().trim().equals("") || mobno.getText().toString().trim().equals("")
                || address.getText().toString().trim().equals("") || city.getText().toString().trim().equals("")


                 && !matcher2.find() && lenmob==10 && lenph==10*/
