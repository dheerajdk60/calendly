package com.mountblue.app.service;

import com.mountblue.app.model.User;

import java.util.Optional;

public interface UserService {

    void saveUser(User user);

    Optional<User> findByName(String name);

    Optional<User> findById(int userId);
}
