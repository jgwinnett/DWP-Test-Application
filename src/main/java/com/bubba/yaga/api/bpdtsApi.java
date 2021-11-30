package com.bubba.yaga.api;

import com.bubba.yaga.entity.User;

import java.util.List;

public interface bpdtsApi {

    List<User> getUsersForCity(String city);

    List<User> getAllUsers();

    User getUserById(int id);
}
