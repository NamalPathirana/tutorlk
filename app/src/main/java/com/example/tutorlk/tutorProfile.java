package com.example.tutorlk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorlk.model.tutorDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class tutorProfile extends AppCompatActivity {

    Button btnUpdate,btnRemoveAccount;
    TextView email,headerName,headerlikes,headerdislikes,headerViews;
    EditText address,nic,DOB,EducationQ,sub1,sub2,Remarks,name,phonenumber;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;
    tutorDetails tutor;
    ProgressBar bar;
    ImageView propic;

    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        headerName=findViewById(R.id.TPheaderName);
        headerlikes=findViewById(R.id.TPheaderLikes);
        headerdislikes=findViewById(R.id.TPheaderDisLikes);
        headerViews=findViewById(R.id.TPheaderViews);

        tutor=new tutorDetails();

        bar=findViewById(R.id.TPprogressbar);
        email=findViewById(R.id.TPemail);
        address=findViewById(R.id.TPaddress);
        nic=findViewById(R.id.TPnic);
        DOB=findViewById(R.id.TPDOB);
        EducationQ=findViewById(R.id.TPeducationQulify);
        sub1=findViewById(R.id.TPsub1);
        sub2=findViewById(R.id.TPsub2);
        Remarks=findViewById(R.id.TPNotableRemarks);
        name=findViewById(R.id.TPname);
        phonenumber=findViewById(R.id.TPphoneNumber);
        btnUpdate=findViewById(R.id.btnTPupdate);
        btnRemoveAccount=findViewById(R.id.btnTPremoveAcc);
        propic=findViewById(R.id.TPpic);

        firebaseAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance();

        final String uid=firebaseAuth.getCurrentUser().getUid();
        final String[] imageUrl = new String[1];

        dbRef= FirebaseDatabase.getInstance().getReference().child("Tutor").child(uid);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    email.setText(dataSnapshot.child("email").getValue().toString());
                    address.setText(dataSnapshot.child("address").getValue().toString());
                    EducationQ.setText(dataSnapshot.child("educationQualification").getValue().toString());
                    nic.setText(dataSnapshot.child("nic").getValue().toString());
                    DOB.setText(dataSnapshot.child("dateOfBirth").getValue().toString());
                    sub1.setText(dataSnapshot.child("sub1").getValue().toString());
                    sub2.setText(dataSnapshot.child("sub2").getValue().toString());
                    Remarks.setText(dataSnapshot.child("notableRemarks").getValue().toString());
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    phonenumber.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                    headerName.setText(dataSnapshot.child("name").getValue().toString());
                    headerlikes.setText(dataSnapshot.child("likes").getValue().toString());
                    headerdislikes.setText(dataSnapshot.child("disLikes").getValue().toString());
                    headerViews.setText(dataSnapshot.child("views").getValue().toString());
                    Picasso.get().load(dataSnapshot.child("imageUrl").getValue().toString()).into(propic);

                    tutor.setLikes(Integer.parseInt(dataSnapshot.child("likes").getValue().toString()));        //set the finals to recover form the update
                    tutor.setDisLikes(Integer.parseInt(dataSnapshot.child("disLikes").getValue().toString()));
                    tutor.setViews(Integer.parseInt(dataSnapshot.child("views").getValue().toString()));
                    tutor.setEmail(dataSnapshot.child("email").getValue().toString());
                    tutor.setUid(dataSnapshot.child("uid").getValue().toString());
                    tutor.setImageUrl(dataSnapshot.child("imageUrl").getValue().toString());

                    imageUrl[0] =dataSnapshot.child("imageUrl").getValue().toString();



                } else
                    Toast.makeText(getApplicationContext(), "No source to Display", Toast.LENGTH_SHORT).show();


            }






            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bar.setVisibility(View.VISIBLE);
                DatabaseReference updateRef=FirebaseDatabase.getInstance().getReference().child("Tutor");

                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(uid)){
                            try {


                                tutor.setAddress(address.getText().toString());
                                tutor.setPhoneNumber(Integer.parseInt(phonenumber.getText().toString()));
                                tutor.setDateOfBirth(DOB.getText().toString());
                                tutor.setSub1(sub1.getText().toString());
                                tutor.setSub2(sub2.getText().toString());
                                tutor.setName(name.getText().toString());
                                tutor.setNic(nic.getText().toString());
                                tutor.setNotableRemarks(Remarks.getText().toString());
                                tutor.setEducationQualification(EducationQ.getText().toString());



                                //Insert

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Tutor").child(uid);
                                dbRef.setValue(tutor);

                                bar.setVisibility(View.GONE);
                                // feedback
                                Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_SHORT).show();

                            }catch (NumberFormatException e){

                                Toast.makeText(getApplicationContext(), "invalid Phone number ", Toast.LENGTH_SHORT).show();


                            }

                        }
                        else
                            Toast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT).show();





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });








            }
        });



        btnRemoveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(tutorProfile.this);
                a_builder.setMessage("Do you want to Remove this account ?.\nIf continued all information will be deleted !")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                bar.setVisibility(View.VISIBLE);

                                DatabaseReference delRef=FirebaseDatabase.getInstance().getReference().child("Tutor");
                                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild(uid)){

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            user.delete()

                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {

                                                                StorageReference imageRef = mStorage.getReferenceFromUrl(imageUrl[0]);
                                                                imageRef.delete();

                                                                dbRef =FirebaseDatabase.getInstance().getReference().child("Tutor");
                                                                dbRef.child(uid).removeValue();

                                                                Toast.makeText(getApplicationContext(), "Account Removed !", Toast.LENGTH_SHORT).show();
                                                                Intent intent=new Intent(tutorProfile.this,MainActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            else
                                                                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                                                        }
                                                    });



                                        }
                                        else{

                                            Toast.makeText(getApplicationContext(), "Accounts userID is not found", Toast.LENGTH_SHORT).show();


                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {


                                    }


                                });






                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }) ;
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert !!!");
                alert.show();









            }
        });







    }
}
