package com.springboot.automobileinsurancesystem.mapper;

import com.springboot.automobileinsurancesystem.dto.CustomerSignUpDto;
import com.springboot.automobileinsurancesystem.dto.OfficerSignUpDto;
import com.springboot.automobileinsurancesystem.model.User;

public class UserMapper {

    public static User toEntity(CustomerSignUpDto customerSignUpDto){
        User user = new User();
        user.setUsername(customerSignUpDto.username());
        user.setPassword(customerSignUpDto.password());
        return user;
    }

    public static User dtoToEntity(OfficerSignUpDto officerSignUpDto){
        User user = new User();
        user.setUsername(officerSignUpDto.username());
        user.setPassword(officerSignUpDto.password());
        return user;
    }
}