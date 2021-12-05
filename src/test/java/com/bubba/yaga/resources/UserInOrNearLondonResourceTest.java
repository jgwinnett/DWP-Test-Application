package com.bubba.yaga.resources;

import com.bubba.yaga.entity.User;
import com.bubba.yaga.entity.UserWithCity;
import com.bubba.yaga.service.UserInOrNearLondonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Set;

import static com.bubba.yaga.CommonTestData.USERS_WITH_CITY;
import static com.bubba.yaga.CommonTestData.USER_SET;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.EMPTY_SET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserInOrNearLondonResourceTest {

    @Mock
    private UserInOrNearLondonService userInOrNearLondonService;
    @InjectMocks
    private UserInOrNearLondonResource underTest;

    @Nested
    class getUsersTest {

        @Test
        public void shouldCallService() {
            try {
                underTest.getUsersInOrNearLondon();
            } catch (WebApplicationException ignored) {
            }

            verify(userInOrNearLondonService).getUsersWhoLiveInOrNearLondon();
        }

        @Test
        public void shouldReturnUserSetIfResultNotEmpty() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(USER_SET);

            Set<User> actual = underTest.getUsersInOrNearLondon();

            assertThat(actual).isInstanceOf(Set.class);
            assertThat(actual).isEqualTo(USER_SET);
        }

        @Test
        public void shouldReturnWebApplicationExceptionIfResultEmpty() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(EMPTY_SET);

            try {
                underTest.getUsersInOrNearLondon();
            } catch (WebApplicationException e) {
                assertThat(e.getResponse().getStatus()).isEqualTo(404);
                assertThat(e.getMessage()).isEqualTo("HTTP 404 Not Found");
            }
        }
    }

    @Nested
    class getUsersWithCityTest {

        @Test
        public void shouldCallService() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(USER_SET);

            underTest.getUsersWithCityInOrNearLondon();

            verify(userInOrNearLondonService).getUsersWhoLiveInOrNearLondon();
            verify(userInOrNearLondonService).getCityLocationForUsers(List.copyOf(USER_SET));
        }

        @Test
        public void shouldReturnUserWithCityIfResultNotEmpty() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(USER_SET);
            when(userInOrNearLondonService.getCityLocationForUsers(anyList())).thenReturn(USERS_WITH_CITY);

            List<UserWithCity> actual = underTest.getUsersWithCityInOrNearLondon();

            assertThat(actual).isInstanceOf(List.class);
            assertThat(actual).isEqualTo(USERS_WITH_CITY);
        }

        @Test
        public void shouldReturnWebApplicationExceptionIfResultEmpty() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(EMPTY_SET);
            try {
                underTest.getUsersInOrNearLondon();
            } catch (WebApplicationException e) {
                assertThat(e.getResponse().getStatus()).isEqualTo(404);
                assertThat(e.getMessage()).isEqualTo("HTTP 404 Not Found");
            }
        }
    }

}
