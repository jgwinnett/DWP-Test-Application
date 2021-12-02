package com.bubba.yaga.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String ipAddress;
    private double latitude;
    private double longitude;
    private Optional<String> city;

    public User(@JsonProperty("id") int id, @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, @JsonProperty("email") String email, @JsonProperty("ip_address") String ipAddress, @JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude, @JsonProperty("city") Optional<String> city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
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
    @JsonProperty
    public Optional<String> getCity() {
        return city;
    }
}
