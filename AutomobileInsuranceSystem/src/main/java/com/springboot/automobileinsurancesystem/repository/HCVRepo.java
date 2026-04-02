package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.model.HCV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HCVRepo extends JpaRepository<HCV, Long> {
}
