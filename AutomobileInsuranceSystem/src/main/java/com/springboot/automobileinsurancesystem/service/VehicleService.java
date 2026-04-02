package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.VehicleByCustomerDto;
import com.springboot.automobileinsurancesystem.dto.VehicleResDto;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.VehicleMapper;
import com.springboot.automobileinsurancesystem.model.Customer;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.VehicleRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepo vehicleRepo;
    private final CustomerService customerService;

    public void addVehicle(@Valid Vehicle vehicle) {
        vehicleRepo.save(vehicle);
    }

    public VehicleResDto getById(long id) { // returns dto
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid vehicle id.."));
        return VehicleMapper.mapToDto(vehicle);
    }

    public Vehicle getByIdEntity(long vehicleId) { // returns entity
        return vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid vehicle id.."));
    }

    public VehicleByCustomerDto getByCustomerId(String username) {
        // get customer by username
        Customer customer = customerService.getByUsername(username);

        // entity to dto
        List<Vehicle> list = vehicleRepo.getCustomerById(customer.getId());
        return new VehicleByCustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getLicense_number(),
                list.stream()
                        .map(VehicleMapper :: mapToDto)
                        .toList()
        );
    }
}
