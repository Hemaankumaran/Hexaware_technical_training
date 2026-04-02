package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.HCVResDto;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.HCV;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.HCVRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HCVService {

    private final HCVRepo hcvRepo;
    private final VehicleService vehicleService;

    public void addHCV(@Valid HCV hcv, long vehicleId) {
        Vehicle vehicle = vehicleService.getByIdEntity(vehicleId); // this also validates the vehicleId
        hcv.setVehicle(vehicle);
        hcvRepo.save(hcv);
    }

    public HCVResDto getById(long hcvId) {
        HCV hcv = hcvRepo.findById(hcvId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid HCV id.."));

        return new HCVResDto(
                hcvId,
                hcv.getHcvBodyType(),
                hcv.getPermit_number(),
                hcv.getMax_load(),
                hcv.getVehicle().getReg_no(),
                hcv.getVehicle().getFuelType()
        );
    }
}
