package com.example.tutorlk;


import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//home class will be the fist to run to check whether the userAuth info is still in the sys. If not by the sequence of manifest system sill start from the
//mainActivity file


public class Home extends Application { // home class will handle the case when the user stops the app when logged in and the Auth info still exist.
                                        // This class checks if their logged in or not.
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference dbRef;

        String uid;

        if(firebaseUser != null && firebaseUser.isEmailVerified()){


            uid=firebaseUser.getUid();


            dbRef= FirebaseDatabase.getInstance().getReference(); // the process to find th correct user type


            dbRef.child("Tutor").child(uid).addListenerForSingleValueEvent(new ValueEventListener() { // if a tutor
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren()) {


                        Intent i = new Intent(Home.this,Tutor.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            dbRef.child("Student").child(uid).addListenerForSingleValueEvent(new ValueEventListener() { //if a student
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren()) {


                        Intent i = new Intent(Home.this,Student.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            dbRef.child("Admin").child(uid).addListenerForSingleValueEvent(new ValueEventListener() { //if a admin
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren()) {

                        Toast.makeText(Home.this, "a Admin", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });






        }
    }

}
