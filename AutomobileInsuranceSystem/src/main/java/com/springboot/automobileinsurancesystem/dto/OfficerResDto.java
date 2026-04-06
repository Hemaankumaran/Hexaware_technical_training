package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;

public record OfficerResDto(
        Long officer_id,
        String officer_name,
        OfficerDesignation officerDesignation,
        String contact
) {
}
