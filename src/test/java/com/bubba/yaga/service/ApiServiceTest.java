package com.bubba.yaga.service;

import com.bubba.yaga.entity.User;
import com.bubba.yaga.gateway.BptdsGateway;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.bubba.yaga.CommonTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    private static final String LONDON = "London";

    @Mock
    private BptdsGateway gateway;
    @Mock
    private FilterUsersByProximityService filterUsersByProximityService;

    @InjectMocks
    private ApiService underTest;

    @Nested
    class getUsersWhoLiveInCityTests {

        @Test
        public void shouldCallGatewayGetUsersForCity() {
            underTest.getUsersWhoLiveInLondon();

            verify(gateway).getUsersForCity(anyString());
        }

        @Test
        public void shouldCallGatewayGetUsersForCityWithCityArgumentLondon() {
            underTest.getUsersWhoLiveInLondon();

            verify(gateway).getUsersForCity(LONDON);
        }

        @Test
        public void shouldReturnTheOutputOfGatewayGetUserForCity() {
            when(gateway.getUsersForCity(LONDON)).thenReturn(USERS_WITHOUT_CITY);

            List<User> actual = underTest.getUsersWhoLiveInLondon();
            assertThat(actual).isEqualTo(USERS_WITHOUT_CITY);
        }
    }

    @Nested
    class getUsersWhoLiveNearLondonTests {

        @Test
        public void shouldCallGatewayGetAllUsers() {
            underTest.getUsersWhoLiveNearLondon();

            verify(gateway).getAllUsers();
        }

        @Test
        public void shouldCallFilterForEachUser() {
            when(gateway.getAllUsers()).thenReturn(USERS_WITHOUT_CITY);

            underTest.getUsersWhoLiveNearLondon();

            verify(gateway).getAllUsers();
            verify(filterUsersByProximityService, times(2)).isWithin50MilesOfLondon(anyDouble(), anyDouble());
        }

        @Test
        public void shouldReturnListOfUsersWhoPassedFilter() {
            when(gateway.getAllUsers()).thenReturn(USERS_WITHOUT_CITY);
            when(filterUsersByProximityService.isWithin50MilesOfLondon(anyDouble(), anyDouble())).thenReturn(true).thenReturn(false);

            List<User> actual = underTest.getUsersWhoLiveNearLondon();

            assertThat(actual).isNotEmpty();
            assertThat(actual.size()).isEqualTo(1);
        }
    }

    @Nested
    class getUsersWhoLiveInOrNearLondonTests {

        @Test
        public void shouldReturnASet() {
            Set<User> actual = underTest.getUsersWhoLiveInOrNearLondon();

            assertThat(actual).isInstanceOf(Set.class);
        }

        @Test
        public void shouldCombineInCityAndNearCityResults() {
            when(gateway.getAllUsers()).thenReturn(USERS_WITHOUT_CITY);
            when(gateway.getUsersForCity(LONDON)).thenReturn(USERS_WITH_CITY);
            when(filterUsersByProximityService.isWithin50MilesOfLondon(anyDouble(), anyDouble())).thenReturn(true);

            Set<User> actual = underTest.getUsersWhoLiveInOrNearLondon();

            assertThat(actual.size()).isEqualTo(3);
            assertThat(actual).contains(USER_MAURICE, USER_BENDIX, USER_MEGHAN);
        }
    }
}
