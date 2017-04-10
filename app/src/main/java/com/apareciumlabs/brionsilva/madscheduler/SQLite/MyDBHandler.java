package com.apareciumlabs.brionsilva.madscheduler.SQLite;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * This class is used to handle all the database transactions
 *
 * @author brionsilva
 * @version 1.0
 * @since 07/04/2017
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.apareciumlabs.brionsilva.madscheduler.Appointment;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MAD_DB.db";
    public static final String TABLE_APPOINTMENTS = "appointments";

    //Columns of the appointment table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DETAILS = "details";

    /**
     * Database information will be passed to the superclass
     *
     * @param context background context
     * @param name Name of the database
     * @param factory Cursor factory
     * @param version Database version
     */
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        //clears the database
        //context.deleteDatabase(DATABASE_NAME);
    }

    /**
     * Creates the table and the columns
     * @param db The SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE " + TABLE_APPOINTMENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_DATE + " TEXT ," +
                COLUMN_TIME + " DATETIME ," +
                COLUMN_TITLE + " TEXT ," +
                COLUMN_DETAILS + " TEXT " +
                ");";

        db.execSQL(query);
    }

    /**
     * If you want to change the database, this method simply drops the current table if it exists
     * and runs the oncreate method.
     *
     * @param db The SQLite database
     * @param oldVersion Old Version of the database
     * @param newVersion New Version of the Database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }


    /**
     * Creates an appointment based on the date, time, title and the details. if an appointment
     * exists with the same appointment name returns -1 and if not executes the insert query and
     * returns 1.
     *
     * @param appointment Instance of appointment class
     */
    public int createAppointment(Appointment appointment){

        SQLiteDatabase db = getWritableDatabase();

        String sql = " SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE "
                + COLUMN_DATE + "=\'" + appointment.getDate() + "\'" + " AND " + COLUMN_TITLE
                + "=\'" + appointment.getTitle() + "\';";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {

            ContentValues contentValues = new ContentValues();

            //stores the values
            contentValues.put(COLUMN_DATE , appointment.getDate());
            contentValues.put(COLUMN_TIME , appointment.getTime());
            contentValues.put(COLUMN_TITLE , appointment.getTitle());
            contentValues.put(COLUMN_DETAILS , appointment.getDetails());


            //insert the values into the database
            db.insert(TABLE_APPOINTMENTS , null , contentValues);
            db.close(); //restores the memory
            cursor.close();
            return 1;

        } else {

            return -1;

        }
    }

    /**
     * This function updates an appointment based on the date, time, title and the details. If the up[date is not
     * successful returns -1 and if successful updates the appointment and return 1
     *
     * @param appointment Instance of appointment class
     * @param time Time that need to updated
     * @param title Title that need to updated
     * @param details Details that need to updated
     * @return
     */
    public int updateAppointment(Appointment appointment , String time , String title , String details){

        SQLiteDatabase db = getWritableDatabase();

        String sql = " SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE "
                + COLUMN_DATE + "=\'" + appointment.getDate() + "\'" + " AND " +
                COLUMN_TITLE + "=\'" + appointment.getTitle() + "\';";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {

            return -1;

        } else {

            ContentValues contentValues = new ContentValues();

            //stores the values to be updated
            contentValues.put(COLUMN_TIME , time);
            contentValues.put(COLUMN_TITLE , title );
            contentValues.put(COLUMN_DETAILS , details);


            //insert the values into the database
            db.update(TABLE_APPOINTMENTS, contentValues , COLUMN_DATE + "='" + appointment.getDate() + "'" + " AND " +
                    COLUMN_TITLE + "='" + appointment.getTitle() + "'" , null);
            db.close(); //restores the memory
            cursor.close();
            return 1;

        }
    }

    /**
     * This function Moves an appointment to a different date
     *
     * @param appointment Instance of appointment class
     */
    public int moveAppointment(Appointment appointment , String date ){

        SQLiteDatabase db = getWritableDatabase();

        String sql = " SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE "
                + COLUMN_DATE + "=\'" + appointment.getDate() + "\'" + " AND " +
                COLUMN_TITLE + "=\'" + appointment.getTitle() + "\';";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {

            return -1;

        } else {

            ContentValues contentValues = new ContentValues();

            //stores the values to be updated
            contentValues.put(COLUMN_DATE , date);

            //insert the values into the database
            db.update(TABLE_APPOINTMENTS, contentValues , COLUMN_DATE + "='" + appointment.getDate() + "'" + " AND " +
                    COLUMN_TITLE + "='" + appointment.getTitle() + "'" , null);
            db.close(); //restores the memory
            cursor.close();
            return 1;

        }
    }

    /**
     * Searches and deletes all the appointments on a selected day
     *
     * @param date Date you wish to delete the appointments from
     */
    public void deleteAppointments(String date){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + "=\'" + date + "\';");
        db.close();
    }

    /**
     * Searches and deletes a specific appointments on a selected day
     *
     * @param date Date of the appointment
     * @param title Title of the appointment
     */
    public void deleteAppointments(String date , String title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + "=\'" + date + "\'"
                + " AND " + COLUMN_TITLE + "=\'" + title + "\';");
        db.close();
    }

    /**
     * Goes through the database and returns the result in a string (Entire Database)
     *
     * @return
     */
    public String databaseToString(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE 1 "; // 1 means every condition is met

        //Cursor exposes results from a query on a SQLiteDatabase
        Cursor cursor = db.rawQuery(query, null);
        //move the cursor to the first row of the results
        cursor.moveToFirst();

        //See if there are anymore results
        while (!cursor.isAfterLast()) {

            if (cursor.getString(cursor.getColumnIndex("title")) != null) {
                dbString += cursor.getString(cursor.getColumnIndex("date"));
                dbString += "~";
                dbString += cursor.getString(cursor.getColumnIndex("time"));
                dbString += "~";
                dbString += cursor.getString(cursor.getColumnIndex("title"));
                dbString += "~";
                dbString += cursor.getString(cursor.getColumnIndex("details"));
                dbString += "\n";
            }
            cursor.moveToNext();
        }
        db.close();
        return dbString;
    }


    /**
     * Goes through the database and returns the result for a single day
     *
     * @return
     */
    public List<Appointment> displayAppointments(String date){

        List<Appointment> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + "=\'" + date + "\'"
                + " ORDER BY " + COLUMN_TIME + " ASC";

        //Cursor exposes results from a query on a SQLiteDatabase
        Cursor cursor = db.rawQuery(query, null);
        //move the cursor to the first row of the results
        cursor.moveToFirst();

        //See if there are anymore results
        while (!cursor.isAfterLast()) {

            if (cursor.getString(cursor.getColumnIndex("title")) != null) {

                Appointment appointment = new Appointment(cursor.getString(cursor.getColumnIndex("date")) ,
                        cursor.getString(cursor.getColumnIndex("time")) ,
                        cursor.getString(cursor.getColumnIndex("title")) ,
                        cursor.getString(cursor.getColumnIndex("details")) );
                list.add(appointment);
            }
            cursor.moveToNext();
        }
        db.close();
        return list;
    }

    /**
     * Goes through the database and returns all  the appoints in the database
     * Note : used in the search function
     *
     * @return
     */
    public List<Appointment> displayAppointments(){

        List<Appointment> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_APPOINTMENTS +";";

        //Cursor exposes results from a query on a SQLiteDatabase
        Cursor cursor = db.rawQuery(query, null);
        //move the cursor to the first row of the results
        cursor.moveToFirst();

        //See if there are anymore results
        while (!cursor.isAfterLast()) {

            if (cursor.getString(cursor.getColumnIndex("title")) != null) {

                Appointment appointment = new Appointment(cursor.getString(cursor.getColumnIndex("date")) ,
                        cursor.getString(cursor.getColumnIndex("time")) ,
                        cursor.getString(cursor.getColumnIndex("title")) ,
                        cursor.getString(cursor.getColumnIndex("details")) );
                list.add(appointment);
            }
            cursor.moveToNext();
        }
        db.close();
        return list;
    }

    /**
     * Deletes the content in a table when the name of the table is passed
     * @param TABLE_NAME Name of the table
     */
    public void clearTable(String TABLE_NAME) {

        SQLiteDatabase db = getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);

    }
}

