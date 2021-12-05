package com.bubba.yaga.resources;

import com.bubba.yaga.entity.User;
import com.bubba.yaga.service.UserInOrNearLondonService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.WebApplicationException;
import java.util.Set;

import static com.bubba.yaga.CommonTestData.USER_SET;
import static com.bubba.yaga.CommonTestData.USER_SET_WITH_CITY;
import static java.util.Collections.EMPTY_SET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
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
        public void shouldCallUserInOrNearLondonService() {
            try {
                underTest.getUsersInOrNearLondon(false);
            } catch (WebApplicationException ignored) {
            }

            verify(userInOrNearLondonService).getUsersWhoLiveInOrNearLondon();
        }

        @Test
        public void shouldReturnUserSetIfResultNotEmpty() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(USER_SET);

            Set<User> actual = underTest.getUsersInOrNearLondon(false);

            assertThat(actual).isInstanceOf(Set.class);
            assertThat(actual).isEqualTo(USER_SET);
        }

        @Test
        public void shouldReturnWebApplicationExceptionIfResultEmpty() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(EMPTY_SET);

            try {
                underTest.getUsersInOrNearLondon(false);
            } catch (WebApplicationException e) {
                assertThat(e.getResponse().getStatus()).isEqualTo(404);
                assertThat(e.getMessage()).isEqualTo("HTTP 404 Not Found");
            }
        }
    }

    @Nested
    class getUsersWithCityTest {

        @Test
        public void shouldCallGetCityLocationForUsersIfQueryParamTrue() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(USER_SET);

            underTest.getUsersInOrNearLondon(true);
            verify(userInOrNearLondonService).getCityLocationForUsers(USER_SET);
        }

        @Test
        public void shouldReturnUserSetWithCityLocation() {
            when(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon()).thenReturn(USER_SET);
            when(userInOrNearLondonService.getCityLocationForUsers(eq(USER_SET))).thenReturn(USER_SET_WITH_CITY);

            Set<User> actual = underTest.getUsersInOrNearLondon(true);

            assertThat(actual).isEqualTo(USER_SET_WITH_CITY);
        }
    }

}
