package com.example.tutorlk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tutorlk.model.tutorDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tutor_form extends AppCompatActivity {

    DatabaseReference dbRef;
    EditText address,nic,DOB,EducationQ,sub1,sub2,Remarks,name,phonenumber;
    tutorDetails tob=new tutorDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_form);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        phonenumber=findViewById(R.id.tutorPhoneNumber);
        address=findViewById(R.id.tutorAddress);
        nic=findViewById(R.id.tutorNIC);
        DOB=findViewById(R.id.tutorDOB);
        EducationQ=findViewById(R.id.tutorEduQulify);
        sub1=findViewById(R.id.tutorexp1);
        sub2=findViewById(R.id.tutorexp2);
        Remarks=findViewById(R.id.tutorNotable);
        name=findViewById(R.id.tutorName);


        Intent i=getIntent();
        final String email=i.getExtras().getString("email");









        Button btn=findViewById(R.id.btnCreateAccount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tob.setEmail(email);                                                            //email will be added from the previous activ
                tob.setPhoneNumber(Integer.parseInt(phonenumber.getText().toString().trim()));
                tob.setAddress(address.getText().toString().trim());
                tob.setDateOfBirth(DOB.getText().toString().trim());
                tob.setEducationQualification(EducationQ.getText().toString().trim());
                tob.setName(name.getText().toString().trim());
                tob.setNic(nic.getText().toString().trim());
                tob.setNotableRemarks(Remarks.getText().toString().trim());
                tob.setSub1(sub1.getText().toString().trim());
                tob.setSub2(sub2.getText().toString().trim());



                dbRef=FirebaseDatabase.getInstance().getReference().child("Tutor").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                dbRef.setValue(tob);
                Intent intent=new Intent(view.getContext(),Login.class);
                startActivity(intent);
            }
        });


    }


    public  String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }


}
