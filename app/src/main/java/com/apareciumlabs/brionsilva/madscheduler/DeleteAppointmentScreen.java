package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteAppointmentScreen extends AppCompatActivity {

    MyDBHandler myDBHandler;

    String date;

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment_screen);

        //get the eelected date
        Intent intent = getIntent();
        date = intent.getStringExtra("Date");

        //creates an instance of the MyDBHandler
        myDBHandler = new MyDBHandler(this, null, null, 1);

        List<Appointment> listArr = myDBHandler.dailyAppointments(date);
        ArrayList<String> arrayList = new ArrayList<>();

        for(int j=0 ; j<listArr.size() ; j++){

            arrayList.add(j+1 + ". " + listArr.get(j).getTime() + " " + listArr.get(j).getTitle());
            Toast.makeText(getBaseContext() ,arrayList.get(j) , Toast.LENGTH_SHORT ).show();

        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, arrayList);

        ListView listView = (ListView) findViewById(R.id.appointmentList);
        listView.setAdapter(adapter);


        /*List<Appointment> listArr = myDBHandler.dailyAppointments(date);
        ArrayList<String> arrayList = new ArrayList<>();

        for(int j=0 ; j<listArr.size() ; j++){

            arrayList.add(j + ". " + listArr.get(j).getTime() + " " + listArr.get(j).getTitle());
            Toast.makeText(getBaseContext() ,arrayList.get(j) , Toast.LENGTH_SHORT ).show();

        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, arrayList);

        //initialising the listview and assigning the arrayadapter
        appoinmentListView = (ListView) findViewById(R.id.appointmentList) ;
        appoinmentListView.setAdapter(adapter);*/


    }
}
