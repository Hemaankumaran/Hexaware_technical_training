package com.springboot.automobileinsurancesystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerSignUpDto(
        @NotNull
        @NotBlank
        String customer_name,
        @Email
        @NotNull
        String email,
        @NotNull
        @NotBlank
        String contact,
        @NotNull
        @NotBlank
        String license_number,
        String aadhaar_number,
        @Size(min = 3, max = 15)
        String username,
        String password
) {
}
