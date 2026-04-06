package com.springboot.automobileinsurancesystem.mapper;

import com.springboot.automobileinsurancesystem.dto.OfficerResDto;
import com.springboot.automobileinsurancesystem.dto.OfficerSignUpDto;
import com.springboot.automobileinsurancesystem.model.Officer;

public class OfficerMapper {

    public static Officer toEntity(OfficerSignUpDto officerSignUpDto){
        Officer officer = new Officer();
        officer.setName(officerSignUpDto.name());
        officer.setContact(officerSignUpDto.contact());
        officer.setOfficerDesignation(officerSignUpDto.officerDesignation());
        return officer;
    }

    public static OfficerResDto toDto(Officer officer){
        return new OfficerResDto(
                officer.getId(),
                officer.getName(),
                officer.getOfficerDesignation(),
                officer.getContact()
        );
    }
}
