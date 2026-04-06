package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.PaymentResDto;
import com.springboot.automobileinsurancesystem.enums.Role;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.exceptions.UpdatePermissionException;
import com.springboot.automobileinsurancesystem.model.*;
import com.springboot.automobileinsurancesystem.repository.PaymentRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final CustomerPolicyService customerPolicyService;
    private final CustomerService customerService;
    private final OfficerService officerService;
    private final UserService userService;

    public void addPayment(@Valid Payment payment, Long customerPolicyId, String username) {
        // validate policy and get it
        CustomerPolicy customerPolicy = customerPolicyService.getByIdEntity(customerPolicyId);
        User user = (User) userService.loadUserByUsername(username);
        Customer customer = customerService.getByUsername(username);
        Officer officer = officerService.getByUsername(username);

        log.atLevel(Level.INFO).log("Checking the permissions of user..");
        // check the crt permission
        if(user.getRole() == Role.CUSTOMER){
            if(customerPolicy.getCustomer().getUser().getId() !=  customer.getUser().getId())
                throw new UpdatePermissionException("You cannot do other customer payments!");
        }
        if(user.getRole() == Role.OFFICER){
            if(customerPolicy.getOfficer().getUser().getId() !=  officer.getUser().getId())
                throw new UpdatePermissionException("This customer does not belong to you!");
        }
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
