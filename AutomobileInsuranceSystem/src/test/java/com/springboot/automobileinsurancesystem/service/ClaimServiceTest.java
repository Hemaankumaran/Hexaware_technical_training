package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.ClaimResDto;
import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
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
}
