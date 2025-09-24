package com.example.demo.service;

import com.example.demo.dao.userRepo;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class userServiceImpl implements userService{

    private userRepo userManager;
    private PasswordEncoder pass;
    @Autowired
    public userServiceImpl(userRepo userManager,PasswordEncoder pass){

        this.userManager=userManager;
        this.pass=pass;
    }



    @Override
    public User RegisterUser(User user) {
        if(userManager.existsByUserName(user.getUserName())){
            throw new RuntimeException("User name already exist...");
        }
        if(userManager.existsByEmail((user.getEmail()))){
            throw new RuntimeException("User Email already exist...");
        }

        user.setPassword(pass.encode(user.getPassword()));

        user.setRole("User");

return userManager.save(user);

    }
    @Override
    public User findByUserName(String userName) {
        return userManager.findByUserName(userName);
    }

    @Override
    public boolean userNameExists(String userName) {
        return userManager.existsByUserName(userName);
    }
    @Override
    public boolean emailExists(String email) {
        return userManager.existsByEmail(email);
    }
}
