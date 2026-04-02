package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.CustomerPageResDto;
import com.springboot.automobileinsurancesystem.dto.CustomerResDto;
import com.springboot.automobileinsurancesystem.dto.CustomerSignUpDto;
import com.springboot.automobileinsurancesystem.enums.Role;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.CustomerMapper;
import com.springboot.automobileinsurancesystem.mapper.UserMapper;
import com.springboot.automobileinsurancesystem.model.Customer;
import com.springboot.automobileinsurancesystem.model.User;
import com.springboot.automobileinsurancesystem.repository.CustomerRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void addCustomer(@Valid Customer customer) {
        customerRepo.save(customer);
    }

    public CustomerPageResDto getAllCustomers(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepo.findAll(pageable);
        List<CustomerResDto> list = customerPage.stream()
                .map(CustomerMapper:: toDto)
                .toList();
        return new CustomerPageResDto(
                list,
                customerPage.getTotalElements(),
                customerPage.getTotalPages()
        );
    }

    public CustomerResDto getById(long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid customer id.."));
        return CustomerMapper.toDto(customer);
    }

    public Customer getByIdEntity(long customerID) {
        return customerRepo.findById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid customer id.."));
    }

    public void addCustomerSignUp(@Valid CustomerSignUpDto customerSignUpDto) {
        // get entities of Customer & User
        Customer customer = CustomerMapper.toEntity(customerSignUpDto);
        User user = UserMapper.toEntity(customerSignUpDto);

        // password encryption
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // add missing fields of user and save user in db
        user.setRole(Role.CUSTOMER);
        user = userService.addUser(user);

        // inject user into customer
        customer.setUser(user);

        // save
        customerRepo.save(customer);
    }

    public Customer getByUsername(String username){
        return customerRepo.getByUsername(username);
    }
}
