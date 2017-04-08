package com.apareciumlabs.brionsilva.madscheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

                String time = timeET.getText().toString();
                String title = titleET.getText().toString();
                String details = detailsET.getText().toString();

                if (TextUtils.isEmpty(time)){

                    timeET.setError("Please set a time for the appointment.");
                    return;

                }else if (TextUtils.isEmpty(title)) {

                    titleET.setError("Please set a title for the appointment.");
                    return;

                }else if(TextUtils.isEmpty(details)) {

                    detailsET.setError("Please set a details for the appointment.");
                    return;

                }else {

                    Appointment appointment = new Appointment(date, time, title, details);
                    int i = dbHandler.createAppointment(appointment);
                    if (i == 1) {

                        errorDialog("Appointment " + title + " on " + date + " was created successfully.");
                        printDatabase();

                    } else if (i == -1) {

                        errorDialog("Appointment "+ title +" already exists, please choose a different event title");
                    }


                }
                break;

            }
        }
    }

    /**
     * This function creates a dialog box which takes
     * @param error String parameter which is passed
     */
    public void errorDialog(String error)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this , R.style.BrionDialogTheme);
        builder.setMessage(error);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
