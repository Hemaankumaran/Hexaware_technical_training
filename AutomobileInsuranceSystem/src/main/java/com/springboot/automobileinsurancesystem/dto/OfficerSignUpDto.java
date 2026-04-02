package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;

public record OfficerSignUpDto(
        String name,
        String contact,
        OfficerDesignation officerDesignation,
        String username,
        String password
) {
}
