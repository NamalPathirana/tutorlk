package com.example.tutorlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView=findViewById(R.id.top_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new StudentFrag()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectefFragment=null;

                    switch (item.getItemId()){


                        case R.id.nav_student_tab:
                            selectefFragment=new StudentFrag();
                            break;

                        case R.id.nav_upload_tab:
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

}
