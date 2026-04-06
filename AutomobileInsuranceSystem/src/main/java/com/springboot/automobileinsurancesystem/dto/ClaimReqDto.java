package com.springboot.automobileinsurancesystem.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClaimReqDto(
        @NotNull
        LocalDate incident_date,
        @NotNull
        String claim_desc
) {
}
