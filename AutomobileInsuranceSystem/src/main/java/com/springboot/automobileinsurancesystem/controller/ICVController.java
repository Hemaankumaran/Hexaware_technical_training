package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.ICVResDto;
import com.springboot.automobileinsurancesystem.model.ICV;
import com.springboot.automobileinsurancesystem.service.ICVService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/icv")
public class ICVController { // add, getById

    private final ICVService icvService;

    @PostMapping("/add/{vehicleId}") // Authenticated
    public ResponseEntity<Map<String, Object>> addICV(@Valid @RequestBody ICV icv,
                                    @PathVariable long vehicleId){
        icvService.addICV(icv, vehicleId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! ICV added");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(map);
    }

    @GetMapping("/get/{ICVId}") // OFFICER, ADMIN
    public ICVResDto getById(@PathVariable long ICVId){
        return icvService.getById(ICVId);
    }
}
