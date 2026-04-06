package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.OfficerResDto;
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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    public OfficerResDto getById(long id) {
        Officer officer = officerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid officer id.."));
        return OfficerMapper.toDto(officer);
    }

    public List<OfficerResDto> getByCustomerId(String username) {
        // get customer by username
        Customer customer = customerService.getByUsername(username);

        // validate
        customerService.getById(customer.getId());

        List<Officer> list = officerRepo.getByCustomerId(customer.getId());

        return list.stream()
                .map(OfficerMapper :: toDto).toList();
    }

    // Filter api
    public List<OfficerResDto> getByDesignation(OfficerDesignation designation) {
        List<Officer> list = officerRepo.getByDesignation(designation);

        return list.stream()
                .map(OfficerMapper :: toDto).toList();
    }

    public Officer getByUsername(String username) {
        return officerRepo.getByUsername(username);
    }

    public void deleteOfficerById(Long officerId) {
        // get obj
        Officer officer = getByIdEntity(officerId);

        log.atLevel(Level.WARN).log("Changing the status of officer to inactive..!");
        // change to INACTIVE
        officer.setOfficerDesignation(OfficerDesignation.INACTIVE);

        // save
        officerRepo.save(officer);
    }

    public Officer getByIdEntity(Long officerId) {
        return officerRepo.findById(officerId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid officer id.."));
    }
}