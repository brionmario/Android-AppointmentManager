package com.apareciumlabs.brionsilva.madscheduler;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * This class is used to create an appointment
 *
 * @author brionsilva
 * @version 1.0
 * @since 07/04/2017
 */
public class Appointment {

    private String date;
    private String time;
    private String title;
    private String details;

    /**
     * The appointment constructor
     * @param date
     * @param time
     * @param title
     * @param details
     */
    public Appointment(String date, String time, String title, String details) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

}
