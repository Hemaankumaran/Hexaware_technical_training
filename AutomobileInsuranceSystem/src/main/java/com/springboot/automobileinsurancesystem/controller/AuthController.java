package com.springboot.automobileinsurancesystem.controller;

import com.springboot.automobileinsurancesystem.utility.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController { // login

    private final JwtUtility jwtUtility;

    @GetMapping("/login") // authenticated
    public ResponseEntity<Map<String, String>> login(Principal principal){
        String loggedInUsername = principal.getName();
        Map<String, String> map = new HashMap<>();
        map.put("Token", jwtUtility.generateToken(loggedInUsername));
        return ResponseEntity.
                status(HttpStatus.CREATED).body(map);
    }
}
