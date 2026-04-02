package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResDto(
        String customer_name,
        String reg_no,
        VehicleClass vehicleClass,
        PaymentStatus paymentStatus,
        BigDecimal amt,
        Instant payment_date,
        PolicyType policyType
) {
}
