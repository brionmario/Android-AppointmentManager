package com.apareciumlabs.brionsilva.madscheduler;

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
                COLUMN_TIME + " TEXT ," +
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
    public void createAppointment(Appointment appointment){

        ContentValues contentValues = new ContentValues();

        //stores the values
        contentValues.put(COLUMN_DATE, appointment.getDate());
        contentValues.put(COLUMN_TIME, appointment.getTime());
        contentValues.put(COLUMN_TITLE, appointment.getTitle());
        contentValues.put(COLUMN_DETAILS, appointment.getDetails());

        //insert the values into the database
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_APPOINTMENTS, null, contentValues);
        db.close();
    }

    /**
     * Searches and deletes an appointment when the title is passed
     *
     * @param title Title of the appointment
     */
    public void deleteProduct(String title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_TITLE + "=\"" + title + "\";");
    }

    /**
     * Goes through the database and returns the result in a string
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
     * Deletes the content in a table when the name of the table is passed
     * @param TABLE_NAME Name of the table
     */
    public void clearTable(String TABLE_NAME) {

        SQLiteDatabase db = getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);

    }
}
