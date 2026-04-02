package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.OfficerSignUpDto;
import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;
import com.springboot.automobileinsurancesystem.enums.Role;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.OfficerMapper;
import com.springboot.automobileinsurancesystem.mapper.UserMapper;
import com.springboot.automobileinsurancesystem.model.Customer;
import com.springboot.automobileinsurancesystem.model.Officer;
import com.springboot.automobileinsurancesystem.model.User;
import com.springboot.automobileinsurancesystem.repository.OfficerRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OfficerService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private OfficerRepo officerRepo;

    public void addOfficer(@Valid Officer officer) {
        officerRepo.save(officer);
    }

     public void addOfficerSignUp(@Valid OfficerSignUpDto officerSignUpDto) {
        Officer officer = OfficerMapper.toEntity(officerSignUpDto);

        User user = UserMapper.dtoToEntity(officerSignUpDto);

        user.setRole(Role.OFFICER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userService.addUser(user);

        officer.setUser(user);
        officerRepo.save(officer);
    }

    public Officer getById(long id) {
        return officerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid officer id.."));
    }

    public List<Officer> getByCustomerId(String username) {
        // get customer by username
        Customer customer = customerService.getByUsername(username);

        // validate
        customerService.getById(customer.getId());

        return officerRepo.getByCustomerId(customer.getId());
    }

    public List<Officer> getByDesignation(OfficerDesignation designation) {
        return officerRepo.getByDesignation(designation);
    }
}