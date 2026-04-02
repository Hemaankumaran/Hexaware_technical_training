package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;

import java.util.List;

public record VehicleByCustomerDto(
        long customer_id,
        String customer_name,
        String license_number,
        List<VehicleResDto> data
) {
}
