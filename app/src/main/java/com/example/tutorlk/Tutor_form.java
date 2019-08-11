package com.example.tutorlk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tutor_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_form);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btn=findViewById(R.id.Tutor_account);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(),Tutor.class);
                startActivity(intent);
            }
        });


    }
}
