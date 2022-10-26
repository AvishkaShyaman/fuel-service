package com.smart.fuelservicemobile.Models;

public class User {
    String id;
    String name;
    String email;
    String token;;
    String role;

    public User(String email, String name, String token, String role) {
        this.id = email;
        this.name = name;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
