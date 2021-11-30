package com.bubba.yaga.gateway;

import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.Client;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BptdsGatewayTest {


    @Mock private Client client;
    @Mock private BptdsApiConfig bptdsApiConfig;

    @InjectMocks
    private BptdsGateway underTest;

    @Test
    public void getUsersForCityShouldReturnAListOfUsers() {

        String city = "London";

        List<User> usersForCity = underTest.getUsersForCity(city);

        assertThat(usersForCity).isNotEmpty();
    }

    @Test
    public void getAllUsersShouldReturnAListOfUsers() {}

    @Test
    public void getUserByIdShouldReturnTheCorrelatedUser() {}

}
