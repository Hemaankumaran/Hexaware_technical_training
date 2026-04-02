package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.LCVResDto;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.LCV;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.LCVRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LCVService {

    private final LCVRepo lcvRepo;
    private final VehicleService vehicleService;

    public void addICV(LCV lcv, long vehicleId) {
        Vehicle vehicle = vehicleService.getByIdEntity(vehicleId);
        lcv.setVehicle(vehicle);
        lcvRepo.save(lcv);
    }

    public LCVResDto getById(long lcvId) {
        LCV lcv = lcvRepo.findById(lcvId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid LCV id.."));

        return new LCVResDto(
                lcvId,
                lcv.getLcvBodyType(),
                lcv.getSeating_cap(),
                lcv.getMax_load(),
                lcv.getVehicle().getReg_no(),
                lcv.getVehicle().getFuelType()
        );
    }
}
