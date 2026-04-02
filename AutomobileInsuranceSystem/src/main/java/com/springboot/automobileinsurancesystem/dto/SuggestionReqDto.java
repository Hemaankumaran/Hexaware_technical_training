package com.springboot.automobileinsurancesystem.dto;

import java.math.BigDecimal;
import java.time.Year;

public record SuggestionReqDto(
        String vehicleClass,
        Year mfg_year,
        BigDecimal originalShowroomPrice
) {
}
