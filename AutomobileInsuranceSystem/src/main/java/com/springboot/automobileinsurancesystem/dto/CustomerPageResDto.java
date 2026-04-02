package com.springboot.automobileinsurancesystem.dto;

import java.util.List;

public record CustomerPageResDto(
        List<CustomerResDto> data,
        long total_records,
        int total_pages
) {
}
