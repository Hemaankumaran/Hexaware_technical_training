package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.ClaimResDto;
import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import com.springboot.automobileinsurancesystem.model.Claim;
import com.springboot.automobileinsurancesystem.service.ClaimService;
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
@RequestMapping("/api/claim")
public class ClaimController { // add, getById, getByClaimStatus

    private final ClaimService claimService;

    @PostMapping("/add/{customerPolicyId}") // Authenticated
    public ResponseEntity<Map<String, Object>> addClaim(@Valid @RequestBody Claim claim,
                                      @PathVariable Long customerPolicyId){
        claimService.addClaim(claim, customerPolicyId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! claim added..");
        return ResponseEntity
                .status(HttpStatus.CREATED).body(map);
    }

    @GetMapping("/get/{claimId}") // OFFICER, ADMIN
    public ClaimResDto getById(@PathVariable Long claimId){
        return claimService.getById(claimId);
    }

    // Filter api
    @GetMapping("/get/status/{claimStatus}") // OFFICER, ADMIN
    public List<ClaimResDto> getByClaimStatus(@PathVariable ClaimStatus claimStatus){
        return claimService.getByClaimStatus(claimStatus);
    }
}
