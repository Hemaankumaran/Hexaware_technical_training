package com.springboot.automobileinsurancesystem.mapper;

import com.springboot.automobileinsurancesystem.dto.CustomerResDto;
import com.springboot.automobileinsurancesystem.dto.CustomerSignUpDto;
import com.springboot.automobileinsurancesystem.model.Customer;

public class CustomerMapper {

    public static CustomerResDto toDto(Customer customer){
        return new CustomerResDto(
                customer.getId(),
                customer.getName(),
                customer.getContact(),
                customer.getLicense_number()
        );
    }

    public static Customer toEntity(CustomerSignUpDto customerSignUpDto){
        Customer customer = new Customer();
        customer.setName(customerSignUpDto.customer_name());
        customer.setEmail(customerSignUpDto.email());
        customer.setContact(customerSignUpDto.contact());
        customer.setLicense_number(customerSignUpDto.license_number());
        return customer;
    }
}
