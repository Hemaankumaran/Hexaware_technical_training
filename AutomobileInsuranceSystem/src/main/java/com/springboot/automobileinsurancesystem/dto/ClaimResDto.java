package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClaimResDto(
        String customer_name,
        String reg_no,
        VehicleClass vehicleClass,
        PolicyType policyType,
        LocalDate incident_date,
        BigDecimal claim_amt,
        ClaimStatus claimStatus,
        BigDecimal sum_insured
) {
}
