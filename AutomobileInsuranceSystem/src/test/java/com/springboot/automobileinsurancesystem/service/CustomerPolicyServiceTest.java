package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.CustomerPolicyResDto;
import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;
import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.CustomerPolicyMapper;
import com.springboot.automobileinsurancesystem.model.*;
import com.springboot.automobileinsurancesystem.repository.CustomerPolicyRepo;
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
public class CustomerPolicyServiceTest {
    @InjectMocks
    private CustomerPolicyService customerPolicyService;
    @Mock
    private CustomerPolicyRepo customerPolicyRepo;

    @Test
    public void getQuoteWhenExists(){
        // check null service
        Assertions.assertNotNull(customerPolicyService);

        // data
        Customer customer = new Customer();
        customer.setName("Dongle");
        customer.setContact("394723493");
        customer.setLicense_number("DI3JD39N4");
        Officer officer = new Officer();
        officer.setName("Uan");
        officer.setOfficerDesignation(OfficerDesignation.GENERAL_SUPPORT);
        officer.setContact("2984839023");
        Vehicle vehicle = new Vehicle();
        vehicle.setReg_no("D2ND83N");
        vehicle.setFuelType(FuelType.PETROL);
        vehicle.setVehicleClass(VehicleClass.ICV);
        Policy policy = new Policy();
        policy.setPolicy_name("test policy");
        policy.setDescription("mock testing");
        policy.setBase_premium(BigDecimal.valueOf(10000));
        policy.setSum_insured(BigDecimal.valueOf(1200000));
        policy.setDuration_years(10);
        policy.setVehicleClass(VehicleClass.ICV);
        policy.setPolicyType(PolicyType.COMPREHENSIVE);
        CustomerPolicy cp = new CustomerPolicy();
        cp.setPremium(BigDecimal.valueOf(10000));
        cp.setSum_insured(BigDecimal.valueOf(1200000));
        cp.setCustomer(customer);
        cp.setOfficer(officer);
        cp.setVehicle(vehicle);
        cp.setPolicy(policy);

        // mock
        Mockito.when(customerPolicyRepo.findById(3L)).thenReturn(Optional.of(cp));

        // assert
        Assertions.assertEquals(cp, customerPolicyService.getQuote(3L));
    }

    @Test
    public void getQuoteWhenNotExists(){
        // mock
        Mockito.when(customerPolicyRepo.findById(3L)).thenReturn(Optional.empty());

        // assert
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class,
                                () -> customerPolicyService.getQuote(3L));
        Assertions.assertEquals("Invalid customer_policy id..", e.getMessage());
    }

    @Test
    public void getByCustomerId(){
        // data
        Customer customer1 = new Customer();
        customer1.setName("Foster");
        Officer officer1 = new Officer();
        officer1.setName("Uan");
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setModel("Honda");
        vehicle1.setVehicleClass(VehicleClass.ICV);
        Policy policy1 = new Policy();
        policy1.setPolicy_name("test policy");
        CustomerPolicy cp1 = new CustomerPolicy();
        cp1.setSum_insured(BigDecimal.valueOf(1200000));
        cp1.setStart_date(LocalDate.parse("2024-03-20"));
        cp1.setNo_of_years(10);
        cp1.setExpiry_date(LocalDate.parse("2034-03-20"));
        cp1.setCustomer(customer1);
        cp1.setOfficer(officer1);
        cp1.setVehicle(vehicle1);
        cp1.setPolicy(policy1);

        Customer customer2 = new Customer();
        customer2.setName("Foster");
        Officer officer2 = new Officer();
        officer2.setName("Williams");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setModel("Hyundai");
        vehicle2.setVehicleClass(VehicleClass.ICV);
        Policy policy2 = new Policy();
        policy2.setPolicy_name("testing policy");
        CustomerPolicy cp2 = new CustomerPolicy();
        cp2.setSum_insured(BigDecimal.valueOf(1200000));
        cp2.setStart_date(LocalDate.parse("2024-03-20"));
        cp2.setNo_of_years(10);
        cp2.setExpiry_date(LocalDate.parse("2034-03-20"));
        cp2.setCustomer(customer2);
        cp2.setOfficer(officer2);
        cp2.setVehicle(vehicle2);
        cp2.setPolicy(policy2);

        List<CustomerPolicy> list = List.of(cp1, cp2);
        List<CustomerPolicyResDto> dtoList = list.stream()
                .map(CustomerPolicyMapper :: toDto).toList();

        // mock
        Mockito.when(customerPolicyRepo.getByCustomerId(8L)).thenReturn(list);

        // assert
        Assertions.assertEquals(dtoList, customerPolicyService.getByCustomerId(8));

        // verify
        Mockito.verify(customerPolicyRepo, Mockito.times(1)).getByCustomerId(8L);
    }
}
