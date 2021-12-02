package com.bubba.yaga.resources;


import com.bubba.yaga.entity.User;
import com.bubba.yaga.service.UserInOrNearLondonService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/users/InOrNearLondon")
@Produces(MediaType.APPLICATION_JSON)
public class UserInOrNearLondonResource {

    private final UserInOrNearLondonService userInOrNearLondonService;

    public UserInOrNearLondonResource(UserInOrNearLondonService userInOrNearLondonService) {
        this.userInOrNearLondonService = userInOrNearLondonService;
    }

    @GET
    public Set<User> getUsersInOrNearLondon() {

        Set<User> users = userInOrNearLondonService.getUsersWhoLiveInOrNearLondon();
        if(!users.isEmpty()) {
            return users;
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
