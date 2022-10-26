package com.smart.fuelservicemobile.Models;

public class getAdminShedResponse {
    private String id;
    private String name;
    private String queueLength;
    private String date;
    private String fuelType;
    private String fuelLevel;

    public getAdminShedResponse(String name, String userId, String queueLength, String date, String fuelLevel) {
        this.id = userId;
        this.name = name;
        this.queueLength = queueLength;
        this.fuelLevel = fuelLevel;
        this.date = date;
    }
}
