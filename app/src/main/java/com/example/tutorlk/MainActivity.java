package com.example.tutorlk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    ProgressBar progressBar;
    EditText email;
    EditText password;
    Button signup;
    Button login;


    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//          toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signup = findViewById(R.id.btnSignup);
        login = findViewById(R.id.btnSignupLogin);

//        toolbar.setTitle(R.string.app_name); //don't know

        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String checkEmail=email.getText().toString().trim(); // for catch null in puts
                String checkPassword=password.getText().toString().trim(); // for catch null in puts



                if(checkEmail.equalsIgnoreCase("")==false &&  checkPassword.equalsIgnoreCase("")==false){



                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                       // Toast.makeText(MainActivity.this, "Registered successfully. Please check your email for verification",Toast.LENGTH_LONG).show();
                                                        Intent intent=new Intent(MainActivity.this,Choice.class);
                                                        intent.putExtra("email", email.getText().toString().trim());

                                                        startActivity(intent);
                                                        email.setText("");
                                                        password.setText("");

                                                    }else{
                                                        Toast.makeText(MainActivity.this,  task.getException().getMessage(),
                                                                Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            });
                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                }else{

                    Toast.makeText(MainActivity.this, "Email or password can't be empty",Toast.LENGTH_LONG).show();

                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });









        //---------my


//        Intent intent=new Intent(this, Login.class);
//        startActivity(intent);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();

        inflater.inflate(R.menu.demo,menu);

        return true;
    }

    




}
