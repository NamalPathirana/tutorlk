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

public class Login extends AppCompatActivity {


    Toolbar toolbar;
    ProgressBar progressBar;
    EditText userEmail;
    EditText userPass;
    Button userLogin;
    Button forgotPass;

    FirebaseAuth firebaseAuth;


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
                                            startActivity(new Intent(Login.this, Tutor.class));
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









