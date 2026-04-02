package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.PaymentResDto;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.CustomerPolicy;
import com.springboot.automobileinsurancesystem.model.Payment;
import com.springboot.automobileinsurancesystem.repository.PaymentRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final CustomerPolicyService customerPolicyService;

    public void addPayment(@Valid Payment payment, Long customerPolicyId) {
        // validate policy and get it
        CustomerPolicy customerPolicy = customerPolicyService.getByIdEntity(customerPolicyId);
        // inject policy
        payment.setCustomerPolicy(customerPolicy);
        // save it
        paymentRepo.save(payment);
    }

    public PaymentResDto getById(Long paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Payment id.."));
        return new PaymentResDto(
                payment.getCustomerPolicy().getCustomer().getName(),
                payment.getCustomerPolicy().getVehicle().getReg_no(),
                payment.getCustomerPolicy().getVehicle().getVehicleClass(),
                payment.getPaymentStatus(),
                payment.getAmt(),
                payment.getPayment_date(),
                payment.getCustomerPolicy().getPolicy().getPolicyType()
        );
    }
}
