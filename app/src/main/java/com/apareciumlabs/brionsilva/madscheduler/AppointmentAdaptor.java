package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 10/04/2017
 */

public class AppointmentAdaptor extends ArrayAdapter<Appointment> {

    TextView titleTV , detailsTV;


    public AppointmentAdaptor(Context context, int textViewResourceId, List<Appointment> appointments) {
        super(context, textViewResourceId, appointments);
    }

    /**
     *
     * This method will create the rows for the list view
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row = (RelativeLayout)convertView;
        if(null == row){
            //No recycled View, we have to inflate one.
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout)inflater.inflate(R.layout.list_row_appointment, null);
        }

        //initializing the two text views
        titleTV = (TextView)row.findViewById(R.id.titleTextView);
        detailsTV = (TextView)row.findViewById(R.id.detailsTextView);


        //Set the resulting synonym category and synonyms in the TextViews
        titleTV.setText(getItem(pos).getTitle());
        detailsTV.setText(getItem(pos).getDetails());

        return row;


    }

}