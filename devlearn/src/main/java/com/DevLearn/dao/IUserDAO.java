package com.DevLearn.dao;

import com.DevLearn.model.User;

import java.util.ArrayList;

public interface IUserDAO {
    void addUser(User user);
    ArrayList<User> listUser();
    User findUserById(int id);
    void updateUser(User user);
    void deleteUser(int id);
    User validation(String email, String password);
}
