package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.CustomerPageResDto;
import com.springboot.automobileinsurancesystem.dto.CustomerResDto;
import com.springboot.automobileinsurancesystem.dto.CustomerSignUpDto;
import com.springboot.automobileinsurancesystem.model.Customer;
import com.springboot.automobileinsurancesystem.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController { // add, customerSignUp, getAll, getById

    private final CustomerService customerService;

    @PostMapping("/add") // Authenticated
    public ResponseEntity<Map<String, Object>> addCustomer(@Valid @RequestBody Customer customer){
        customerService.addCustomer(customer);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! customer added");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map);
    }

    @PostMapping("/signup") // PermitAll
    public ResponseEntity<Map<String, Object>> addCustomerSignUp(@Valid @RequestBody CustomerSignUpDto customerSignUpDto){
        customerService.addCustomerSignUp(customerSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/get") // ADMIN
    public CustomerPageResDto getAllCustomers(@RequestParam(value = "size", required = false, defaultValue = "5")int size,
                                              @RequestParam(value = "page", required = false, defaultValue = "0")int page){
        return customerService.getAllCustomers(size, page);
    }

//    @GetMapping("/get/{id}") // OFFICER, ADMIN
//    public CustomerResDto getById(@PathVariable long id){
//        return customerService.getById(id);
//    }
}
