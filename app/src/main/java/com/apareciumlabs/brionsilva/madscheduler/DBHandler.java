package com.apareciumlabs.brionsilva.madscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 07/04/2017
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MAD_scheduler.db";
    public static final String TABLE_APPOINTMENTS = "appointments";

    //Columns of the appointment table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DETAILS = "details";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    /**
     * Creates the table and the columns
     * @param db The SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE " + TABLE_APPOINTMENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_DATE + " TEXT " +
                COLUMN_TIME + " TEXT " +
                COLUMN_TITLE + " TEXT " +
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

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
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

        /*String sql = " SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_TITLE + " =\" " + title + " \";";
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {*/
            //Insert new
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DATE , appointment.getDate());
            contentValues.put(COLUMN_TIME , appointment.getTime());
            contentValues.put(COLUMN_TIME , appointment.getTitle());
            contentValues.put(COLUMN_DETAILS , appointment.getDetails());

            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_APPOINTMENTS , null , contentValues);
            db.close(); //restores the memory
            /*cursor.close();

        } else {

            return -1;

        }
        cursor.close();
        return 0;*/
    }

    public void deleteAppointment(String title){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_TITLE + " =\" " + title + "\";");
    }

    public String printDatabase (){
            String dbString = "";
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE 1";// why not leave out the WHERE  clause?

            //Cursor points to a location in your results
            Cursor recordSet = db.rawQuery(query, null);
            //Move to the first row in your results
            recordSet.moveToFirst();

            //Position after the last row means the end of the results
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("title")) != null) {
                    dbString += recordSet.getString(recordSet.getColumnIndex("title"));
                    dbString += "\n";
                }
                recordSet.moveToNext();
            }
            db.close();
            return dbString;
        }
}
