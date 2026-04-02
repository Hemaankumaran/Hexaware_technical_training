package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.model.Policy;

import java.math.BigDecimal;
import java.util.List;

public record PolicyPageResDto(
        List<Policy> data,
        long total_records,
        int total_pages
) {
}
