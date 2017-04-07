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
    private static final String TABLE_APPOINTMENTS = "appointments";

    //Columns of the appointment table
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DETAILS = "details";




    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    /**
     * Creates the table and the columns
     * @param db The SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE" + TABLE_APPOINTMENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_DATE + " TEXT " +
                COLUMN_TIME + " TEXT " +
                COLUMN_TITLE + " TEXT unique" +
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

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    /**
     * Creates an appointment based on the date, time, title and the details. if an appointment
     * exists with the same appointment name returns -1 and if not executes the insert query and
     * returns 1.
     *
     * @param date Date of the appointment
     * @param time Time of the appointment
     * @param title Title of the appointment
     * @param details Details of the appointment
     */
    public int createAppointment(String date , String time , String title , String details){

        SQLiteDatabase db = getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_TITLE + "=" + title;
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {
            //Insert new
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DATE , date);
            contentValues.put(COLUMN_TIME , time);
            contentValues.put(COLUMN_TIME , title);
            contentValues.put(COLUMN_DETAILS , details);


            db.insert(TABLE_APPOINTMENTS , null , contentValues);
            db.close(); //restores the memory
            cursor.close();

        } else {

            return -1;

        }
        cursor.close();
        return 0;
    }
}
