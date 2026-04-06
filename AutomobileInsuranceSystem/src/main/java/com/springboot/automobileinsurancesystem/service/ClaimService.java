package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.ClaimReqDto;
import com.springboot.automobileinsurancesystem.dto.ClaimResDto;
import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.ClaimMapper;
import com.springboot.automobileinsurancesystem.model.Claim;
import com.springboot.automobileinsurancesystem.model.CustomerPolicy;
import com.springboot.automobileinsurancesystem.repository.ClaimRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ClaimService {

    private final ClaimRepo claimRepo;
    private final CustomerPolicyService customerPolicyService;

    public void addClaim(@Valid ClaimReqDto claimReqDto, Long customerPolicyId) {
        // get customerPolicy obj
        CustomerPolicy customerPolicy = customerPolicyService.getByIdEntity(customerPolicyId);

        // create claim obj
        Claim claim = new Claim();
        claim.setIncident_date(claimReqDto.incident_date());
        claim.setClaim_desc(claimReqDto.claim_desc());
        claim.setClaimStatus(ClaimStatus.INITIATED);
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

    public void updateClaimStatus(Long claimId) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Claim Id.."));

        claim.setClaimStatus(ClaimStatus.UNDER_REVIEW);

        claimRepo.save(claim);
    }

    public void approveClaim(Long claimId, int damagePercent, BigDecimal deductible) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Claim Id.."));

        // calculate the claim amt
        BigDecimal idv = claim.getCustomerPolicy().getSum_insured();
        claim.setClaim_amt(calculateClaimAmt(idv, damagePercent, deductible));

        claim.setClaimStatus(ClaimStatus.APPROVED);
        claim.setClaim_date(LocalDate.now());

        claimRepo.save(claim);
    }

    private BigDecimal calculateClaimAmt(BigDecimal idv, int damagePercent, BigDecimal deductible) {
        log.atLevel(Level.DEBUG).log("Entering the claim amt calculation method..!");
        // convert damage percent -> damage -> big decimal
        BigDecimal damageRatio = new BigDecimal(damagePercent)
                                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);
        BigDecimal damageValue;

        if(damagePercent >= 75) // give full idv
            damageValue = idv;
        else // give partial idv
            damageValue = idv.multiply(damageRatio);

        // subtract deductible
        BigDecimal total_claim = damageValue.subtract(deductible);

        if(total_claim.longValue() < 0) return BigDecimal.ZERO;
        return total_claim;
    }

    public void rejectClaim(Long claimId) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Claim Id.."));

        claim.setClaimStatus(ClaimStatus.REJECTED);

        claimRepo.save(claim);
    }
}
