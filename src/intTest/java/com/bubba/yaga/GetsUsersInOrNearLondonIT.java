package com.bubba.yaga;


import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import com.bubba.yaga.gateway.BptdsGateway;
import com.bubba.yaga.service.FilterUsersByProximityService;
import com.bubba.yaga.service.UserInOrNearLondonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.Set;

import static com.bubba.yaga.CommonTestData.USER_IN_LONDON_BY_CITY;
import static com.bubba.yaga.CommonTestData.USER_IN_LONDON_BY_COORDS;
import static com.bubba.yaga.CommonTestData.USER_NOT_IN_LONDON_BY_CITY;
import static com.bubba.yaga.CommonTestData.USER_NOT_IN_LONDON_BY_COORDS;
import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetsUsersInOrNearLondonIT {

    private final BptdsApiConfig config = new BptdsApiConfig();
    private Client client;
    private ObjectMapper objectMapper;
    private BptdsGateway gateway;
    private FilterUsersByProximityService filterUsersByProximityService;
    private UserInOrNearLondonService userInOrNearLondonService;

    @BeforeAll
    public void init() {
        config.setBaseURL("https://bpdts-test-app.herokuapp.com");
        client = ClientBuilder.newClient();
        objectMapper = new ObjectMapper();
        gateway = new BptdsGateway(client, config, objectMapper);
        filterUsersByProximityService = new FilterUsersByProximityService();
        userInOrNearLondonService = new UserInOrNearLondonService(gateway,filterUsersByProximityService);
    }

    @Test
    public void getUsersWhoLiveInLondon() {
        List<User> usersWhoLiveInLondon = userInOrNearLondonService.getUsersWhoLiveInLondon();

        assertThat(usersWhoLiveInLondon.size()).isEqualTo(6);
        assertThat(usersWhoLiveInLondon).extracting(User::getEmail).contains(USER_IN_LONDON_BY_CITY.getEmail());
        assertThat(usersWhoLiveInLondon).extracting(User::getEmail).doesNotContain(USER_NOT_IN_LONDON_BY_CITY.getEmail());
    }

    @Test
    public void getUsersWhoLiveNearLondon() {
        List<User> usersWhoLiveNearLondon = userInOrNearLondonService.getUsersWhoLiveNearLondon();

        assertThat(usersWhoLiveNearLondon.size()).isEqualTo(3);

        assertThat(usersWhoLiveNearLondon).extracting(User::getEmail).contains(USER_IN_LONDON_BY_COORDS.getEmail());
        assertThat(usersWhoLiveNearLondon).extracting(User::getEmail).doesNotContain(USER_NOT_IN_LONDON_BY_COORDS.getEmail());
    }

    @Test
    public void getUsersWhoLiveNearOrInLondon() {
        Set<User> usersWhoLiveInOrNearLondon = userInOrNearLondonService.getUsersWhoLiveInOrNearLondon();

        assertThat(usersWhoLiveInOrNearLondon.size()).isEqualTo(9);

        assertThat(usersWhoLiveInOrNearLondon).extracting(User::getEmail).contains(USER_IN_LONDON_BY_COORDS.getEmail());
        assertThat(usersWhoLiveInOrNearLondon).extracting(User::getEmail).doesNotContain(USER_NOT_IN_LONDON_BY_COORDS.getEmail());
        assertThat(usersWhoLiveInOrNearLondon).extracting(User::getEmail).contains(USER_IN_LONDON_BY_COORDS.getEmail());
        assertThat(usersWhoLiveInOrNearLondon).extracting(User::getEmail).doesNotContain(USER_NOT_IN_LONDON_BY_COORDS.getEmail());
    }
}