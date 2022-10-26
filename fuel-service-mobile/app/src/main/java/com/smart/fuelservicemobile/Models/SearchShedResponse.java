package com.smart.fuelservicemobile.Models;

public class SearchShedResponse {
    private String id;
    private String name;
    private String queueLength;
    private String date;

    public SearchShedResponse(String name, String userId, String queueLength, String date) {
        this.id = userId;
        this.name = name;
        this.queueLength = queueLength;
        this.date = date;
    }
}
