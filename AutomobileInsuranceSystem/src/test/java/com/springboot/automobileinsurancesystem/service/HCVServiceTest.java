package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.HCVResDto;
import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.HCVBodyType;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.HCV;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.HCVRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class HCVServiceTest {

    @InjectMocks
    private HCVService hcvService;
    @Mock
    private HCVRepo hcvRepo;

    @Test
    public void getByIdTestWhenExists(){
        // check null for service
        Assertions.assertNotNull(hcvService);

        // data
        HCV hcv = new HCV();
        hcv.setId(5);
        hcv.setHcvBodyType(HCVBodyType.TANKER);
        hcv.setPermit_number("6578H56");
        hcv.setMax_load(11);
        Vehicle vehicle = new Vehicle();
        vehicle.setReg_no("FS83ND432");
        vehicle.setFuelType(FuelType.ELECTRIC);
        hcv.setVehicle(vehicle);

        HCVResDto hcvResDto = new HCVResDto(
                hcv.getId(),
                hcv.getHcvBodyType(),
                hcv.getPermit_number(),
                hcv.getMax_load(),
                hcv.getVehicle().getReg_no(),
                hcv.getVehicle().getFuelType()
        );

        // mock
        Mockito.when(hcvRepo.findById(5L)).thenReturn(Optional.of(hcv));

        // assert
        Assertions.assertEquals(hcvResDto, hcvService.getById(5));

        // verify
        Mockito.verify(hcvRepo, Mockito.times(1)).findById(5L);
    }

    @Test
    public void getByIdTestWhenNotExists(){
        // mock
        Mockito.when(hcvRepo.findById(5L)).thenReturn(Optional.empty());
        // assert
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> hcvService.getById(5));
        // verify
        Mockito.verify(hcvRepo, Mockito.times(1)).findById(5L);
    }
}
