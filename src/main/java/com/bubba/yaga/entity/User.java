package com.bubba.yaga.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String ipAddress;
    private Double latitude;
    private Double longitude;
    private Optional<String> city;


    public User(int id, String firstName, String lastName, String email, String ipAddress, Double latitude, Double longitude, Optional<String> city) {
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
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty
    public Double getLongitude() {
        return longitude;
    }
    @JsonProperty
    public Optional<String> getCity() {
        return city;
    }
}
