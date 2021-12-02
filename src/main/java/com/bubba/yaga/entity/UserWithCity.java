package com.bubba.yaga.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserWithCity extends User {

    private String city;

    public UserWithCity(@JsonProperty("id") int id, @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, @JsonProperty("email") String email, @JsonProperty("ip_address") String ipAddress, @JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude, @JsonProperty("city") String city) {
        super(id,firstName,lastName,email,ipAddress,latitude,longitude);
        this.city = city;
    }

    @JsonProperty
    public String getCity() {
        return city;
    }

}
