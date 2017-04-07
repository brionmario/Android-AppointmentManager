package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private Button crtAppoBtn,editAppoBtn,delAppoBtn,moveAppoBtn,searchBtn;
    CalendarView calendarView;
    private int getYear,getMonth,getDay;

    private String date;

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

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                getYear = year ;
                getMonth = month;
                getDay = dayOfMonth ;

                //assign the date variable with the clicked date
                date = getDay + "/" + getMonth + "/" + getYear ;
               // Toast.makeText(getBaseContext() , year + "/" + month + "/" + dayOfMonth , Toast.LENGTH_SHORT).show();
            }
        });

        //initialize the default date  and assign it to the date variable in case if he user doesn't
        //click on any date.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateSelected = simpleDateFormat.format(new Date(calendarView.getDate()));
        date = dateSelected;


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.createAppointmentButton :{

                Intent intent = new Intent(getBaseContext() , CreateAppointmentScreen.class);
                intent.putExtra("Date" , date ); // format - dd/MM/yyyy
                startActivity(intent);

                break;

            }
        }

    }
}
