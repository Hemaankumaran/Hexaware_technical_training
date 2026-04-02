package com.springboot.automobileinsurancesystem.repository;

import com.springboot.automobileinsurancesystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
