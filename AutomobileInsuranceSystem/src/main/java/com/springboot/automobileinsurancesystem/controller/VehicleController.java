package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.VehicleByCustomerDto;
import com.springboot.automobileinsurancesystem.dto.VehicleResDto;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.service.VehicleService;
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
@RequestMapping("/api/vehicle")
public class VehicleController { // add, getById, getByCustomerId

    private final VehicleService vehicleService;

    @PostMapping("/add") // Authenticated
    public ResponseEntity<Map<String, Object>> addVehicle(@Valid @RequestBody Vehicle vehicle){
        vehicleService.addVehicle(vehicle);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! vehicle added");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map);
    }

    @GetMapping("/get/{vehicleId}") // OFFICER, ADMIN
    public VehicleResDto getById(@PathVariable long vehicleId){
        return vehicleService.getById(vehicleId);
    }

    @GetMapping("/get/customer") // CUSTOMER
    public VehicleByCustomerDto getByCustomerId(Principal principal){
        return vehicleService.getByCustomerId(principal.getName());
    }
}
