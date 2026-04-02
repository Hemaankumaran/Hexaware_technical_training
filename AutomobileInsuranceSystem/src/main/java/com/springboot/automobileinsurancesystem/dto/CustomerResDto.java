package com.springboot.automobileinsurancesystem.dto;

public record CustomerResDto(
        long id,
        String name,
        String contact,
        String license_number
) {
}
