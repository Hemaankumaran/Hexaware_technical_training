package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;
import com.springboot.automobileinsurancesystem.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfficerRepo extends JpaRepository<Officer, Long> {

    @Query("""
            select o from Officer o
            join CustomerPolicy cp on o.id = cp.officer.id
            where cp.customer.id = ?1
            """)
    List<Officer> getByCustomerId(Long customerId);

    @Query("""
            select o from Officer o
            where o.officerDesignation = ?1
            """)
    List<Officer> getByDesignation(OfficerDesignation designation);
}
