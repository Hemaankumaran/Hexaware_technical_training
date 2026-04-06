package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.PaymentResDto;
import com.springboot.automobileinsurancesystem.enums.PaymentStatus;
import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.*;
import com.springboot.automobileinsurancesystem.repository.PaymentRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepo paymentRepo;

    @Test
    public void getByIdWhenExists(){
        // check for null service
        Assertions.assertNotNull(paymentService);

        // data
        Customer customer = new Customer();
        customer.setName("Cena");
        Vehicle vehicle = new Vehicle();
        vehicle.setReg_no("JE303N24");
        vehicle.setVehicleClass(VehicleClass.HCV);
        Policy policy = new Policy();
        policy.setPolicyType(PolicyType.THIRD_PARTY);
        CustomerPolicy cp = new CustomerPolicy();
        cp.setCustomer(customer);
        cp.setPolicy(policy);
        cp.setVehicle(vehicle);

        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setAmt(BigDecimal.valueOf(20000));
        payment.setPayment_date(Instant.parse("2024-11-20T15:30:00Z"));
        payment.setCustomerPolicy(cp);

        PaymentResDto dto = new PaymentResDto(
                payment.getCustomerPolicy().getCustomer().getName(),
                payment.getCustomerPolicy().getVehicle().getReg_no(),
                payment.getCustomerPolicy().getVehicle().getVehicleClass(),
                payment.getPaymentStatus(),
                payment.getAmt(),
                payment.getPayment_date(),
                payment.getCustomerPolicy().getPolicy().getPolicyType()
        );

        // mock
        Mockito.when(paymentRepo.findById(5L)).thenReturn(Optional.of(payment));

        // assert
        Assertions.assertEquals(dto, paymentService.getById(5L));
    }

    @Test
    public void getByIdWhenNotExists(){
        // mock
        Mockito.when(paymentRepo.findById(5L)).thenReturn(Optional.empty());

        // assert
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> paymentService.getById(5L));
        Assertions.assertEquals("Invalid Payment id..", e.getMessage());

        // verify
        Mockito.verify(paymentRepo, Mockito.times(1)).findById(5L);
    }
}
