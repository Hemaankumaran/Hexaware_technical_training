package com.springboot.automobileinsurancesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminReqDto(
        @NotNull
        @NotBlank
        String username,
        @NotNull
        @NotBlank
        String password
) {
}
