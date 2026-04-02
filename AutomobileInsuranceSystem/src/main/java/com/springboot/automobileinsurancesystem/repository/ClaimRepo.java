package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import com.springboot.automobileinsurancesystem.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClaimRepo extends JpaRepository<Claim, Long> {

    @Query("""
            select c from Claim c
            where c.claimStatus = ?1
            """)
    List<Claim> getByClaimStatus(ClaimStatus claimStatus);
}
