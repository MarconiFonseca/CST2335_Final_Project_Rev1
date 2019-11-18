package com.example.cst2335_final_project.Charging_Car;

public class Charging {

    private int id;
    private int latitude;
    private int longitude;
    private String phone_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Charging(int id, int latitude, int longitude, String phone_number) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone_number = phone_number;
    }



}