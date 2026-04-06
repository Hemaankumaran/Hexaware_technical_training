package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.ClaimReqDto;
import com.springboot.automobileinsurancesystem.dto.ClaimResDto;
import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import com.springboot.automobileinsurancesystem.model.Claim;
import com.springboot.automobileinsurancesystem.service.ClaimService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/claim")
public class ClaimController { // add, getById, getByClaimStatus

    private final ClaimService claimService;

    // initiate claim
    @PostMapping("/add/{customerPolicyId}") // Authenticated
    public ResponseEntity<Map<String, Object>> addClaim(@Valid @RequestBody ClaimReqDto claimReqDto,
                                                        @PathVariable Long customerPolicyId){
        claimService.addClaim(claimReqDto, customerPolicyId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! claim initiated..");
        return ResponseEntity
                .status(HttpStatus.CREATED).body(map);
    }

    // officer update claim as UNDER_REVIEW
    @PutMapping("/update/status/{claimId}") // OFFICER
    public ResponseEntity<Map<String, Object>> updateClaimStatus(@PathVariable Long claimId){
        claimService.updateClaimStatus(claimId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Updated Claim Status - UNDER_REVIEW");
        return ResponseEntity
                .status(HttpStatus.OK).body(map);
    }

    // officer approves claim
    @PutMapping("/approve/status/{claimId}/{damagePercent}/{deductible}") // OFFICER
    public ResponseEntity<Map<String, Object>> approveClaim(@PathVariable Long claimId,
                                                            @PathVariable int damagePercent,
                                                            @PathVariable BigDecimal deductible){
        claimService.approveClaim(claimId, damagePercent, deductible);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Approve Claim");
        return ResponseEntity
                .status(HttpStatus.OK).body(map);
    }

    // officer rejects claim
    @PutMapping("/reject/{claimId}") // OFFICER
    public ResponseEntity<Map<String, Object>> rejectClaim(@PathVariable Long claimId){
        claimService.rejectClaim(claimId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Rejected Claim");
        return ResponseEntity
                .status(HttpStatus.OK).body(map);
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
