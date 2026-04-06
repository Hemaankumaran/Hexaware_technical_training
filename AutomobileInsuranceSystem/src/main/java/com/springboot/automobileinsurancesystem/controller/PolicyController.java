package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.FilterReqDto;
import com.springboot.automobileinsurancesystem.dto.PolicyPageResDto;
import com.springboot.automobileinsurancesystem.dto.SuggestionReqDto;
import com.springboot.automobileinsurancesystem.dto.SuggestionResDto;
import com.springboot.automobileinsurancesystem.model.Policy;
import com.springboot.automobileinsurancesystem.service.PolicyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/policy")
public class PolicyController { // add, getAll, getById, getByFilter, getSuggestions

    private final PolicyService policyService;

    @PostMapping("/add") // OFFICER, ADMIN
    public ResponseEntity<Map<String, Object>> addPolicy(@Valid @RequestBody Policy policy){
        policyService.addPolicy(policy);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! policy added");
        return ResponseEntity
                .status(HttpStatus.CREATED).body(map);
    }


    @GetMapping("/getall") // permitAll
    public PolicyPageResDto getAllPolicies(@RequestParam(value = "size", required = false, defaultValue = "5")int size,
                                           @RequestParam(value = "page", required = false, defaultValue = "0")int page){
        return policyService.getAllPolicies(size, page);
    }

    @GetMapping("/get/{policyId}") // Authenticated
    public Policy getById(@PathVariable long policyId){
        return policyService.getById(policyId);
    }

    // @RequestBody and @GetMapping clashes, so use @PostMapping
    @PostMapping("/get/filter") // permitAll
    public List<Policy> getByFilter(@RequestBody FilterReqDto filterReqDto){
        return policyService.getByFilter(filterReqDto);
    }

    @GetMapping("/suggestions") // permitAll
    public List<SuggestionResDto> getSuggestions(@Valid @RequestBody SuggestionReqDto suggestionReqDto) {
        return policyService.getSuggestions(suggestionReqDto);
    }
}
