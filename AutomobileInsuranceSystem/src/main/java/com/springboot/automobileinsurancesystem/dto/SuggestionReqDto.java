package com.springboot.automobileinsurancesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Year;

public record SuggestionReqDto(
        @NotNull
        @NotBlank
        String vehicleClass,
        @NotNull
        Year mfg_year,
        @NotNull
        BigDecimal originalShowroomPrice
) {
}
