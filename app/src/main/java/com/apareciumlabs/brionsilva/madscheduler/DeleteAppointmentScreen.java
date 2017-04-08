package com.apareciumlabs.brionsilva.madscheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteAppointmentScreen extends AppCompatActivity {

    MyDBHandler myDBHandler;
    String date;

    Button confirmBtn;
    EditText appointmentNumberET;

    //list view stuff
    ArrayAdapter adapter;
    ListView listView;

    //lists to store the resulting appointments
    List<Appointment> listArr;
    ArrayList<String> arrayList;

    //variable to store the value input from the textbox
    String appointmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment_screen);

        //get the selected date
        Intent intent = getIntent();
        date = intent.getStringExtra("Date");

        //creates an instance of the MyDBHandler
        myDBHandler = new MyDBHandler(this, null, null, 1);

        listArr = myDBHandler.displayAppointments(date);
        arrayList = new ArrayList<>();

        for(int j=0 ; j<listArr.size() ; j++){

            arrayList.add(j+1 + ". " + listArr.get(j).getTime() + " " + listArr.get(j).getTitle());
            //Toast.makeText(getBaseContext() ,arrayList.get(j) , Toast.LENGTH_SHORT ).show();

        }

        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, arrayList);

        listView = (ListView) findViewById(R.id.appointmentList);
        listView.setAdapter(adapter);

        //initialising the button and edit text
        confirmBtn = (Button) findViewById(R.id.confirmButton);
        appointmentNumberET = (EditText) findViewById(R.id.appoNumberEditText);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentNumber = appointmentNumberET.getText().toString();
                if(appointmentNumber.equals(null) || appointmentNumber.equals("")){
                    confirmBtn.setError("Please select an appointment number.");
                }else{
                    try{

                        errorDialog("Would you like to delete event : “ " +
                                listArr.get(Integer.parseInt(appointmentNumber)-1).getTitle() +" ”?");

                    }catch (IndexOutOfBoundsException e){

                        Toast.makeText(getBaseContext(), "There's no appointment numbered " + appointmentNumber +
                                ". Please try again with a valid number." , Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

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
                "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getBaseContext(), "Deleted the " +
                                listArr.get(Integer.parseInt(appointmentNumber) - 1).getTitle() +
                                " appointment.", Toast.LENGTH_SHORT).show();
                        myDBHandler.deleteAppointments(date , listArr.get(Integer.parseInt(appointmentNumber)-1).getTitle());
                        //adapter.notifyDataSetChanged(); //refreshes the list
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(
                "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
