package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.OfficerSignUpDto;
import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;
import com.springboot.automobileinsurancesystem.model.Officer;
import com.springboot.automobileinsurancesystem.service.OfficerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/officer")
@AllArgsConstructor
public class OfficerController { // add, getById, getByCustomerId, officerSignUp

    private final OfficerService officerService;

    @PostMapping("/add") // ADMIN
    public ResponseEntity<Map<String, Object>> addOfficer(@Valid @RequestBody Officer officer){
        officerService.addOfficer(officer);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! officer added");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map);
    }

    @PostMapping("/signup") // Authenticated
    public ResponseEntity<Map<String, Object>> addOfficerSignUp(@Valid @RequestBody OfficerSignUpDto officerSignUpDto){
        officerService.addOfficerSignUp(officerSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/get/{id}") // ADMIN
    public Officer getById(@PathVariable long id){
        return officerService.getById(id);
    }

    @GetMapping("/get/customer") // CUSTOMER
    public List<Officer> getByCustomerId(Principal principal){
        return officerService.getByCustomerId(principal.getName());
    }

    // Filter api
    @GetMapping("/get/designation") // OFFICER, ADMIN
    public List<Officer> getByDesignation(@RequestParam OfficerDesignation designation){
        return officerService.getByDesignation(designation);
    }
}

// officer physical review