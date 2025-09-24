package com.example.demo.service;

import com.example.demo.entity.User;

public interface userService {
    public User RegisterUser(User user);
    public User findByUserName(String userName);
    public boolean emailExists(String email);
    public boolean userNameExists(String userName);

}
