package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.ClaimResDto;
import com.springboot.automobileinsurancesystem.enums.*;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.ClaimMapper;
import com.springboot.automobileinsurancesystem.model.*;
import com.springboot.automobileinsurancesystem.repository.ClaimRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClaimServiceTest {

    @InjectMocks
    private ClaimService claimService;
    @Mock
    private ClaimRepo claimRepo;

    @Test
    public void getByIdTestWhenExists(){
        // check null for service
        Assertions.assertNotNull(claimService);

        // data
        Claim claim = new Claim();

        Customer customer = new Customer();
        customer.setName("Harry");

        Vehicle vehicle = new Vehicle();
        vehicle.setReg_no("308SF3948");
        vehicle.setVehicleClass(VehicleClass.HCV);

        Policy policy = new Policy();
        policy.setPolicyType(PolicyType.THIRD_PARTY);

        CustomerPolicy customerPolicy = new CustomerPolicy();
        customerPolicy.setSum_insured(BigDecimal.valueOf(100000));
        customerPolicy.setCustomer(customer);
        customerPolicy.setVehicle(vehicle);
        customerPolicy.setPolicy(policy);

        claim.setCustomerPolicy(customerPolicy);
        claim.setIncident_date(LocalDate.parse("2025-09-23"));
        claim.setClaim_amt(BigDecimal.valueOf(90000));
        claim.setClaimStatus(ClaimStatus.INITIATED);

        ClaimResDto claimResDto = new ClaimResDto(
                claim.getCustomerPolicy().getCustomer().getName(),
                claim.getCustomerPolicy().getVehicle().getReg_no(),
                claim.getCustomerPolicy().getVehicle().getVehicleClass(),
                claim.getCustomerPolicy().getPolicy().getPolicyType(),
                claim.getIncident_date(),
                claim.getClaim_amt(),
                claim.getClaimStatus(),
                claim.getCustomerPolicy().getSum_insured()
        );

        // mock
        Mockito.when(claimRepo.findById(5L)).thenReturn(Optional.of(claim));

        // assert
        Assertions.assertEquals(claimResDto, claimService.getById(5L));

        // verify
        Mockito.verify(claimRepo, Mockito.times(1)).findById(5L);
    }

    @Test
    public void getByIdTestWhenNotExists(){
        // mock
        Mockito.when(claimRepo.findById(5L)).thenReturn(Optional.empty());
        // assert
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> claimService.getById(5L));
        // verify
        Mockito.verify(claimRepo, Mockito.times(1)).findById(5L);
    }

    @Test
    public void getByClaimStatusTestWhenFound(){
        // data
        Claim claim1 = new Claim();
        claim1.setIncident_date(LocalDate.parse("2026-08-12"));
        claim1.setClaim_desc("Testing...");
        CustomerPolicy cp1 = new CustomerPolicy();
        cp1.setPremium(BigDecimal.valueOf(20000));
        cp1.setSum_insured(BigDecimal.valueOf(100000));
        Customer customer1 = new Customer();
        customer1.setName("Tester..");
        customer1.setContact("298843923");
        customer1.setLicense_number("NF3239HD");
        Officer officer1 = new Officer();
        officer1.setName("Officer1");
        officer1.setOfficerDesignation(OfficerDesignation.SALES_SUPPORT);
        officer1.setContact("9634578345");
        Policy policy1 = new Policy();
        policy1.setPolicy_name("new policy");
        policy1.setDescription("testing");
        policy1.setBase_premium(BigDecimal.valueOf(20000));
        policy1.setSum_insured(BigDecimal.valueOf(100000));
        policy1.setDuration_years(8);
        policy1.setVehicleClass(VehicleClass.HCV);
        policy1.setPolicyType(PolicyType.THIRD_PARTY);
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setReg_no("DH28AO2083");
        vehicle1.setFuelType(FuelType.ELECTRIC);
        vehicle1.setVehicleClass(VehicleClass.HCV);
        cp1.setCustomer(customer1);
        cp1.setOfficer(officer1);
        cp1.setVehicle(vehicle1);
        cp1.setPolicy(policy1);
        claim1.setCustomerPolicy(cp1);

        Claim claim2 = new Claim();
        claim2.setIncident_date(LocalDate.parse("2026-08-12"));
        claim2.setClaim_desc("Testing...");
        CustomerPolicy cp2 = new CustomerPolicy();
        cp2.setPremium(BigDecimal.valueOf(20000));
        cp2.setSum_insured(BigDecimal.valueOf(100000));
        Customer customer2 = new Customer();
        customer2.setName("Tester..");
        customer2.setContact("298843923");
        customer2.setLicense_number("NF3239HD");
        Officer officer2 = new Officer();
        officer2.setName("Officer1");
        officer2.setOfficerDesignation(OfficerDesignation.SALES_SUPPORT);
        officer2.setContact("9634578345");
        Policy policy2 = new Policy();
        policy2.setPolicy_name("new policy");
        policy2.setDescription("testing");
        policy2.setBase_premium(BigDecimal.valueOf(20000));
        policy2.setSum_insured(BigDecimal.valueOf(100000));
        policy2.setDuration_years(8);
        policy2.setVehicleClass(VehicleClass.HCV);
        policy2.setPolicyType(PolicyType.THIRD_PARTY);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setReg_no("DH28AO2083");
        vehicle2.setFuelType(FuelType.ELECTRIC);
        vehicle2.setVehicleClass(VehicleClass.HCV);
        cp2.setCustomer(customer2);
        cp2.setOfficer(officer2);
        cp2.setVehicle(vehicle2);
        cp2.setPolicy(policy2);
        claim2.setCustomerPolicy(cp2);

        List<Claim> list = List.of(claim1, claim2);
        List<ClaimResDto> dtoList = list.stream()
                .map(ClaimMapper :: toDto).toList();
        // mock
        Mockito.when(claimRepo.getByClaimStatus((ClaimStatus.APPROVED))).thenReturn(list);

        // assert
        Assertions.assertEquals(dtoList, claimService.getByClaimStatus(ClaimStatus.APPROVED));
    }

    @Test
    public void getByClaimStatusWhenNotFound(){
        // data
        List<Claim> list = List.of();
        List<ClaimResDto> listDto = List.of();
        // mock
        Mockito.when(claimRepo.getByClaimStatus(ClaimStatus.REJECTED)).thenReturn(list);

        // assert
        Assertions.assertEquals(listDto, claimService.getByClaimStatus(ClaimStatus.REJECTED));

    }
}
