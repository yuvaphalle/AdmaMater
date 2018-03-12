package com.example.yuvap.adma;


public class Applicant {
    String applicant_ug_id;
    String fname;
    String mname;
    String lname;
    String fmg;
    String fmgname;
    String dob;
    String gender;
    String email;
    String altEmail;
    String mobNo;
    String phNo;
    String address;
    String state;
    String city;
    String pinCode;
    String quota;
    String tenmarks;
    String twelvemarks;
    String fpref;
    String spref;
    String gapyear;
    String pdf_name;

    public Applicant(){

    }

    public Applicant(String applicant_ug_id, String fname, String mname, String lname, String fmg, String fmgname, String dob, String gender, String email, String altEmail, String mobNo, String phNo, String address, String state, String city, String pinCode, String quota, String tenmarks, String twelvemarks, String fpref, String spref, String gapyear, String pdf_name) {
        this.applicant_ug_id = applicant_ug_id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.fmg = fmg;
        this.fmgname = fmgname;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.altEmail = altEmail;
        this.mobNo = mobNo;
        this.phNo = phNo;
        this.address = address;
        this.state = state;
        this.city = city;
        this.pinCode = pinCode;
        this.quota = quota;
        this.tenmarks = tenmarks;
        this.twelvemarks = twelvemarks;
        this.fpref = fpref;
        this.spref = spref;
        this.gapyear = gapyear;
        this.pdf_name = pdf_name;
    }

    public String getApplicant_ug_id() {
        return applicant_ug_id;
    }

    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    public String getFmg() {
        return fmg;
    }

    public String getFmgname() {
        return fmgname;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAltEmail() {
        return altEmail;
    }

    public String getMobNo() {
        return mobNo;
    }

    public String getPhNo() {
        return phNo;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getQuota() {
        return quota;
    }

    public String getTenmarks() {
        return tenmarks;
    }

    public String getTwelvemarks() {
        return twelvemarks;
    }

    public String getFpref() {
        return fpref;
    }

    public String getSpref() {
        return spref;
    }

    public String getGapyear() {
        return gapyear;
    }

    public String getPdf_name(){
        return pdf_name;
    }
}
