package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.model.LCV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LCVRepo extends JpaRepository<LCV, Long> {
}
