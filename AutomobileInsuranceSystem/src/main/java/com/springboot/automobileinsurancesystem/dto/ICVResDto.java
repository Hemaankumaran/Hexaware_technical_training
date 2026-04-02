package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.ICVBodyType;

public record ICVResDto(
        long id,
        ICVBodyType icvBodyType,
        String permit_number,
        int max_load,
        String reg_no,
        FuelType fuelType
) {
}
