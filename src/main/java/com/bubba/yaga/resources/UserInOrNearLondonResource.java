package com.bubba.yaga.resources;


import com.bubba.yaga.api.UserInOrNearLondonApi;
import com.bubba.yaga.entity.User;
import com.bubba.yaga.entity.UserWithCity;
import com.bubba.yaga.service.UserInOrNearLondonService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.Collections.addAll;

@Path("/InOrNearLondon")
@Produces(MediaType.APPLICATION_JSON)
public class UserInOrNearLondonResource implements UserInOrNearLondonApi {

    private final UserInOrNearLondonService userInOrNearLondonService;

    public UserInOrNearLondonResource(UserInOrNearLondonService userInOrNearLondonService) {
        this.userInOrNearLondonService = userInOrNearLondonService;
    }

    @Path("users")
    @GET()
    public Set<User> getUsersInOrNearLondon() {

        Set<User> users = userInOrNearLondonService.getUsersWhoLiveInOrNearLondon();
        if(!users.isEmpty()) {
            return users;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Path("usersWithCity")
    @GET
    public List<UserWithCity> getUsersWithCityInOrNearLondon() {

        List<User> users = new ArrayList<>(userInOrNearLondonService.getUsersWhoLiveInOrNearLondon());
        List<UserWithCity> usersWithCity = userInOrNearLondonService.getCityLocationForUsers(users);
        if(!users.isEmpty()) {
            return usersWithCity;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
