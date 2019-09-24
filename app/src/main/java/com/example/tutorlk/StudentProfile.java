package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class StudentProfile extends AppCompatActivity {

    Button btnUpdate,btnRemoveAccount;
    TextView email,headerName,headerlikes,headerdislikes,headerViews;
    EditText address,nic,DOB,EducationQ,sub1,sub2,Remarks,name,phonenumber;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;
    Student_tab Student;
    ProgressBar bar;
    ImageView propic;

    private FirebaseStorage mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        headerName=findViewById(R.id.SPheaderName);

        Student=new Student_tab();

        bar=findViewById(R.id.SPprogressbar);
        email=findViewById(R.id.SPemail);
        address=findViewById(R.id.SPaddress);
        nic=findViewById(R.id.SPnic);
        DOB=findViewById(R.id.SPDOB);
        EducationQ=findViewById(R.id.SPeducationQulify);
        Remarks=findViewById(R.id.SPNotableRemarks);
        name=findViewById(R.id.SPname);
        phonenumber=findViewById(R.id.SPphoneNumber);
        btnUpdate=findViewById(R.id.btnSPupdate);
        btnRemoveAccount=findViewById(R.id.btnSPremoveAcc);
        propic=findViewById(R.id.SPpic);

        firebaseAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance();

        final String uid=firebaseAuth.getCurrentUser().getUid();
        final String[] imageUrl = new String[1];

        dbRef= FirebaseDatabase.getInstance().getReference().child("Student").child(uid);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    email.setText(dataSnapshot.child("email").getValue().toString());
                    address.setText(dataSnapshot.child("address").getValue().toString());
                    EducationQ.setText(dataSnapshot.child("educationalState").getValue().toString());
                    nic.setText(dataSnapshot.child("nic").getValue().toString());
                    DOB.setText(dataSnapshot.child("dateOfBirth").getValue().toString());
                    Remarks.setText(dataSnapshot.child("notableRemarks").getValue().toString());
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    phonenumber.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                    headerName.setText(dataSnapshot.child("name").getValue().toString());
                    Picasso.get().load(dataSnapshot.child("imageUrl").getValue().toString()).into(propic);


                    Student.setEmail(dataSnapshot.child("email").getValue().toString());                    //set the finals to recover form the update
                    Student.setUid(dataSnapshot.child("uid").getValue().toString());
                    Student.setImageUrl(dataSnapshot.child("imageUrl").getValue().toString());

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
                DatabaseReference updateRef=FirebaseDatabase.getInstance().getReference().child("Student");

                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(uid)){
                            try {


                                Student.setAddress(address.getText().toString());
                                Student.setPhoneNumber(Integer.parseInt(phonenumber.getText().toString()));
                                Student.setDateOfBirth(DOB.getText().toString());
                                Student.setName(name.getText().toString());
                                Student.setNic(nic.getText().toString());
                                Student.setNotableRemarks(Remarks.getText().toString());
                                Student.setEducationalState(EducationQ.getText().toString());



                                //Insert

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child(uid);
                                dbRef.setValue(Student);

                                bar.setVisibility(View.GONE); // shut down the progress bar
                                // feedback
                                Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_SHORT).show();

                            }catch (NumberFormatException e){

                                Toast.makeText(getApplicationContext(), "invalid Phone number ", Toast.LENGTH_SHORT).show();


                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE); // shut down the progress bar
                        }



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

                AlertDialog.Builder a_builder = new AlertDialog.Builder(StudentProfile.this);
                a_builder.setMessage("Do you want to Remove this account ?.\nIf continued all information will be deleted !")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                bar.setVisibility(View.VISIBLE);

                                DatabaseReference delRef=FirebaseDatabase.getInstance().getReference().child("Student");
                                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild(uid)){

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            user.delete()

                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {

                                                                StorageReference imageRef = mStorage.getReferenceFromUrl(imageUrl[0]); // remove the image from the storage
                                                                imageRef.delete();

                                                                dbRef =FirebaseDatabase.getInstance().getReference().child("Student");
                                                                dbRef.child(uid).removeValue();

                                                                Toast.makeText(getApplicationContext(), "Account Removed !", Toast.LENGTH_SHORT).show();
                                                                Intent intent=new Intent(StudentProfile.this,MainActivity.class);
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
