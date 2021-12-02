package com.bubba.yaga.gateway;

import com.bubba.yaga.api.BpdtsApi;
import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
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
        this.webTarget = client.target(config.getBaseURL());
    }

    public List<User> getUsersForCity(String city) {
        String jsonResponse = webTarget.path("city").path(city).path("users").request(MediaType.TEXT_PLAIN).get(String.class);

        return mapJsonResponseToListOfUsers(jsonResponse);
    };

    public List<User> getAllUsers() {
        String jsonResponse = webTarget.path("users").request(MediaType.TEXT_PLAIN).get(String.class);

        return mapJsonResponseToListOfUsers(jsonResponse);
    }

    public Optional<User> getUserById(int id) {
        try {
            String jsonResponse = webTarget.path("user").path(Integer.toString(id)).request(MediaType.TEXT_PLAIN).get(String.class);
            return mapJsonResponseToUser(jsonResponse);
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
    
    private Optional<User> mapJsonResponseToUser(String jsonInput) {
        Optional<User> user;
        try {
            user = Optional.of(objectMapper.readValue(jsonInput, User.class));
        } catch (IOException e) {
            LOGGER.error("Exception thrown while parsing json response - " + e);
            user = Optional.empty();
        }
        return user;
    }
}
