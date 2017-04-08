package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private Button crtAppoBtn,editAppoBtn,delAppoBtn,moveAppoBtn,searchBtn;
    Button deleteAllBtn , selectDeleteBtn;
    CalendarView calendarView;
    private int getYear,getMonth,getDay;

    private String date;
    PopupWindow popupWindow;

    MyDBHandler myDBHandler;

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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateSelected = simpleDateFormat.format(new GregorianCalendar(year, month, dayOfMonth).getTime());
                date = dateSelected;
                //Toast.makeText(getBaseContext(),dateSelected,Toast.LENGTH_SHORT).show();
            }
        });

        //initialize the default date  and assign it to the date variable in case if he user doesn't
        //click on any date.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateSelected = simpleDateFormat.format(new Date(calendarView.getDate()));
        date = dateSelected;

        //creates an instance of the MyDBHandler
        myDBHandler = new MyDBHandler(this, null, null, 1);

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
            case R.id.deleteAppointmentButton : {
                deleteAppointmentPopup(v);
                break;
            }
        }

    }

    /**
     * This junction creates just a simple popup window that has two buttons
     *
     * @param v The current view instance is passed
     */
    private void deleteAppointmentPopup (View v) {
        try {
            //get an instance of layoutinflater
            LayoutInflater inflater = (LayoutInflater) HomeScreen.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //initiate the view
            final View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popupView));

            //initialize a size for the popup
            popupWindow = new PopupWindow(layout, 1200, 900 ,  true);
            // display the popup in the center
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            //Deletes all the appointments for a given date
            deleteAllBtn = (Button) layout.findViewById(R.id.delAllButton);
            deleteAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(),"Deleted all the appointments on "+ date,Toast.LENGTH_LONG).show();
                    myDBHandler.deleteAppointments(date);
                    popupWindow.dismiss();
                }
            });

            //Opens up the list of appointments for the given date
            selectDeleteBtn = (Button) layout.findViewById(R.id.selDelButton);
            selectDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getBaseContext() , DeleteAppointmentScreen.class);
                    intent.putExtra("Date" , date ); // format - dd/MM/yyyy
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
