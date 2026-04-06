package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.VehicleResDto;
import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.VehicleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;
    @Mock
    private VehicleRepo vehicleRepo;

    @Test
    public void getByIdWhenExists(){
        // data
        Vehicle vehicle = new Vehicle();
        vehicle.setReg_no("NI29SJ2038");
        vehicle.setFuelType(FuelType.DIESEL);
        vehicle.setVehicleClass(VehicleClass.LCV);
        vehicle.setModel("Honda");
        VehicleResDto dto = new VehicleResDto(
                vehicle.getId(),
                vehicle.getReg_no(),
                vehicle.getModel(),
                vehicle.getFuelType(),
                vehicle.getVehicleClass()
        );

        // mock
        Mockito.when(vehicleRepo.findById(4L)).thenReturn(Optional.of(vehicle));

        // assert
        Assertions.assertEquals(dto, vehicleService.getById(4L));

        // verify
        Mockito.verify(vehicleRepo, Mockito.times(1)).findById(4L);
    }

    @Test
    public void getByIdWhenNotExists(){
        // mock
        Mockito.when(vehicleRepo.findById(4L)).thenReturn(Optional.empty());

        // assert
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> vehicleService.getById(4L));
        Assertions.assertEquals("Invalid vehicle id..", e.getMessage());
    }
}
