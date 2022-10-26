package com.smart.fuelservicemobile.Models;

public class updateAdminShedRequest {

    private String name;
    private String fuelType;
    private String fuelLevel;
    private String isAvailable;

    public updateAdminShedRequest(String name, String fuelLevel, String isAvailable, String fuelType) {
        this.name = name;
        this.fuelType = fuelType;
        this.fuelLevel = fuelLevel;
        this.isAvailable = isAvailable;
    }
}
