package com.example.tutorlk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import android.view.View;
import android.widget.Toast;


public class Choice extends AppCompatActivity {


    String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);




        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        Spinner dynamicSpinner = (Spinner) findViewById(R.id.static_spinner);

        String[] items = new String[] { "Tutor","Student" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                type=(String) parent.getItemAtPosition(position);

                Button btn=findViewById(R.id.btnLogin);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(type.equalsIgnoreCase("Tutor")) {

                            Intent intent=getIntent();
                            String email=intent.getExtras().getString("email");
                           Toast.makeText(Choice.this,email,Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(view.getContext(), Tutor_form.class);
                            i.putExtra("email",email);
                            startActivity(i);
                        }
                        else if(type.equalsIgnoreCase("Student")) {
                            Intent intent = getIntent();
                            String email = intent.getExtras().getString("email");
                            Toast.makeText(Choice.this, email, Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(view.getContext(), Student_form.class);
                            i.putExtra("email", email);
                            startActivity(i);
                        }

                    }
                });


            }

                @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });






    }





}
