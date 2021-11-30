package com.bubba.yaga.gateway;

import com.bubba.yaga.api.BpdtsApi;
import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;

import javax.ws.rs.client.Client;
import java.util.Collections;
import java.util.List;

public class BptdsGateway implements BpdtsApi {

    private final Client client;
    private final BptdsApiConfig config;

    public BptdsGateway(Client client, BptdsApiConfig config) {
        this.client = client;
        this.config = config;
    };


    public List<User> getUsersForCity(String city) {

        return Collections.emptyList();
    };

    public List<User> getAllUsers() {

        return Collections.emptyList();

    };

    public User getUserById(int id) {

        return null;
    };

}
