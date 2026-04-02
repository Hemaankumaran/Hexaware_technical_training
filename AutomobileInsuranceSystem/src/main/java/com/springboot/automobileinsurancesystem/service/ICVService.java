package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.ICVResDto;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.ICV;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.ICVRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ICVService {

    private final ICVRepo icvRepo;
    private final VehicleService vehicleService;

    public void addICV(ICV icv, long vehicleId) {
        // validate vehicle and inject
        Vehicle vehicle = vehicleService.getByIdEntity(vehicleId);
        icv.setVehicle(vehicle);

        // save
        icvRepo.save(icv);
    }

    public ICVResDto getById(long icvId) {
        ICV icv = icvRepo.findById(icvId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid ICV id.."));

        return new ICVResDto(
                icvId,
                icv.getIcvBodyType(),
                icv.getPermit_number(),
                icv.getMax_load(),
                icv.getVehicle().getReg_no(),
                icv.getVehicle().getFuelType()
        );
    }
}
