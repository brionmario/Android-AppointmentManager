package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.apareciumlabs.brionsilva.madscheduler.R;
import com.apareciumlabs.brionsilva.madscheduler.SQLite.MyDBHandler;

import java.util.ArrayList;
import java.util.List;

public class SearchAppointmentScreen extends AppCompatActivity implements View.OnClickListener{

    private Button searchBtn;
    private EditText searchET;

    MyDBHandler myDBHandler;


    //list view stuff
    AppointmentAdaptor appointmentAdaptor;
    ListView listView;

    //lists to store all the resulting appointments
    List<Appointment> listArr;
    //list to store matching appointments
    List<Appointment> listMatches;

    //variable to store the value input from the textbox
    String searchKeywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment_screen);

        //initialising the button and edit text
        searchBtn = (Button) findViewById(R.id.confirmButton);
        searchBtn.setOnClickListener(this);
        searchET = (EditText) findViewById(R.id.searchEditText);

        //creates an instance of the MyDBHandler
        myDBHandler = new MyDBHandler(this, null, null, 1);

        //call the displayappointment method and store all the appointments in a list
        listArr = myDBHandler.displayAppointments();

        //initialize the list view
        listView = (ListView) findViewById(R.id.searchList);

    }

    @Override
    public void onClick(View v) {

        //Hides the virtual keyboard when the buttons are clicked
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        switch (v.getId()){
            case R.id.confirmButton : {

                try {
                    if (searchET.getText().toString().equals("") || searchET.getText().toString().equals(null)) {
                        searchET.setError("Please input a Keyword");
                    } else {
                        //initialize a new list of apppointments
                        listMatches = new ArrayList<>();

                        //assign the edit text value to the searchKeywords variable
                        searchKeywords = searchET.getText().toString();


                        //see if the arraylist objectcts contain any of the keywords
                        for (Appointment appointment : listArr) {

                            if (appointment.getTitle().contains(searchKeywords)) {

                                listMatches.add(appointment);
                            }

                        }


                        appointmentAdaptor = new AppointmentAdaptor(getBaseContext(), -1, listMatches);
                        listView.setAdapter(appointmentAdaptor);
                    }
                }catch (Exception e){

                    Toast.makeText(getBaseContext(),"Couldn't find any matches", Toast.LENGTH_SHORT).show();

                }
                searchET.setText("");
                break;
            }
        }
    }
}
