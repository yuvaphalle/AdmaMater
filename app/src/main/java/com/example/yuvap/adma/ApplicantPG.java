package com.example.yuvap.adma;

/**
 * Created by USER on 04-03-2018.
 */

public class ApplicantPG {
    String applicant_pg_id;
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
    String ugmarks;
    String fpref;
    String spref;
    String gapyear;

    public ApplicantPG() {
    }

    public ApplicantPG(String applicant_pg_id, String fname, String mname, String lname, String fmg, String fmgname, String dob, String gender, String email, String altEmail, String mobNo, String phNo, String address, String state, String city, String pinCode, String quota, String tenmarks, String twelvemarks, String ugmarks, String fpref, String spref, String gapyear) {
        this.applicant_pg_id = applicant_pg_id;
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
        this.ugmarks = ugmarks;
        this.fpref = fpref;
        this.spref = spref;
        this.gapyear = gapyear;
    }

    public String getApplicant_pg_id() {
        return applicant_pg_id;
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

    public String getUgmarks() {
        return ugmarks;
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
}
