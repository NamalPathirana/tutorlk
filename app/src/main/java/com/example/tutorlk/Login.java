package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    Toolbar toolbar;
    ProgressBar progressBar;
    EditText userEmail;
    EditText userPass;
    Button userLogin;
    Button forgotPass;

    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.loginEmail);
        userPass = findViewById(R.id.loginPassword);
        userLogin = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.btnUserForgottPass);

        toolbar.setTitle("Login");

        firebaseAuth = FirebaseAuth.getInstance();

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String checkEmail=userEmail.getText().toString().trim(); // for catch null in puts
                String checkPassword=userPass.getText().toString().trim(); // for catch null in puts


                if(checkEmail.equalsIgnoreCase("")==false &&  checkPassword.equalsIgnoreCase("")==false) {

                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(),
                            userPass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                                           // Toast.makeText(Login.this,firebaseAuth.getUid(), Toast.LENGTH_LONG).show();

                                            final String uid=firebaseAuth.getUid();

                                            dbRef= FirebaseDatabase.getInstance().getReference(); // the process to find th correct user type


                                            dbRef.child("Tutor").child(uid).addListenerForSingleValueEvent(new ValueEventListener() { // if a tutor
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.hasChildren()) {

                                                        startActivity(new Intent(Login.this, Tutor.class));
                                                        finish();


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

                                                        startActivity(new Intent(Login.this, Student.class));
                                                        finish();

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

                                                        Toast.makeText(Login.this, "a Admin", Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });







                                            startActivity(new Intent(Login.this, Student.class));
                                            finish();


                                        } else {
                                            Toast.makeText(Login.this, "Please verify your email address"
                                                    , Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(Login.this, task.getException().getMessage()
                                                , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else
                    Toast.makeText(Login.this, "Email or Password can't be empty", Toast.LENGTH_LONG).show();

            }
        });



        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPasswordActivity.class));
            }
        });




    }




    }









