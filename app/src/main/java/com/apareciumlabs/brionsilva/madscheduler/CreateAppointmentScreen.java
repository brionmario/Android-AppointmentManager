package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAppointmentScreen extends AppCompatActivity {

    Button saveBtn;
    EditText titleET,timeET, detailsET;
    DBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_screen);

        myDBHandler = new DBHandler(this,null,null,1);

        //Get the extras passed from the previous activity
        Intent intent = getIntent();
        final String date = intent.getStringExtra("Date");
        //Toast.makeText(getBaseContext(), date ,Toast.LENGTH_SHORT ).show();

        titleET = (EditText) findViewById(R.id.titleEditText);
        timeET = (EditText) findViewById(R.id.timeEditText);
        detailsET = (EditText) findViewById(R.id.detailsEditText);

        saveBtn = (Button) findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDBHandler.createAppointment("5/7/7","4:4","dfdf", "sdksdfhs");
                //Toast.makeText(getBaseContext(),myDBHandler.printDatabase(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
