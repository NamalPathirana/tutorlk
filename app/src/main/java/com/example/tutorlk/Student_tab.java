package com.example.tutorlk;

import android.content.Context;
import android.content.Intent;

public class Student_tab {

    private int mImageResource;
    private String mText1;
    private String mText2;

    public Student_tab(int mImageResource, String mText1, String mText2) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void OpentStudentportal(Context context, String name){

        String Sid=name;
        Intent intent=new Intent(context,student_portal.class);
        intent.putExtra("Studentid",Sid.trim());
        context.startActivity(intent);


    }


}
