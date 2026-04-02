package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User, Long>{

    @Query("""
            select u from User u
            where u.username = ?1
            """)
    UserDetails getUserByUserName(String username);
}
