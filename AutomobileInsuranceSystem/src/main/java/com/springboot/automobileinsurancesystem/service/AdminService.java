package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.AdminReqDto;
import com.springboot.automobileinsurancesystem.enums.Role;
import com.springboot.automobileinsurancesystem.model.User;
import com.springboot.automobileinsurancesystem.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public void addAdmin(AdminReqDto adminReqDto) {
        User user = new User();
        user.setUsername(adminReqDto.username());
        user.setPassword(passwordEncoder.encode(adminReqDto.password()));
        user.setRole(Role.ADMIN);
        userRepo.save(user);
        log.atLevel(Level.INFO).log("Admin credentials has been saved!");
    }
}
