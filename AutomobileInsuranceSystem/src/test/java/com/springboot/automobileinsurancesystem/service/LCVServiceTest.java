package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.LCVResDto;
import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.LCVBodyType;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.LCV;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.LCVRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LCVServiceTest {

    @InjectMocks
    private LCVService lcvService;
    @Mock
    private LCVRepo lcvRepo;

    @Test
    public void getByIdTestWhenExists(){
        // check null
        Assertions.assertNotNull(lcvService);

        // data
        LCV lcv = new LCV();
        lcv.setId(5);
        lcv.setLcvBodyType(LCVBodyType.CAR);
        lcv.setSeating_cap(4);
        lcv.setMax_load(5);
        Vehicle vehicle = new Vehicle();
        vehicle.setReg_no("DFN3948");
        vehicle.setFuelType(FuelType.DIESEL);
        lcv.setVehicle(vehicle);

        LCVResDto lcvResDto = new LCVResDto(
                lcv.getId(),
                lcv.getLcvBodyType(),
                lcv.getSeating_cap(),
                lcv.getMax_load(),
                lcv.getVehicle().getReg_no(),
                lcv.getVehicle().getFuelType()
        );

        // mock
        Mockito.when(lcvRepo.findById(5L)).thenReturn(Optional.of(lcv));

        // assert
        Assertions.assertEquals(lcvResDto, lcvService.getById(5));

        // verify
        Mockito.verify(lcvRepo, Mockito.times(1)).findById(5L);
    }

    @Test
    public void getByIdTestWhenNotExists(){
        // mock
        Mockito.when(lcvRepo.findById(5L)).thenReturn(Optional.empty());
        // assert
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> lcvService.getById(5));
        // verify
        Mockito.verify(lcvRepo, Mockito.times(1)).findById(5L);
    }
}
