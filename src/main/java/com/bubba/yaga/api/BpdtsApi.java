package com.bubba.yaga.api;

import com.bubba.yaga.entity.User;
import com.bubba.yaga.entity.UserWithCity;

import java.util.List;
import java.util.Optional;

public interface BpdtsApi {

    List<User> getUsersForCity(String city);

    List<User> getAllUsers();

    Optional<UserWithCity> getUserById(int id);
}
