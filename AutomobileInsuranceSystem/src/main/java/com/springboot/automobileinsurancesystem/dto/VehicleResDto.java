package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;

public record VehicleResDto(
        long id,
        String reg_no,
        String model,
        FuelType fuelType,
        VehicleClass vehicleClass
) {
}
