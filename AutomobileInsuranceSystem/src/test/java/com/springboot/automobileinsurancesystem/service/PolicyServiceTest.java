package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.SuggestionReqDto;
import com.springboot.automobileinsurancesystem.dto.SuggestionResDto;
import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.Policy;
import com.springboot.automobileinsurancesystem.repository.PolicyRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTest {
    @InjectMocks
    private PolicyService policyService;
    @Mock
    private PolicyRepo policyRepo;

    @Test
    public void getAllPoliciesTest(){
        // data
        Policy p1 = new Policy();
        p1.setPolicy_name("test1");
        p1.setPolicyType(PolicyType.THIRD_PARTY);
        p1.setSum_insured(BigDecimal.valueOf(100000));
        p1.setDescription("Tester");
        p1.setVehicleClass(VehicleClass.ICV);
        p1.setBase_premium(BigDecimal.valueOf(20000));
        p1.setDuration_years(5);

        Policy p2 = new Policy();
        p2.setPolicy_name("test1");
        p2.setPolicyType(PolicyType.THIRD_PARTY);
        p2.setSum_insured(BigDecimal.valueOf(100000));
        p2.setDescription("Tester");
        p2.setVehicleClass(VehicleClass.ICV);
        p2.setBase_premium(BigDecimal.valueOf(20000));
        p2.setDuration_years(5);
        List<Policy> list = List.of(p1, p2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Policy> policyPage = new PageImpl<>(list);

        // mock
        Mockito.when(policyRepo.findAll(pageable)).thenReturn(policyPage);

        // assert
        Assertions.assertEquals(2, policyService.getAllPolicies(2, 0).data().size());
    }

    @Test
    public void getByIdWhenExists(){
        // data
        Policy p1 = new Policy();
        p1.setPolicy_name("test1");
        p1.setPolicyType(PolicyType.THIRD_PARTY);
        p1.setSum_insured(BigDecimal.valueOf(100000));
        p1.setDescription("Tester");
        p1.setVehicleClass(VehicleClass.ICV);
        p1.setBase_premium(BigDecimal.valueOf(20000));
        p1.setDuration_years(5);

        Policy p2 = new Policy();
        p2.setPolicy_name("test1");
        p2.setPolicyType(PolicyType.THIRD_PARTY);
        p2.setSum_insured(BigDecimal.valueOf(100000));
        p2.setDescription("Tester");
        p2.setVehicleClass(VehicleClass.ICV);
        p2.setBase_premium(BigDecimal.valueOf(20000));
        p2.setDuration_years(5);

        // mock
        Mockito.when(policyRepo.findById(7L)).thenReturn(Optional.of(p1));

        // assert
        Assertions.assertEquals(p1, policyService.getById(7));
        Assertions.assertNotEquals(p2, policyService.getById(7));

        // verify
        Mockito.verify(policyRepo, Mockito.times(2)).findById(7L);
    }

    @Test
    public void getByIdWhenNotExists(){
        // mock
        Mockito.when(policyRepo.findById(9L)).thenReturn(Optional.empty());
        // assert
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> policyService.getById(9));
        Assertions.assertEquals("Invalid policy id..", e.getMessage());
    }

    @Test
    public void getSuggestions(){
        // data
        SuggestionReqDto reqDto = new SuggestionReqDto("ICV", Year.of(2025), BigDecimal.valueOf(2500000));

        Policy p1 = new Policy();
        p1.setPolicy_name("test1");
        p1.setPolicyType(PolicyType.THIRD_PARTY);
        p1.setSum_insured(BigDecimal.valueOf(100000));
        p1.setDescription("Tester");
        p1.setVehicleClass(VehicleClass.ICV);
        p1.setBase_premium(BigDecimal.valueOf(20000));
        p1.setDuration_years(5);

        Policy p2 = new Policy();
        p2.setPolicy_name("test1");
        p2.setPolicyType(PolicyType.THIRD_PARTY);
        p2.setSum_insured(BigDecimal.valueOf(100000));
        p2.setDescription("Tester");
        p2.setVehicleClass(VehicleClass.ICV);
        p2.setBase_premium(BigDecimal.valueOf(20000));
        p2.setDuration_years(5);
        List<Policy> list = List.of(p1, p2);

        List<SuggestionResDto> resDto = list.stream()
                .sorted(Comparator.comparingInt((Policy p) -> {
                    int age = Year.now().getValue() - reqDto.mfg_year().getValue();
                    // if age > 7 -> prioritize Third Party
                    if(age > 7 && p.getPolicyType() == PolicyType.THIRD_PARTY) return 1;
                    // if age < 3 -> prioritize Comprehensive
                    if(age < 3 && p.getPolicyType() == PolicyType.COMPREHENSIVE) return 1;
                    return 2;
                }))
                .map(policy -> {
                    // calculate idv for policies
                    Map<String, BigDecimal> map = policyService.calculateInsuranceMathForSuggestion(
                            reqDto.originalShowroomPrice(),
                            reqDto.mfg_year(),
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

        // mock
        Mockito.when(policyRepo.getByFilter(null, VehicleClass.ICV)).thenReturn(list);

        // assert
        Assertions.assertEquals(resDto, policyService.getSuggestions(reqDto));
    }
}
