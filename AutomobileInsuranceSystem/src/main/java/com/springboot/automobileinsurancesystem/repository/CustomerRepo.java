package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("""
            select c from Customer c
            where c.user.username = ?1
            """)
    Customer getByUsername(String username);
}