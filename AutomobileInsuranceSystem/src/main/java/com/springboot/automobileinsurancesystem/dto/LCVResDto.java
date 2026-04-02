package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.LCVBodyType;

public record LCVResDto(
        long id,
        LCVBodyType lcvBodyType,
        int seating_cap,
        int max_load,
        String reg_no,
        FuelType fuelType
) {
}
