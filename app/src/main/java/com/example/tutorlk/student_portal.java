package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class student_portal extends AppCompatActivity {

    TextView address,email,DOB,remarks,headername,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_portal);

        Intent intent=getIntent();

        String uid=intent.getStringExtra("uid");

        System.out.println(uid+" uid !!!!!!!!!!!!++++!!!!+++!!!");


        DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("Student").child(uid);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    ImageView stupic=(ImageView)findViewById(R.id.stdPortalpic);
                    TextView name=(TextView) findViewById(R.id.stdProtalname);
                    TextView edu=(TextView) findViewById(R.id.stdPortaleducation);
                    address=findViewById(R.id.stdportaladdress);
                    email=findViewById(R.id.stdPortalEmail);
                    DOB=findViewById(R.id.stdPortalDateOfbirth);
                    remarks=findViewById(R.id.stdportalRemarks);
                    headername=findViewById(R.id.stdProtalheaderName);


                    Picasso.get().load(dataSnapshot.child("imageUrl").getValue().toString()).into(stupic);
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    edu.setText(dataSnapshot.child("educationalState").getValue().toString());
                    address.setText(dataSnapshot.child("address").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    DOB.setText(dataSnapshot.child("dateOfBirth").getValue().toString());
                    remarks.setText(dataSnapshot.child("notableRemarks").getValue().toString());
                    headername.setText(dataSnapshot.child("name").getValue().toString());


                } else
                    Toast.makeText(getApplicationContext(), "No source to Display", Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });






//        int index=getindex(id,studentListDB);
//        Student_tab Student=studentListDB.get(index);





//        stu_pic.setImageResource(Student.getmImageResource());     // use setImageResourse
//        name.setText(Student.getmText1());
//        edu.setText(Student.getmText2());



    }


    public int getindex(String id,ArrayList<Student_tab> list){

        int noOfobject=list.size();

        for(int i=0;i<=noOfobject;i++){
            System.out.println(i);
            System.out.println(id);
            if(list.get(i).getName().equalsIgnoreCase(id)){

                return i;


            }
            else {
                System.out.println("\nSearching");

            }


        }
        System.out.println("object does not is not there in the studentlistDB arrays");
        return 6;


    }



}
