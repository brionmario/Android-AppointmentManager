package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CreateAppointmentScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_screen);

        Intent intent = getIntent();

        Toast.makeText(getBaseContext(),intent.getStringExtra("Date"),Toast.LENGTH_SHORT ).show();
    }
}
