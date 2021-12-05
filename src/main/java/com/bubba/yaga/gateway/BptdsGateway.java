package com.bubba.yaga.gateway;

import com.bubba.yaga.api.BpdtsApi;
import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import com.bubba.yaga.entity.UserWithCity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        this.webTarget = parseConfig(config);
    }

    public List<User> getUsersForCity(String city) {
        String jsonResponse = webTarget.path("city").path(city).path("users").request().get(String.class);

        return mapJsonResponseToListOfUsers(jsonResponse);
    };

    public List<User> getAllUsers() {
        String jsonResponse = webTarget.path("users").request().get(String.class);

        return mapJsonResponseToListOfUsers(jsonResponse);
    }

    public Optional<UserWithCity> getUserById(int id) {
        try {
            String jsonResponse = webTarget.path("user").path(Integer.toString(id)).request().get(String.class);
            return mapJsonResponseToUserWithCity(jsonResponse);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    private List<User> mapJsonResponseToListOfUsers(String jsonInput)  {
        List<User> listOfUsers;
        try {
            listOfUsers = objectMapper.readValue(jsonInput, new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.error("Exception thrown while parsing json response - " + e);
            listOfUsers = Collections.emptyList();
        }
        return listOfUsers;
    }

    private Optional<UserWithCity> mapJsonResponseToUserWithCity(String jsonInput) {
        Optional<UserWithCity> user;
        try {
            user = Optional.of(objectMapper.readValue(jsonInput, UserWithCity.class));
        } catch (IOException e) {
            LOGGER.error("Exception thrown while parsing json response - " + e);
            user = Optional.empty();
        }
        return user;
    }

    private WebTarget parseConfig(BptdsApiConfig config) {
        return client.target(config.getBaseURL());
    }
}
