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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bubba.yaga.CommonTestData.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BptdsGatewayTest {

    private WireMock wiremock;

    private Client client = ClientBuilder.newClient();
    private BptdsApiConfig bptdsApiConfig = mock(BptdsApiConfig.class);
    private ObjectMapper objectMapper = mock(ObjectMapper.class);

    private BptdsGateway underTest;

    @Nested
    @WireMockTest
    class getUsersForCityTests {

        private static final String LONDON = "London";

        @BeforeEach
        public void setup(WireMockRuntimeInfo wmRuntimeInfo) {
            when(bptdsApiConfig.getBaseURL()).thenReturn(wmRuntimeInfo.getHttpBaseUrl());

            wiremock = wmRuntimeInfo.getWireMock();
            underTest  = new BptdsGateway(client, bptdsApiConfig, objectMapper);
        }

        @Test
        public void shouldMakeHTTPGetRequestWithPathSameAsCity() throws JsonProcessingException {
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("cityLondonFound.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE));

            underTest.getUsersForCity(LONDON);

            verify(getRequestedFor(urlEqualTo("/city/London/users")));
        }

        @Test
        public void shouldReturnAListOfUsersIfResponseNotEmpty() throws JsonProcessingException {
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("cityLondonFound.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE));

            List<User> usersForCity = underTest.getUsersForCity(LONDON);

            assertThat(usersForCity).isNotEmpty();
        }

        @Test
        public void shouldReturnAnEmptyListIfResponseEmpty() throws JsonProcessingException {
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("empty.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(Collections.emptyList());

            List<User> usersForCity = underTest.getUsersForCity(LONDON);

            assertThat(usersForCity).isEmpty();
        }

        @Test
        public void shouldMapJsonStringResponseIntoListOfUser() throws JsonProcessingException {
            stubFor(get("/city/London/users").willReturn(aResponse().withBodyFile("cityLondonFound.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE));

            List<User> actual = underTest.getUsersForCity(LONDON);

            assertThat(actual).isInstanceOf(List.class);
            assertThat(actual.get(0)).isEqualTo(USER_MAURICE);
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

    @Nested
    @WireMockTest
    class getAllUsersTests {

        @BeforeEach
        public void setup(WireMockRuntimeInfo wmRuntimeInfo) {
            when(bptdsApiConfig.getBaseURL()).thenReturn(wmRuntimeInfo.getHttpBaseUrl());

            wiremock = wmRuntimeInfo.getWireMock();
            underTest  = new BptdsGateway(client, bptdsApiConfig, objectMapper);
        }

        @Test
        public void shouldMakeHTTPGetRequestToUserPath() throws JsonProcessingException {
            stubFor(get("/users").willReturn(aResponse().withBodyFile("users.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE, USER_BENDIX));

            underTest.getAllUsers();

            verify(getRequestedFor(urlEqualTo("/users")));
        }

        @Test
        public void shouldReturnAListOfUsers() throws JsonProcessingException {
            stubFor(get("/users").willReturn(aResponse().withBodyFile("users.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE, USER_BENDIX));

            List<User> actual = underTest.getAllUsers();

            assertThat(actual).isNotEmpty();
        }

        @Test
        public void shouldMapJsonStringResponseIntoListOfUser() throws JsonProcessingException {
            stubFor(get("/users").willReturn(aResponse().withBodyFile("users.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE, USER_BENDIX));

            List<User> actual = underTest.getAllUsers();

            assertThat(actual).isInstanceOf(List.class);
            assertThat(actual.get(0)).isEqualTo(USER_MAURICE);
            assertThat(actual.get(1)).isEqualTo(USER_BENDIX);
        }

        @Test
        public void shouldLogErrorIfObjectMapperReadValueThrowsException() throws IOException {
            LogCaptor logCaptor = LogCaptor.forClass(BptdsGateway.class);
            stubFor(get("/users").willReturn(aResponse().withBodyFile("users.json")));
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenAnswer( invocation-> {throw new IOException("Error processing JSON");});

            underTest.getAllUsers();

            assertThat(logCaptor.getErrorLogs()).containsExactly("Exception thrown while parsing json response - java.io.IOException: Error processing JSON");
        }
    }

    @Nested
    @WireMockTest
    class getUserByIdTests {

        @BeforeEach
        public void setup(WireMockRuntimeInfo wmRuntimeInfo) {
            when(bptdsApiConfig.getBaseURL()).thenReturn(wmRuntimeInfo.getHttpBaseUrl());

            wiremock = wmRuntimeInfo.getWireMock();
            underTest  = new BptdsGateway(client, bptdsApiConfig, objectMapper);
        }


        @Test
        public void shouldMakeHTTPGetRequestToUserForIdPath() throws JsonProcessingException {
            stubFor(get("/user/3").willReturn(aResponse().withBodyFile("userById.json")));
            when(objectMapper.readValue(anyString(), eq(User.class))).thenReturn(USER_MEGHAN);

            underTest.getUserById(3);

            verify(getRequestedFor(urlEqualTo("/user/3")));
        }

        @Test
        public void shouldMapJsonStringResponseIntoOptionalUser() throws JsonProcessingException {
            stubFor(get("/user/3").willReturn(aResponse().withBodyFile("userById.json")));
            when(objectMapper.readValue(anyString(), eq(User.class))).thenReturn(USER_MEGHAN);

            Optional<User> actual = underTest.getUserById(3);

            assertThat(actual).isInstanceOf(Optional.class);
        }

        @Test
        public void shouldReturnOptionalUserCorrespondingToId() throws JsonProcessingException {
            stubFor(get("/user/3").willReturn(aResponse().withBodyFile("userById.json")));
            when(objectMapper.readValue(anyString(), eq(User.class))).thenReturn(USER_MEGHAN);

            Optional<User> actual = underTest.getUserById(3);

            assertThat(actual.get()).isEqualTo(USER_MEGHAN);
        }

        @Test
        public void shouldReturnEmptyOptionalAndNotCallParserIfResponseStatusIs404()  {
            stubFor(get("/user/3").willReturn(status(404)));

            Optional<User> actual = underTest.getUserById(3);

            assertThat(actual).isEmpty();
            verifyNoInteractions(objectMapper);
        }



        @Test
        public void shouldLogErrorAndReturnEmptyOptionalIfObjectMapperReadValueThrowsException() throws IOException {
            LogCaptor logCaptor = LogCaptor.forClass(BptdsGateway.class);
            stubFor(get("/user/3").willReturn(aResponse().withBodyFile("userById.json")));
            when(objectMapper.readValue(anyString(), eq(User.class))).thenAnswer( invocation-> {throw new IOException("Error processing JSON");});

            underTest.getUserById(3);

            assertThat(logCaptor.getErrorLogs()).containsExactly("Exception thrown while parsing json response - java.io.IOException: Error processing JSON");
        }

    }

    @Test
    public void getUserByIdShouldReturnTheCorrelatedUser() {}

}
