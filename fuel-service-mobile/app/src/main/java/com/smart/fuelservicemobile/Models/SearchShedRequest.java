package com.smart.fuelservicemobile.Models;

public class SearchShedRequest {
    private String name;
    private String userId;

    public SearchShedRequest(String name, String userId) {
        this.userId = userId;
        this.name = name;
    }
}
