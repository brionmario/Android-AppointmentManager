package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private Button crtAppoBtn,editAppoBtn,delAppoBtn,moveAppoBtn,searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //initializing the buttons and adding onclick
        crtAppoBtn = (Button) findViewById(R.id.createAppointmentButton);
        crtAppoBtn.setOnClickListener(this);

        editAppoBtn = (Button) findViewById(R.id.viewEditButton);
        editAppoBtn.setOnClickListener(this);

        delAppoBtn = (Button) findViewById(R.id.deleteAppointmentButton);
        delAppoBtn.setOnClickListener(this);

        moveAppoBtn = (Button) findViewById(R.id.moveAppointmentButton);
        moveAppoBtn.setOnClickListener(this);

        searchBtn = (Button) findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.createAppointmentButton :{

                Intent intent = new Intent(getBaseContext() , CreateAppointmentScreen.class);
                startActivity(intent);

            }
        }

    }
}
