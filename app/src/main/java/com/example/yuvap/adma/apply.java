package com.example.yuvap.adma;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class apply extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final static int PICK_PDF_CODE = 2342;

    Button submit;
    Button clear;
    Button chooser;

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
    EditText pdfname;

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
    StorageReference sApplicant;

    private String date;
    private int check;
    private int position;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        gender = (RadioGroup) findViewById(R.id.gender);
        gapyear = (RadioGroup) findViewById(R.id.gapyear);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);

        clear = (Button) findViewById(R.id.clear);
        submit = (Button) findViewById(R.id.submit);
        chooser = (Button) findViewById(R.id.choosedoc);

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
        pdfname = (EditText) findViewById(R.id.pdfName);

        dbApplicant = FirebaseDatabase.getInstance().getReference("Applicants UG Programmes");
        sApplicant = FirebaseStorage.getInstance().getReference("Applicant UG Programmes (PDF)");

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
                (this, R.array.ugprog, android.R.layout.simple_spinner_item);
        adapterfpref.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fpref.setAdapter(adapterfpref);*/
        fpref.setOnItemSelectedListener(this);

        spref = (Spinner) findViewById(R.id.spref);
        /*ArrayAdapter<CharSequence> adapterspref = ArrayAdapter.createFromResource
                (this, R.array.ugprog, android.R.layout.simple_spinner_item);
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
                dobcal = new DatePickerDialog(apply.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                                if(dayOfMonth<10 && (monthOfYear+1)<10){
                                    date = "0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year;
                                } else if(dayOfMonth<10 && (monthOfYear+1)>=10){
                                    date = "0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                } else if(dayOfMonth>=10 && (monthOfYear+1)<10) {
                                    date = dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year;
                                }

                                dob.setText(date);
                            }
                        }, mYear, mMonth, mDay);
                dobcal.show();
            }
        });

        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
                if(check==3) {
                    addApplicant();
                }
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

    public void addApplicant(){
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
        String gap_year = "";
        String pdf_name= pdfname.getText().toString();
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
                || !TextUtils.isEmpty(state_) || !TextUtils.isEmpty(city_) || filePath!=null) {


            String applicant_ug_id = dbApplicant.push().getKey();
            Applicant applicant = new Applicant(applicant_ug_id, first_name, middle_name, last_name, fmg_, fmg_name,
                    date_of_birth, sex, e_mail, alt_email, mob_no, ph_no, address_, state_, city_,
                    pin_code, category_, ten_marks, twelve_marks, first_pref, second_pref, gap_year, pdf_name);
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Documents");
            dbApplicant.child(applicant_ug_id).setValue(applicant);
            StorageReference msRef = FirebaseStorage.getInstance().getReference().child("documents/" + pdf_name);
            msRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(getApplicationContext(), "Applied Successfully.", Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(apply.this,
                                    MainActivity.class);
                            startActivity(myIntent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), "Documents failed to upload.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploaded");
                        }
                    });


        } else {

            Toast.makeText(this, "Please fill all the required fields.", Toast.LENGTH_LONG).show();
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        } else {
            Toast.makeText(getApplicationContext(), "Please select a document!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadDoc(String name) {
        if (filePath != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading Documents");
                StorageReference msRef = sApplicant.child("documents/" + name);
                msRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "Documents failed to upload.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage(((int) progress) + "% Uploaded");
                            }
                        });
            }
        }


    public void clearFields(View view){
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
        fpref.setSelection(0);
        spref.setSelection(0);
    }

    public void checkFields(){

        String e_mail = email.getText().toString();
        String alt_email = altEmail.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        String dobstr = dob.getText().toString();
        String beforestr = "31/03/1998";
        String afterstr = "31/03/1995";
        Date dobdate = new Date();
        Date before = new Date();
        Date after = new Date();
        try {
            dobdate = formatter.parse(dobstr);
            before =  formatter.parse(beforestr);
            after = formatter.parse(afterstr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher1 = pattern.matcher(e_mail);
        Matcher matcher2 = pattern.matcher(alt_email);

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
        if (!dobdate.before(before)&&!dobdate.after(after)){

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Sorry, you are not eligible due to not being in the specified age bracket.");
            dlgAlert.setTitle("Eligibility");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            check-=1;
        } else
            check+=1;

    }
}
