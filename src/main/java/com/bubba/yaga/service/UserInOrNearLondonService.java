package com.bubba.yaga.service;

import com.bubba.yaga.entity.User;
import com.bubba.yaga.gateway.BptdsGateway;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInOrNearLondonService {

    private final BptdsGateway gateway;
    private final FilterUsersByProximityService filterUsersByProximityService;

    private static final String LONDON = "London";

    public UserInOrNearLondonService(BptdsGateway gateway, FilterUsersByProximityService filterUsersByProximityService) {
        this.gateway = gateway;
        this.filterUsersByProximityService = filterUsersByProximityService;
    }

    public Set<User> getUsersWhoLiveInOrNearLondon() {
        List<User> usersInLondon = getUsersWhoLiveInLondon();
        List<User> usersNearLondon = getUsersWhoLiveNearLondon();

        Set<User> combinedUsers = new LinkedHashSet<>();
        combinedUsers.addAll(usersInLondon);
        combinedUsers.addAll(usersNearLondon);

        return combinedUsers;
    }

    public List<User> getUsersWhoLiveInLondon() {
        return gateway.getUsersForCity(LONDON);
    }

    public List<User> getUsersWhoLiveNearLondon() {
        List<User> allUsers = gateway.getAllUsers();

        return allUsers.stream()
                .filter(user -> filterByProximity(user.getLatitude(), user.getLongitude()) )
                .collect(Collectors.toList());
    }

    public Set<User> getCityLocationForUsers(Set<User> users) {
        Set<User> usersWithCity = new HashSet<>();

        users.forEach( user ->
            gateway.getUserById(user.getId()).ifPresent(usersWithCity::add)
        );

        return usersWithCity;
    }

    private boolean filterByProximity(double lat, double lng) {
        return filterUsersByProximityService.isWithin50MilesOfLondon(lat, lng);
    }

}
