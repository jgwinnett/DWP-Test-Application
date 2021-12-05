package com.bubba.yaga.api;

import com.bubba.yaga.entity.User;

import javax.ws.rs.GET;
import java.util.Set;

public interface UserInOrNearLondonApi {

  @GET
  Set<User> getUsersInOrNearLondon(boolean withCity);

}
