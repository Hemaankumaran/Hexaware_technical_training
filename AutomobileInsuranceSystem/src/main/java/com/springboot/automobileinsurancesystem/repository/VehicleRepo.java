package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.dto.VehicleByCustomerDto;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

    @Query("""
            select v from Vehicle v
            join CustomerPolicy cp on v.id = cp.vehicle.id
            where cp.customer.id = ?1
            """) // select customer via policy
    List<Vehicle> getCustomerById(long customerId);
}
