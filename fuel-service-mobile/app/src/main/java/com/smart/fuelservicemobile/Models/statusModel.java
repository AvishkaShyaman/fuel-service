package com.smart.fuelservicemobile.Models;

public class statusModel {
    private String status;
    private String message;
    private String code;

    public statusModel(String status, String message, String code) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
