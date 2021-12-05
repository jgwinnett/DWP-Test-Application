package com.bubba.yaga.resources;


import com.bubba.yaga.api.UserInOrNearLondonApi;
import com.bubba.yaga.entity.User;
import com.bubba.yaga.service.UserInOrNearLondonService;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserInOrNearLondonResource implements UserInOrNearLondonApi {

    private final UserInOrNearLondonService userInOrNearLondonService;

    public UserInOrNearLondonResource(UserInOrNearLondonService userInOrNearLondonService) {
        this.userInOrNearLondonService = userInOrNearLondonService;
    }

    @Path("InOrNearLondon")
    @GET()
    public Set<User> getUsersInOrNearLondon(@DefaultValue("false") @QueryParam("withCity") boolean withCity) {

        Set<User> users = userInOrNearLondonService.getUsersWhoLiveInOrNearLondon();
        if(!users.isEmpty()) {
            if(withCity) {
                return userInOrNearLondonService.getCityLocationForUsers(users);
            } else {
                return users;
            }
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
