package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.ClaimResDto;
import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.ClaimMapper;
import com.springboot.automobileinsurancesystem.model.Claim;
import com.springboot.automobileinsurancesystem.model.CustomerPolicy;
import com.springboot.automobileinsurancesystem.repository.ClaimRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClaimService {

    private final ClaimRepo claimRepo;
    private final CustomerPolicyService customerPolicyService;

    public void addClaim(@Valid Claim claim, Long customerPolicyId) {
        CustomerPolicy customerPolicy = customerPolicyService.getByIdEntity(customerPolicyId);
        claim.setCustomerPolicy(customerPolicy);

        claimRepo.save(claim);
    }

    public ClaimResDto getById(Long claimId) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid claim id.."));
        return ClaimMapper.toDto(claim);
    }

    public List<ClaimResDto> getByClaimStatus(ClaimStatus claimStatus) {
        List<Claim> list = claimRepo.getByClaimStatus(claimStatus);
        return list.stream()
                .map(ClaimMapper :: toDto)
                .toList();
    }
}
