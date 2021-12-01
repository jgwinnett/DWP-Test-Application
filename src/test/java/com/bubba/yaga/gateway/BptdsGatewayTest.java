package com.bubba.yaga.gateway;

import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WireMockTest
public class BptdsGatewayTest {

    private WireMock wiremock;

    private Client client = ClientBuilder.newClient();
    private BptdsApiConfig bptdsApiConfig = mock(BptdsApiConfig.class);
    private ObjectMapper objectMapper = mock(ObjectMapper.class);

    private BptdsGateway underTest;

    @BeforeEach
    public void setup(WireMockRuntimeInfo wmRuntimeInfo) {
        when(bptdsApiConfig.getBaseURL()).thenReturn(wmRuntimeInfo.getHttpBaseUrl());

        wiremock = wmRuntimeInfo.getWireMock();
        underTest  = new BptdsGateway(client, bptdsApiConfig, objectMapper);
    }

    @Nested
    class getUsersForCityTests {

        private final User expectedUser = new User(125, "Mechelle", "Boam", "mboam3q@thetimes.co.uk", "113.71.242.187", -6.5115909, 105.652983, Optional.empty());
        private static final String LONDON = "London";


        @Test
        public void shouldMakeHTTPGetRequestWithPathSameAsCity() throws JsonProcessingException {
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("cityLondonFound.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(expectedUser));

            underTest.getUsersForCity(LONDON);

            verify(getRequestedFor(urlEqualTo("/city/London/users")));
        }

        @Test
        public void shouldReturnAListOfUsers() throws JsonProcessingException {
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("cityLondonFound.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(expectedUser));

            List<User> usersForCity = underTest.getUsersForCity(LONDON);

            assertThat(usersForCity).isNotEmpty();
        }

        @Test
        public void shouldMapJsonStringResponseIntoListOfUser() throws JsonProcessingException {
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("cityLondonFound.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(expectedUser));

            List<User> actual = underTest.getUsersForCity(LONDON);

            assertThat(actual).isInstanceOf(List.class);
            assertThat(actual.get(0)).isEqualTo(expectedUser);
        }

        @Test
        public void shouldLogErrorIfObjectMapperReadValueThrowsException() throws IOException {

            LogCaptor logCaptor = LogCaptor.forClass(BptdsGateway.class);
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("cityLondonFound.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenAnswer( invocation-> {throw new IOException("Error processing JSON");});

            underTest.getUsersForCity(LONDON);

            assertThat(logCaptor.getErrorLogs()).containsExactly("Exception thrown while parsing json response - java.io.IOException: Error processing JSON");
        }


    }

    @Test
    public void getAllUsersShouldReturnAListOfUsers() {}

    @Test
    public void getUserByIdShouldReturnTheCorrelatedUser() {}

}
