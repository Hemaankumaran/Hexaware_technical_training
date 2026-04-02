package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.dto.HCVResDto;
import com.springboot.automobileinsurancesystem.model.HCV;
import com.springboot.automobileinsurancesystem.service.HCVService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/hcv")
public class HCVController { // add, getById

    private final HCVService hcvService;

    @PostMapping("/add/{vehicleId}") // Authenticated
    public ResponseEntity<Map<String, Object>> addHCV(@Valid @RequestBody HCV hcv,
                                    @PathVariable long vehicleId){
        hcvService.addHCV(hcv, vehicleId);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "nice! HCV added");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map);
    }

    @GetMapping("/get/{HCVId}") // OFFICER, ADMIN
    public HCVResDto getById(@PathVariable long HCVId){
        return hcvService.getById(HCVId);
    }
}
