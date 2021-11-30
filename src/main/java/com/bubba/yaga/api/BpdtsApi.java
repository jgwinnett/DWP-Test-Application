package com.bubba.yaga.api;

import com.bubba.yaga.entity.User;

import java.util.List;

public interface BpdtsApi {

    List<User> getUsersForCity(String city);

    List<User> getAllUsers();

    User getUserById(int id);
}
