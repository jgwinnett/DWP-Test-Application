package com.bubba.yaga.gateway;

import com.bubba.yaga.api.BpdtsApi;
import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BptdsGateway implements BpdtsApi {

    private final static Logger LOGGER = LoggerFactory.getLogger(BptdsGateway.class);

    private final Client client;
    private final BptdsApiConfig config;
    private final WebTarget webTarget;
    private final ObjectMapper objectMapper;
    public BptdsGateway(Client client, BptdsApiConfig config, ObjectMapper objectMapper) {
        this.client = client;
        this.config = config;
        this.objectMapper = objectMapper;
        this.webTarget = client.target(config.getBaseURL());
    };


    public List<User> getUsersForCity(String city) {
        String jsonResponse = webTarget.path("city").path(city).path("users").request(MediaType.TEXT_PLAIN).get(String.class);

        List<User> listOfUsers = Collections.emptyList();

        try {
            listOfUsers = objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.error("Exception thrown while parsing json response - " + e);
        }

        return listOfUsers;
    };

    public List<User> getAllUsers() {

        return Collections.emptyList();

    };

    public User getUserById(int id) {

        return null;
    };

}
