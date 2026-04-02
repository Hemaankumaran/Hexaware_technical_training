package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.HCVBodyType;

public record HCVResDto(
        long id,
        HCVBodyType hcvBodyType,
        String permit_number,
        int max_load,
        String reg_no,
        FuelType fuelType
) {
}
