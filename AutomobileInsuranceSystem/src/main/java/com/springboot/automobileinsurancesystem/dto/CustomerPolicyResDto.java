package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustomerPolicyResDto(
        long id,
        BigDecimal sum_insured,
        LocalDate start_date,
        int no_of_years,
        LocalDate expiry_date,
        PolicyType policyType,
        String customerName,
        String officerName,
        String model,
        VehicleClass vehicleClass
) {
}
