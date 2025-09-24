package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<User,Integer> {
   public boolean existsByEmail(String email);
    public boolean existsByUserName(String userName);
    public User findByUserName(String userName);
}
