package com.example.tutorlk.model;

import android.content.Context;
import android.content.Intent;

import com.example.tutorlk.Tutor_portal;

public class tutorDetails{

    private String email;
    private String imageUrl;
    private String name;
    private String nic;
    private String dateOfBirth;
    private String educationQualification;
    private String address;
    private String sub1;
    private String sub2;
    private String notableRemarks;
    private int phoneNumber;
    private int likes=0;
    private int disLikes=0;
    private int views=0;
    private String uid;

    @Override
    public String toString() {
        return "tutorDetails{" +
                "email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", nic='" + nic + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", educationQualification='" + educationQualification + '\'' +
                ", address='" + address + '\'' +
                ", sub1='" + sub1 + '\'' +
                ", sub2='" + sub2 + '\'' +
                ", notableRemarks='" + notableRemarks + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", likes=" + likes +
                ", disLikes=" + disLikes +
                ", views=" + views +
                ", uid='" + uid + '\'' +
                '}';
    }

    public tutorDetails(){};

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public void setEducationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(int disLikes) {
        this.disLikes = disLikes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNic() {
        return nic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }


    public String getAddress() {
        return address;
    }

    public String getSub1() {
        return sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public String getNotableRemarks() {
        return notableRemarks;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public void setNotableRemarks(String notableRemarks) {
        this.notableRemarks = notableRemarks;
    }

    public void OpentTuotrPortal(Context context, String name){      // function will open the correct user protal

        String Tid=name;                                            //get user id
        Intent intent=new Intent(context, Tutor_portal.class);
        intent.putExtra("uid",Tid.trim());                   //set uid
        context.startActivity(intent);


    }



}
