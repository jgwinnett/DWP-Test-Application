package com.bubba.yaga.gateway;

import com.bubba.yaga.config.BptdsApiConfig;
import com.bubba.yaga.entity.User;
import com.bubba.yaga.entity.UserWithCity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bubba.yaga.CommonTestData.USER_BENDIX;
import static com.bubba.yaga.CommonTestData.USER_MAURICE;
import static com.bubba.yaga.CommonTestData.USER_MEGHAN_CITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BptdsGatewayTest {


    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Client client;
    @Mock
    private BptdsApiConfig bptdsApiConfig;
    @Mock
    private ObjectMapper objectMapper;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebTarget webtarget;

    @InjectMocks
    private BptdsGateway underTest;

    @Nested
    class getUsersForCityTests {

        private static final String LONDON = "London";

        @BeforeEach
        public void setup() {
            Mockito.reset();
            when(bptdsApiConfig.getBaseURL()).thenReturn("localhost");
            when(client.target(anyString())).thenReturn(webtarget);
            underTest = new BptdsGateway(client, bptdsApiConfig, objectMapper);
        }

        @Test
        public void shouldUseCityArgumentWithinPath() throws JsonProcessingException {
            WebTarget webTargetCityPath = mock(WebTarget.class, RETURNS_DEEP_STUBS);
            WebTarget webTargetLondon = mock(WebTarget.class, RETURNS_DEEP_STUBS);

            when(webtarget.path(anyString())).thenReturn(webTargetCityPath);
            when(webTargetCityPath.path(anyString())).thenReturn(webTargetLondon);

            when(webTargetLondon.path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE));

            underTest.getUsersForCity(LONDON);

            verify(webtarget).path("city");
            verify(webTargetCityPath).path(LONDON);
            verify(webTargetLondon).path("users");
        }

        @Test
        public void shouldReturnAListOfUsersIfResponseNotEmpty() throws JsonProcessingException {
            when(webtarget.path(anyString()).path(anyString()).path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE));

            List<User> usersForCity = underTest.getUsersForCity(LONDON);

            assertThat(usersForCity).isNotEmpty();
        }

        @Test
        public void shouldReturnAnEmptyListIfResponseEmpty() throws JsonProcessingException {
            when(webtarget.path(anyString()).path(anyString()).path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(Collections.emptyList());

            List<User> usersForCity = underTest.getUsersForCity(LONDON);

            assertThat(usersForCity).isEmpty();
        }

        @Test
        public void shouldMapJsonStringResponseIntoListOfUser() throws JsonProcessingException {
            when(webtarget.path(anyString()).path(anyString()).path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE));

            List<User> actual = underTest.getUsersForCity(LONDON);

            assertThat(actual).isInstanceOf(List.class);
            assertThat(actual.get(0)).isEqualTo(USER_MAURICE);
        }

        @Test
        public void shouldLogErrorIfObjectMapperReadValueThrowsException() throws IOException {
            LogCaptor logCaptor = LogCaptor.forClass(BptdsGateway.class);
            when(webtarget.path(anyString()).path(anyString()).path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenAnswer(invocation -> {
                throw new IOException("Error processing JSON");
            });

            underTest.getUsersForCity(LONDON);

            assertThat(logCaptor.getErrorLogs()).containsExactly("Exception thrown while parsing json response - java.io.IOException: Error processing JSON");
        }
    }

    @Nested
    class getAllUsersTests {

        @BeforeEach
        public void setup() {
            Mockito.reset();
            when(bptdsApiConfig.getBaseURL()).thenReturn("localhost");
            when(client.target(anyString())).thenReturn(webtarget);
            underTest = new BptdsGateway(client, bptdsApiConfig, objectMapper);
        }

        @Test
        public void shouldMakeHTTPGetRequestToUserPath() throws JsonProcessingException {
            WebTarget userPath = mock(WebTarget.class, RETURNS_DEEP_STUBS);

            when(webtarget.path(anyString())).thenReturn(userPath);
            when(userPath.request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE, USER_BENDIX));

            underTest.getAllUsers();

            verify(webtarget).path("users");

        }

        @Test
        public void shouldReturnAListOfUsers() throws JsonProcessingException {
            when(webtarget.path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE, USER_BENDIX));

            List<User> actual = underTest.getAllUsers();

            assertThat(actual).isNotEmpty();
        }

        @Test
        public void shouldMapJsonStringResponseIntoListOfUser() throws JsonProcessingException {
            when(webtarget.path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenReturn(List.of(USER_MAURICE, USER_BENDIX));

            List<User> actual = underTest.getAllUsers();

            assertThat(actual).isInstanceOf(List.class);
            assertThat(actual.get(0)).isEqualTo(USER_MAURICE);
            assertThat(actual.get(1)).isEqualTo(USER_BENDIX);
        }

        @Test
        public void shouldLogErrorIfObjectMapperReadValueThrowsException() throws IOException {
            LogCaptor logCaptor = LogCaptor.forClass(BptdsGateway.class);
            when(webtarget.path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), Mockito.<TypeReference<List<User>>>any())).thenAnswer(invocation -> {
                throw new IOException("Error processing JSON");
            });

            underTest.getAllUsers();

            assertThat(logCaptor.getErrorLogs()).containsExactly("Exception thrown while parsing json response - java.io.IOException: Error processing JSON");
        }
    }

    @Nested
    class getUserByIdTests {

        @BeforeEach
        public void setup() {
            Mockito.reset();
            when(bptdsApiConfig.getBaseURL()).thenReturn("localhost");
            when(client.target(anyString())).thenReturn(webtarget);
            underTest = new BptdsGateway(client, bptdsApiConfig, objectMapper);
        }

        @Test
        public void shouldMakeHTTPGetRequestToUserForIdPath() throws JsonProcessingException {
            final int id = USER_MEGHAN_CITY.getId();

            WebTarget userPath = mock(WebTarget.class);
            WebTarget userIdPath = mock(WebTarget.class, RETURNS_DEEP_STUBS);

            when(webtarget.path(anyString())).thenReturn(userPath);
            when(userPath.path(anyString())).thenReturn(userIdPath);
            when(userIdPath.request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), eq(UserWithCity.class))).thenReturn(USER_MEGHAN_CITY);

            underTest.getUserById(id);

            verify(webtarget).path("user");
            verify(userPath).path(Integer.toString(id));
        }

        @Test
        public void shouldMapJsonStringResponseIntoOptionalUser() throws JsonProcessingException {
            when(webtarget.path(anyString()).path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), eq(UserWithCity.class))).thenReturn(USER_MEGHAN_CITY);

            Optional<UserWithCity> actual = underTest.getUserById(3);

            assertThat(actual).isInstanceOf(Optional.class);
        }

        @Test
        public void shouldReturnOptionalUserCorrespondingToId() throws JsonProcessingException {
            when(webtarget.path(anyString()).path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), eq(UserWithCity.class))).thenReturn(USER_MEGHAN_CITY);

            Optional<UserWithCity> actual = underTest.getUserById(3);

            assertThat(actual.get()).isEqualTo(USER_MEGHAN_CITY);
        }

        @Test
        public void shouldReturnEmptyOptionalAndNotCallParserIfResponseStatusIs404() {
            when(webtarget.path(anyString()).path(anyString()).request().get(eq(String.class))).thenThrow(new NotFoundException());

            Optional<UserWithCity> actual = underTest.getUserById(1);

            assertThat(actual).isEmpty();
            verifyNoInteractions(objectMapper);
        }

        @Test
        public void shouldLogErrorAndReturnEmptyOptionalIfObjectMapperReadValueThrowsException() throws IOException {
            LogCaptor logCaptor = LogCaptor.forClass(BptdsGateway.class);
            when(webtarget.path(anyString()).path(anyString()).request().get(eq(String.class))).thenReturn("{}");
            when(objectMapper.readValue(anyString(), eq(UserWithCity.class))).thenAnswer(invocation -> {
                throw new IOException("Error processing JSON");
            });

            underTest.getUserById(1);

            assertThat(logCaptor.getErrorLogs()).containsExactly("Exception thrown while parsing json response - java.io.IOException: Error processing JSON");
        }
    }
}

