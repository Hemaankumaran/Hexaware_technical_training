package com.springboot.automobileinsurancesystem.dto;

import com.springboot.automobileinsurancesystem.enums.PolicyType;

import java.math.BigDecimal;

public record SuggestionResDto(
        long id,
        String policyName,
        String description,
        BigDecimal estimatedSumInsured, // IDV or SumInsured
        BigDecimal estimatedPremium,    // Calculated Final Price
        PolicyType policyType
) {
}
