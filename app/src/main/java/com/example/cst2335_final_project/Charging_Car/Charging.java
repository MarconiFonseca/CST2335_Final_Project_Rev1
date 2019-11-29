package com.example.cst2335_final_project.Charging_Car;

public class Charging {

    private String title;
    private Double latitude;
    private Double longitude;
    private String phone_number;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Charging(String title, Double latitude, Double longitude,String phone_number) {
        this.title=title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone_number = phone_number;
    }



}