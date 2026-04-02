package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PolicyRepo extends JpaRepository<Policy, Long> {

    @Query("""
            select p from Policy p
            where (?1 is null or p.policyType = ?1) and (?2 is null or p.vehicleClass = ?2)
            """)
    List<Policy> getByFilter(PolicyType policyType, VehicleClass vehicleClass);
}
