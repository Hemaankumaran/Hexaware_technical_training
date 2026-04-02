package com.springboot.automobileinsurancesystem.dto;

public record FilterReqDto(
        String policy_type,
        String vehicle_class
) {
}

// keep as string so to filter dynamically as anyone or both can be null