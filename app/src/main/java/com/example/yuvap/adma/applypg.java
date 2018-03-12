package com.example.yuvap.adma;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class applypg extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button submit;
    Button clear;

    EditText fname;
    EditText mname;
    EditText lname;
    EditText fmgname;
    EditText email;
    EditText altEmail;
    EditText mobno;
    EditText phno;
    EditText address;
    EditText city;
    EditText pin;
    EditText dob;
    EditText tenmarks;
    EditText twelvemarks;
    EditText ugmarks;

    Spinner state;
    Spinner category;
    Spinner fmg;
    Spinner fpref;
    Spinner spref;

    DatePickerDialog dobcal;

    RadioGroup gender;
    RadioGroup gapyear;

    RadioButton male;
    RadioButton female;
    RadioButton other;
    RadioButton yes;
    RadioButton no;

    DatabaseReference dbApplicant;

    private String date;
    private int check;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applypg);

        gender = (RadioGroup) findViewById(R.id.gender);
        gapyear = (RadioGroup) findViewById(R.id.gapyear);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);

        clear = (Button) findViewById(R.id.clear);
        submit = (Button) findViewById(R.id.submit);

        dob = (EditText) findViewById(R.id.dob);
        fname = (EditText) findViewById(R.id.fname);
        mname = (EditText) findViewById(R.id.mname);
        lname = (EditText) findViewById(R.id.lname);
        fmgname = (EditText) findViewById(R.id.fmgName);
        email = (EditText) findViewById(R.id.email);
        altEmail = (EditText) findViewById(R.id.altEmail);
        mobno = (EditText) findViewById(R.id.mobNo);
        phno = (EditText) findViewById(R.id.phoneNo);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        pin = (EditText) findViewById(R.id.pinCode);
        tenmarks = (EditText) findViewById(R.id.tenaggr);
        twelvemarks = (EditText) findViewById(R.id.twelveaggr);
        ugmarks = (EditText) findViewById(R.id.ugaggr);

        dbApplicant = FirebaseDatabase.getInstance().getReference("Applicants PG Programmes");

        fmg = (Spinner) findViewById(R.id.fmg);
        /*ArrayAdapter<CharSequence> adapterfmg = ArrayAdapter.createFromResource
                (this, R.array.fmg, android.R.layout.simple_spinner_item);
        adapterfmg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fmg.setAdapter(adapterfmg);*/
        fmg.setOnItemSelectedListener(this);

        state = (Spinner) findViewById(R.id.state);
        /*ArrayAdapter<CharSequence> adapterstate = ArrayAdapter.createFromResource
                (this, R.array.states, android.R.layout.simple_spinner_item);
        adapterstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapterstate);*/
        state.setOnItemSelectedListener(this);

        category = (Spinner) findViewById(R.id.category);
        /*ArrayAdapter<CharSequence> adaptercategory = ArrayAdapter.createFromResource
                (this, R.array.category, android.R.layout.simple_spinner_item);
        adaptercategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adaptercategory);*/
        category.setOnItemSelectedListener(this);

        fpref = (Spinner) findViewById(R.id.fpref);
        /*ArrayAdapter<CharSequence> adapterfpref = ArrayAdapter.createFromResource
                (this, R.array.pgprog, android.R.layout.simple_spinner_item);
        adapterfpref.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fpref.setAdapter(adapterfpref);*/
        fpref.setOnItemSelectedListener(this);

        spref = (Spinner) findViewById(R.id.spref);
        /*ArrayAdapter<CharSequence> adapterspref = ArrayAdapter.createFromResource
                (this, R.array.pgprog, android.R.layout.simple_spinner_item);
        adapterspref.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spref.setAdapter(adapterspref);*/
        spref.setOnItemSelectedListener(this);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                dobcal = new DatePickerDialog(applypg.this,
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

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname.setText("");
                mname.setText("");
                lname.setText("");
                email.setText("");
                fmg.setSelection(0);
                fmgname.setText("");
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
                gapyear.clearCheck();
                tenmarks.setText("");
                twelvemarks.setText("");
                ugmarks.setText("");
                fpref.setSelection(0);
                spref.setSelection(0);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addApplicant();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.fpref:
                position = fpref.getSelectedItemPosition();
                if (position == 1) {
                    spref.setSelection(2);
                } else if (position == 2) {
                    spref.setSelection(1);
                }
                break;
            case R.id.spref:
                position = spref.getSelectedItemPosition();
                if (position == 1) {
                    fpref.setSelection(2);
                } else if (position == 2) {
                    fpref.setSelection(1);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addApplicant() {
        String first_name = fname.getText().toString().trim();
        String middle_name = mname.getText().toString().trim();
        String last_name = lname.getText().toString().trim();
        String fmg_ = fmg.getSelectedItem().toString();
        String fmg_name = fmgname.getText().toString().trim();
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
        String first_pref = fpref.getSelectedItem().toString();
        String second_pref = spref.getSelectedItem().toString();
        String ten_marks = tenmarks.getText().toString();
        String twelve_marks = twelvemarks.getText().toString();
        String ug_marks = ugmarks.getText().toString();
        String gap_year = "";
        if (yes.isChecked()) {
            gap_year = yes.getText().toString();
        } else if (no.isChecked()) {
            gap_year = no.getText().toString();
        }

        if (!TextUtils.isEmpty(first_name) || !TextUtils.isEmpty(last_name) || !TextUtils.isEmpty(fmg_)
                || !TextUtils.isEmpty(fmg_name) || !TextUtils.isEmpty(sex) || !TextUtils.isEmpty(first_pref)
                || !TextUtils.isEmpty(second_pref) || !TextUtils.isEmpty(ten_marks)
                || !TextUtils.isEmpty(twelve_marks) || !TextUtils.isEmpty(gap_year)
                || !TextUtils.isEmpty(category_) || !TextUtils.isEmpty(pin_code)
                || !TextUtils.isEmpty(date_of_birth) || !TextUtils.isEmpty(e_mail)
                || !TextUtils.isEmpty(mob_no) || !TextUtils.isEmpty(address_)
                || !TextUtils.isEmpty(state_) || !TextUtils.isEmpty(city_)) {

            String id = dbApplicant.push().getKey();
            ApplicantPG applicant = new ApplicantPG(id, first_name, middle_name, last_name, fmg_, fmg_name,
                    date_of_birth, sex, e_mail, alt_email, mob_no, ph_no, address_, state_, city_,
                    pin_code, category_, ten_marks, twelve_marks, ug_marks, first_pref, second_pref, gap_year);
            dbApplicant.child(id).setValue(applicant);
            Toast.makeText(this, "Applied Successfully", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(applypg.this,
                    MainActivity.class);
            startActivity(myIntent);
        } else {

            Toast.makeText(this, "Please fill all the required fields.", Toast.LENGTH_LONG).show();
        }
    }
}

