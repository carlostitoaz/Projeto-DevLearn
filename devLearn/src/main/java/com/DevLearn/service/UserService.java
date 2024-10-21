package com.DevLearn.service;

import com.DevLearn.dao.IUserDAO;
import com.DevLearn.model.User;

import java.util.List;

public class UserService {
    private IUserDAO userDAO;

    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public List<User> listUser() {
        return userDAO.listUser();
    }

    public User findUserById(int id) {
        return userDAO.findUserById(id);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    public User validation(String email, String password){
        return userDAO.validation(email, password);
    }
}
