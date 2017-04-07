package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAppointmentScreen extends AppCompatActivity implements View.OnClickListener {

    EditText titleET, timeET, detailsET;
    Button saveBtn;
    MyDBHandler dbHandler;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_screen);

        Intent intent = getIntent();
        date = intent.getStringExtra("Date");
        Toast.makeText(getBaseContext() , date , Toast.LENGTH_SHORT).show();

        //initializing the edit text boxes
        titleET = (EditText) findViewById(R.id.titleEditText);
        timeET = (EditText) findViewById(R.id.timeEditText);
        detailsET = (EditText) findViewById(R.id.detailsEditText);

        saveBtn = (Button) findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(this);

        /**
         * create a new database handler. null can be passed because the helper has all the constants
         * 1 is the database version
         */
        dbHandler = new MyDBHandler(this, null, null, 1);
        //dbHandler.clearTable("appointments");
        printDatabase();
    }

    /**
     * This method prints the current database
     */
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        Toast.makeText(getBaseContext() , dbString , Toast.LENGTH_LONG).show();
        titleET.setText(""); timeET.setText("");detailsET.setText("");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.saveButton : {

                Appointment appointment = new Appointment(date , timeET.getText().toString() ,
                        titleET.getText().toString() , detailsET.getText().toString());
                dbHandler.createAppointment(appointment);
                printDatabase();
                break;

            }
        }
    }
}
