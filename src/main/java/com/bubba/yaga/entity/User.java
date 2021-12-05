package com.bubba.yaga.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String ipAddress;
    private final double latitude;
    private final double longitude;

    public User(@JsonProperty("id") int id, @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, @JsonProperty("email") String email, @JsonProperty("ip_address") String ipAddress, @JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty
    public double getLongitude() {
        return longitude;
    }
}
