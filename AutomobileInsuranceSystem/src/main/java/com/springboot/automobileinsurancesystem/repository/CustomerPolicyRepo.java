package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.model.CustomerPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerPolicyRepo extends JpaRepository<CustomerPolicy, Long> {

    @Query("""
            select cp from CustomerPolicy cp
            where cp.customer.id = ?1
            """)
    List<CustomerPolicy> getByCustomerId(long customerId);

    // this fetches faster than manual query
    boolean existsByVehicleId(Long vehicleId);
}
