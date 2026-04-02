package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.FilterReqDto;
import com.springboot.automobileinsurancesystem.dto.PolicyPageResDto;
import com.springboot.automobileinsurancesystem.dto.SuggestionReqDto;
import com.springboot.automobileinsurancesystem.dto.SuggestionResDto;
import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.Policy;
import com.springboot.automobileinsurancesystem.repository.PolicyRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

@Service
@AllArgsConstructor
public class PolicyService {
    private final PolicyRepo policyRepo;

    public void addPolicy(@Valid Policy policy) {
        policyRepo.save(policy);
    }

    public PolicyPageResDto getAllPolicies(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Policy> customerPage = policyRepo.findAll(pageable);
        List<Policy> list = customerPage.stream().toList();
        return new PolicyPageResDto(
                list,
                customerPage.getTotalElements(),
                customerPage.getTotalPages()
        );
    }

    public Policy getById(long policyId) {
        return policyRepo.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid policy id.."));
    }

    public List<Policy> getByFilter(FilterReqDto filterReqDto) {
        // if both are null - no filter
        if(filterReqDto.policy_type() == null && filterReqDto.vehicle_class() == null)
            return List.of();

        // if not null or blank assign to their enums
        PolicyType policyType = filterReqDto.policy_type() != null && !filterReqDto.policy_type().isBlank() ?
                PolicyType.valueOf(filterReqDto.policy_type()): null;
        VehicleClass vehicleClass = filterReqDto.vehicle_class() != null && !filterReqDto.vehicle_class().isBlank() ?
                VehicleClass.valueOf(filterReqDto.vehicle_class()): null;

        // pass to repo
        return policyRepo.getByFilter(policyType, vehicleClass);
    }

    public List<SuggestionResDto> getSuggestions(SuggestionReqDto dto) {
        // Get age of vehicle
        int age = Year.now().getValue() - dto.mfg_year().getValue();

        // Get all plans for this vehicle's class
        List<Policy> policyList = getByFilter(new FilterReqDto(null, dto.vehicleClass()));

        return policyList.stream()
                .sorted(Comparator.comparingInt((Policy p) -> {
                    // if age > 7 -> prioritize Third Party
                    if(age > 7 && p.getPolicyType() == PolicyType.THIRD_PARTY) return 1;
                    // if age < 3 -> prioritize Comprehensive
                    if(age < 3 && p.getPolicyType() == PolicyType.COMPREHENSIVE) return 1;
                    return 2;
                }))
                .map(policy -> {
                    // calculate idv for policies
                    Map<String, BigDecimal> map = calculateInsuranceMathForSuggestion(
                            dto.originalShowroomPrice(),
                            dto.mfg_year(),
                            policy);

                    // return as a dto for clean display
                    return new SuggestionResDto(
                            policy.getId(),
                            policy.getPolicy_name(),
                            policy.getDescription(),
                            map.get("sumInsured"),
                            map.get("premium"),
                            policy.getPolicyType()
                    );
                }).toList();
    }

    private Map<String, BigDecimal> calculateInsuranceMathForSuggestion(BigDecimal price, Year year, Policy policy) {
        int age = Year.now().getValue() - year.getValue();

        // Depreciation Logic
        double depRate = Math.min(age * 0.10, 0.50);

        // IDV = Price * (1 - Dep)
        BigDecimal idv = price.multiply(BigDecimal.valueOf(1 - depRate));

        // Premium = Base + (IDV * 2% risk) [Insured Declared Value]
        BigDecimal riskFactor = idv.multiply(new BigDecimal("0.02"));
        BigDecimal totalPremium = policy.getBase_premium().add(riskFactor);

        return Map.of("sumInsured", idv, "premium", totalPremium);
    }
}
