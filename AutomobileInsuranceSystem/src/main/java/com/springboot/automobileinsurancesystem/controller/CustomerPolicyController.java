package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.CustomerPolicyResDto;
import com.springboot.automobileinsurancesystem.enums.PolicyStatus;
import com.springboot.automobileinsurancesystem.model.CustomerPolicy;
import com.springboot.automobileinsurancesystem.service.CustomerPolicyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customerpolicy")
public class CustomerPolicyController { // assignPolicyToCustomerByAdmin, buyPolicyByCustomer,
                                        // updatePolicyStatus, updateOfficer, approvePolicy

    private final CustomerPolicyService customerPolicyService;

    @PostMapping("/add/admin/{policyId}/{customerId}/{officerId}/{vehicleId}") // ADMIN
    public ResponseEntity<Map<String, Object>> assignPolicyToCustomerByAdmin(@PathVariable long policyId,
                                                                             @PathVariable long customerId,
                                                                             @PathVariable long officerId,
                                                                             @PathVariable long vehicleId){
        customerPolicyService.assignPolicyToCustomerByAdmin(policyId, customerId, officerId, vehicleId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! policy bought");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map);
    }

    @PostMapping("/add/{policyId}/{officerId}/{vehicleId}") // CUSTOMER
    public ResponseEntity<Map<String, Object>> buyPolicyByCustomer(@PathVariable long policyId,
                                                                   Principal principal,
                                                                   @PathVariable long officerId,
                                                                   @PathVariable long vehicleId){
        customerPolicyService.buyPolicyByCustomer(policyId, principal.getName(), officerId, vehicleId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! policy bought");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map);
    }

    // scheduling the inspection - date cannot be in past (handle in React)
    @PostMapping("/schedule/{inspectionDate}/{customerPolicyId}") // CUSTOMER
    public ResponseEntity<Map<String, Object>> scheduleInspection(@PathVariable LocalDate inspectionDate,
                                                                  @PathVariable Long customerPolicyId,
                                                                  Principal principal){
        customerPolicyService.scheduleInspection(inspectionDate, customerPolicyId, principal.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Inspection Scheduled!");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).body(map);
    }

    // assign officer by Admin - if needed to reassign officer
    @PutMapping("/update/officer/{customerPolicyId}/{officerId}") // ADMIN
    public ResponseEntity<Map<String, Object>> assignOfficer(@PathVariable Long customerPolicyId,
                                                             @PathVariable Long officerId){
        customerPolicyService.updateOfficer(customerPolicyId, officerId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Officer assigned!");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).body(map);
    }

    // inspection verified by officer - INSPECTION_COMPLETED / REJECTED
    @PutMapping("/update/status/{customerPolicyId}/{policyStatus}") // OFFICER, ADMIN
    public ResponseEntity<Map<String, Object>> updatePolicyStatus(@PathVariable Long customerPolicyId,
                                                                  @PathVariable PolicyStatus policyStatus,
                                                                  Principal principal){ // evaluate officer
        customerPolicyService.updatePolicyStatus(customerPolicyId, policyStatus, principal.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Policy Status Updated!");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).body(map);
    }

    // officer approves the policy
    @PutMapping("/approve/{customerPolicyId}/{startDate}") // OFFICER
    public ResponseEntity<Map<String, Object>> approvePolicy(@PathVariable Long customerPolicyId,
                                                             @PathVariable LocalDate startDate,
                                                             Principal principal){
        customerPolicyService.approvePolicy(customerPolicyId, startDate, principal.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! policy turned active");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).body(map);
    }

    // generate quote
    @GetMapping("/quote/{customerPolicyId}") // OFFICER, ADMIN
    public CustomerPolicy getQuote(@PathVariable Long customerPolicyId){
        return customerPolicyService.getQuote(customerPolicyId);
    }

    @GetMapping("/get/customer/{customerId}") // Authenticated // even officers can view policies of customers
    public List<CustomerPolicyResDto> getByCustomerId(@PathVariable long customerId){
        return customerPolicyService.getByCustomerId(customerId);
    }

    @DeleteMapping("/delete/{customerPolicyId}") // ADMIN
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable long customerPolicyId){
        customerPolicyService.deleteById(customerPolicyId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! policy turned expired");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).body(map);
    }
}
