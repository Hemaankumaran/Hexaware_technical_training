package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.LCVResDto;
import com.springboot.automobileinsurancesystem.model.LCV;
import com.springboot.automobileinsurancesystem.service.LCVService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lcv")
public class LCVController { // add, getById

    private final LCVService lcvService;

    @PostMapping("/add/{vehicleId}") // Authenticated
    public ResponseEntity<Map<String, Object>> addLCV(@Valid @RequestBody LCV lcv,
                                    @PathVariable long vehicleId){
        lcvService.addICV(lcv, vehicleId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! LCV added");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(map);
    }

    @GetMapping("/get/{LCVId}") // OFFICER, ADMIN
    public LCVResDto getById(@PathVariable long LCVId){
        return lcvService.getById(LCVId);
    }
}
