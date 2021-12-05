package com.bubba.yaga.api;

import com.bubba.yaga.entity.User;
import com.bubba.yaga.entity.UserWithCity;

import javax.ws.rs.GET;
import java.util.List;
import java.util.Set;

public interface UserInOrNearLondonApi {

  @GET
  Set<User> getUsersInOrNearLondon();

  @GET
  List<UserWithCity> getUsersWithCityInOrNearLondon();
}
