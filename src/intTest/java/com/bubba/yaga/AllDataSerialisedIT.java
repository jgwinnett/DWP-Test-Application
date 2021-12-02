package com.bubba.yaga;

import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import com.bubba.yaga.gateway.BptdsGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AllDataSerialisedIT {

    private final BptdsApiConfig config = new BptdsApiConfig();
    private Client client;
    private ObjectMapper objectMapper;
    private BptdsGateway gateway;

    @BeforeAll
    public void init() {
        config.setBaseURL("https://bpdts-test-app.herokuapp.com");
        client = ClientBuilder.newClient();
        objectMapper = new ObjectMapper();
        gateway = new BptdsGateway(client, config, objectMapper);
    }

    @Test
    public void allUsersAreSerialisedCorrectly() {
        List<User> allUsers = gateway.getAllUsers();

        allUsers.forEach(this::testUserSerialisation);
    }

    private void testUserSerialisation(User user) {
        assertThat(user.getId()).isNotNull();
        assertThat(user.getFirstName()).isNotEmpty();
        assertThat(user.getLastName()).isNotEmpty();
        assertThat(user.getEmail()).isNotEmpty();
        assertThat(user.getLatitude()).isNotNull();
        assertThat(user.getLatitude()).isNotNaN();
        assertThat(user.getLongitude()).isNotNull();
        assertThat(user.getLongitude()).isNotNaN();

    }

}
