package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorlk.model.tutorDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Tutor_portal extends AppCompatActivity {

    TextView address,email,DOB,remarks,headername,name,sub1,sub2,phoneNumber;
    Button btnChat,btnLike,btnDisLike,btnReport;
    String tutorID;
    DatabaseReference dbRef,dbUpdate;
    public tutorDetails tutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_portal);

        Intent intent=getIntent();

        final String  uid=intent.getStringExtra("uid");

        System.out.println(uid+" uid !!!!!!!!!!!!++++!!!!+++!!!");

        tutorID=uid;

        btnChat=(Button) findViewById(R.id.btnTutorChat);
        btnLike=findViewById(R.id.btnStudentThumbesUp);

        tutor=new tutorDetails(); // tutor object

        DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("Tutor").child(uid);

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    ImageView stupic=(ImageView)findViewById(R.id.tutorPortalpic);
                    TextView name=(TextView) findViewById(R.id.tutorProtalname);
                    TextView edu=(TextView) findViewById(R.id.tutorPortalEducationalQ);
                    address=findViewById(R.id.tutorportaladdress);
                    email=findViewById(R.id.tutorPortalEmail);
                    DOB=findViewById(R.id.tutorPortalDateOfbirth);
                    remarks=findViewById(R.id.tutorPortalRemarks);
                    headername=findViewById(R.id.tutorPortalHeaderName);
                    sub1=findViewById(R.id.tutorPortalSub1);
                    sub2=findViewById(R.id.tutorPortalSub2);
                    phoneNumber=findViewById(R.id.tutorPortalPhoneNumber);


                    //create a tutor object to make updates possible.(using the object)

                    tutor.setImageUrl(dataSnapshot.child("imageUrl").getValue().toString());
                    tutor.setName(dataSnapshot.child("name").getValue().toString());
                    tutor.setEducationQualification(dataSnapshot.child("educationQualification").getValue().toString());
                    tutor.setAddress(dataSnapshot.child("address").getValue().toString());
                    tutor.setEmail(dataSnapshot.child("email").getValue().toString());
                    tutor.setDateOfBirth(dataSnapshot.child("dateOfBirth").getValue().toString());
                    tutor.setNotableRemarks(dataSnapshot.child("notableRemarks").getValue().toString());
                    tutor.setSub1(dataSnapshot.child("sub1").getValue().toString());
                    tutor.setSub2(dataSnapshot.child("sub2").getValue().toString());
                    tutor.setPhoneNumber(Integer.parseInt(dataSnapshot.child("phoneNumber").getValue().toString()));
                    tutor.setViews(Integer.parseInt(dataSnapshot.child("views").getValue().toString()));
                    tutor.setLikes(Integer.parseInt(dataSnapshot.child("likes").getValue().toString()));
                    tutor.setDisLikes(Integer.parseInt(dataSnapshot.child("disLikes").getValue().toString()));
                    tutor.setUid(dataSnapshot.child("uid").getValue().toString());
                    tutor.setNic(dataSnapshot.child("nic").getValue().toString());



                    Picasso.get().load(dataSnapshot.child("imageUrl").getValue().toString()).into(stupic);
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    edu.setText(dataSnapshot.child("educationQualification").getValue().toString());
                    address.setText(dataSnapshot.child("address").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    DOB.setText(dataSnapshot.child("dateOfBirth").getValue().toString());
                    remarks.setText(dataSnapshot.child("notableRemarks").getValue().toString());
                    headername.setText(dataSnapshot.child("name").getValue().toString());
                    sub1.setText(dataSnapshot.child("sub1").getValue().toString());
                    sub2.setText(dataSnapshot.child("sub2").getValue().toString());
                    phoneNumber.setText(dataSnapshot.child("phoneNumber").getValue().toString());

                    //System.out.println(tutor);

                    int currentViews=Integer.parseInt(dataSnapshot.child("views").getValue().toString()); //update views
                    System.out.println(currentViews);
                    currentViews=currentViews+1;

                    tutor.setViews(currentViews);
                    addView(uid,tutor);
                    updataTutor(uid,tutor);

                } else
                    Toast.makeText(getApplicationContext(), "No source to Display", Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }







        });









        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent chat=new Intent(Tutor_portal.this, TutorChat.class);
                chat.putExtra("tutorID",tutorID);
                startActivity(chat);


            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btnLike.setBackgroundResource(R.drawable.thumbsupblue);



            }
        });



    }


    public int getindex(String id, ArrayList<Student_tab> list){

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

    public void addView(String uid, final tutorDetails t){  // function to increment the tutors views don't work



        dbRef=FirebaseDatabase.getInstance().getReference("Tutor").child(uid);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()){



                }
                else
                    Toast.makeText(Tutor_portal.this,"View addition faild",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    public void updataTutor(final String uid,tutorDetails t){


                dbUpdate = FirebaseDatabase.getInstance().getReference().child("Tutor").child(uid);
                dbUpdate.setValue(t);



    }






}
