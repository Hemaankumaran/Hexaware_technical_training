package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.PaymentResDto;
import com.springboot.automobileinsurancesystem.model.Payment;
import com.springboot.automobileinsurancesystem.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController { // add, getById

    private final PaymentService paymentService;

    @PostMapping("/add/{customerPolicyId}") // Authenticated // status taken from upi, as success or failed
    public ResponseEntity<Map<String, Object>> addPayment(@Valid @RequestBody Payment payment,
                                                          @PathVariable Long customerPolicyId,
                                                          Principal principal){
        paymentService.addPayment(payment, customerPolicyId, principal.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! payment added");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map);
    }

    @GetMapping("/get/{paymentId}") // OFFICER, ADMIN
    public PaymentResDto getById(@PathVariable Long paymentId){
        return paymentService.getById(paymentId);
    }
}
