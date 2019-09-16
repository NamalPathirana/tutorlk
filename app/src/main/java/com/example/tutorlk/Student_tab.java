package com.example.tutorlk;

import android.content.Context;
import android.content.Intent;

public class Student_tab {

    private String imageUrl;
    private String name;
    private String educationalState;
    private String address;
    private String dateOfBirth;
    private String notableRemarks;
    private int phoneNumber;
    private String email;
    private String uid;
    private String nic;



    public Student_tab (){}

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNotableRemarks() {
        return notableRemarks;
    }

    public void setNotableRemarks(String notableRemarks) {
        this.notableRemarks = notableRemarks;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducationalState() {
        return educationalState;
    }

    public void setEducationalState(String educationalState) {
        this.educationalState = educationalState;
    }

    public void OpentStudentportal(Context context, String name){

        String Sid=name;                                            //get user id
        Intent intent=new Intent(context,student_portal.class);
        intent.putExtra("uid",Sid.trim());                   //set uid
        context.startActivity(intent);


    }


}
