package com.smart.fuelservicemobile.Models;

public class RegisterRequest {
    private String name;
    private String vehicleNo;
    private String vehicleType;
    private String email;
    private String password;

    public RegisterRequest(String email, String password, String vehicleNo, String vehicleType, String name) {
        this.email = email;
        this.password = password;
        this.vehicleNo = vehicleNo;
        this.name = name;
        this.vehicleType = vehicleType;
    }
}
