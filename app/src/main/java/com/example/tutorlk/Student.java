package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Student extends AppCompatActivity {


    Button userLogout=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        Toast.makeText(this,firebaseUser.getEmail(),Toast.LENGTH_SHORT).show();

        BottomNavigationView bottomNavigationView=findViewById(R.id.top_navigation_tutor);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_std,new TutorFrag()).commit();

        //---------Signout---------((( onclick don't work for items on the toolbar)))

//        Button userLogout=(Button) findViewById(R.id.btnSignout);
//
//        userLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(Tutor.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });


        //---------Signout---------


    }




    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectefFragment=null;

            switch (item.getItemId()){


                case R.id.std_nav_tutor_tab:
                    selectefFragment=new TutorFrag();
                    break;

                case R.id.std_nav_download_tab:
                    selectefFragment=new UploadFrag();
                    break;

                case R.id.nav_course_tab:
                    selectefFragment=new Coursefrag();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectefFragment).commit();


            return true;
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();

        inflater.inflate(R.menu.demo,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id =item.getItemId();

        if(id==R.id.btnSignout) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(Student.this);
            a_builder.setMessage("Do you want to Sign out ? ")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(Student.this, "Login Out", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(Student.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

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
        else if(id==R.id.account) {
            Toast.makeText(this, "accounts", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Student.this,tutorProfile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
