package com.cubezytech.pillsreminder.Model;

import java.util.ArrayList;

public class Common {

    String date, time, pills, taken, taken_time;
    ArrayList<String> ids;
    ArrayList<String> from;
    ArrayList<String> medType;

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public ArrayList<String> getFrom() {
        return from;
    }

    public void setFrom(ArrayList<String> from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPills() {
        return pills;
    }

    public void setPills(String pills) {
        this.pills = pills;
    }

    public ArrayList<String> getMedType() {
        return medType;
    }

    public void setMedType(ArrayList<String> medType) {
        this.medType = medType;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public String getTaken_time() {
        return taken_time;
    }

    public void setTaken_time(String taken_time) {
        this.taken_time = taken_time;
    }
}
