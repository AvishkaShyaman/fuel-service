package com.smart.fuelservicemobile.Models;

public class updateAdminShedResponse {
    private String name;
    private String queueLength;
    private String date;
    private String fuelType;
    private String fuelLevel;

    public updateAdminShedResponse(String name, String queueLength, String date, String fuelLevel) {
        this.name = name;
        this.queueLength = queueLength;
        this.fuelLevel = fuelLevel;
        this.date = date;
    }
}
