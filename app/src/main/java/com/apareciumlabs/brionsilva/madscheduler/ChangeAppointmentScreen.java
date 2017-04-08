package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChangeAppointmentScreen extends AppCompatActivity {

    MyDBHandler myDBHandler;
    String date;

    Button confirmBtn;
    EditText appointmentNumberET;

    TextView heading, helperText;

    //list view stuff
    ArrayAdapter adapter;
    ListView listView;

    //lists to store the resulting appointments
    List<Appointment> listArr;
    ArrayList<String> arrayList;

    //variable to store the value input from the textbox
    String appointmentNumber;

    //variable to view the change type
    String changeType;

    //Update popup
    PopupWindow popupWindow;
    Button updateBtn;
    EditText titleET, timeET, detailsET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_appointment_screen);

        //get the selected date
        Intent intent = getIntent();
        date = intent.getStringExtra("Date");

        //catching the change type i.e Delete, Edit or Move
        changeType = intent.getStringExtra("Change Type");

        //initialising the textViews
        heading = (TextView) findViewById(R.id.headingTextView);
        helperText = (TextView) findViewById(R.id.helperTextView);

        //initialising the button and edit text
        confirmBtn = (Button) findViewById(R.id.confirmButton);
        appointmentNumberET = (EditText) findViewById(R.id.appointmentNumberEditText);

        if(changeType.equals("Delete")){

            heading.setText("DELETE APPOINTMENT");
            helperText.setText("Please enter the number of the appointment you wish to delete and press the DELETE button.");
            confirmBtn.setText("DELETE");


        }else if(changeType.equals("Edit")){

            heading.setText("VIEW/EDIT APPOINTMENT");
            helperText.setText("Please enter the number of the appointment you wish to edit and press the EDIT button.");
            confirmBtn.setText("EDIT");

        }else if(changeType.equals("Move")){

            heading.setText("MOVE APPOINTMENT");
            helperText.setText("Please enter the number of the appointment you wish to move and press the MOVE button.");
            confirmBtn.setText("MOVE");

        }else {
            heading.setText("CHANGE APPOINTMENT");
            helperText.setText("Something's Wrong!");
            Toast.makeText(getBaseContext() ,"Oops! Something went wrong!" , Toast.LENGTH_SHORT ).show();
            finish();
        }

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

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentNumber = appointmentNumberET.getText().toString();
                if(appointmentNumber.equals(null) || appointmentNumber.equals("")){
                    confirmBtn.setError("Please select a valid appointment number");
                    appointmentNumberET.setText("");
                }else{
                    try{

                        //if the change type is delete
                        if(changeType.equals("Delete")) {

                            errorDialog("Would you like to delete event : “ " +
                                    listArr.get(Integer.parseInt(appointmentNumber) - 1).getTitle() + " ”?");

                        } else if (changeType.equals("Edit")){

                            updateAppointmentPopup(v);

                        } else if (changeType.equals("Move")){

                        }
                        appointmentNumberET.setText("");
                    }catch (IndexOutOfBoundsException e){
                        appointmentNumberET.setText("");
                        Toast.makeText(getBaseContext(), "There's no appointment numbered " + appointmentNumber +
                                ". Please try again with a valid number." , Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    /**
     * This function creates a  error dialog box
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
                        //adapter.notifyDataSetChanged(); //refreshes the list, NOT WORKING
                        dialog.dismiss();

                        //bad way to refresh
                        finish();
                        startActivity(getIntent());
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

    /**
     * This junction creates a popup window with three textboxes and a button to update an
     * appointment
     *
     * @param v The current view instance is passed
     */
    private void updateAppointmentPopup (View v) {

        try {
            //get an instance of layoutinflater
            LayoutInflater inflater = (LayoutInflater) ChangeAppointmentScreen.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //initiate the view
            final View layout = inflater.inflate(R.layout.update_popup,
                    (ViewGroup) findViewById(R.id.updatePopupView));

            //initialize a size for the popup
            popupWindow = new PopupWindow(layout, 1200, 1650 ,  true);
            // display the popup in the center
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            //initialising the update popup button and edit texts
            titleET = (EditText) findViewById(R.id.updateTitleEditText);
            timeET = (EditText) findViewById(R.id.updateTimeEditText);
            detailsET = (EditText) findViewById(R.id.updateDetailsEditText);

            //Updates the selected appointment
            updateBtn = (Button) layout.findViewById(R.id.updateButton);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(),"Updated the appointment" ,Toast.LENGTH_LONG).show();
                    popupWindow.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
