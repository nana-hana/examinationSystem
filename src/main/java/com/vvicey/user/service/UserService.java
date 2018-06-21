package com.vvicey.user.service;

import com.vvicey.user.entity.User;

public interface UserService {

    int createUser(User user);

    int cutOutUser(Integer uid);

    int reviseUser(User record);

    User checkUser(String username, String password);

}
